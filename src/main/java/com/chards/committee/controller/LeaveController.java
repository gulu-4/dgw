package com.chards.committee.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.Leave;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.LeaveByIdOrNameDTO;
import com.chards.committee.dto.LeaveInfoDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.LeaveService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.service.UserService;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * (Leave)表控制层
 *
 * @author chards
 * @since 2020-08-26 14:51:51
 */
@RestController
@RequestMapping("/leaves")
@Api(tags = "请假信息模块")
public class LeaveController {
	/**
	 * 服务对象
	 */
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private StuInfoService stuInfoService;

	@Autowired
	private CoreAdminService coreAdminService;

	@Autowired
	private UserService userService;

	/**
	 * 学生查看自己的所有的数据
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping
	@ApiOperation(value = "学生查看自己的所有数据")
	public R selectAll() {
		Leave leave = new Leave();
		leave.setStuNum(RequestUtil.getId());
		List<Leave> leaves = leaveService.list(new QueryWrapper<>(leave).orderByDesc("create_time"));
		if (leaves.size()==0){
			return R.success("无请假数据");
		}

		leaves.forEach(leave1 -> {
			if (!StringUtils.isBlank(leave1.getReviewerId())) {
//				CoreAdmin byId = coreAdminService.getById(leave1.getReviewerId());
				UserInfo userInfo = userService.getUserById(leave1.getReviewerId());
				if (userInfo!=null)
				leave1.setReviewerId(userInfo.getName());
				else leave1.setReviewerId("管理员");
			}
		});
		return R.success(leaves);
	}

	/**
	 * 新增数据
	 *
	 * @param leaveInsertVO 实体对象
	 * @return 新增结果
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping
	@ApiOperation(value = "提交请假申请")
	public R insert(@RequestBody @Valid LeaveInsertVO leaveInsertVO) {
		if (leaveInsertVO.getEndDate().isBefore(leaveInsertVO.getStartDate())) {
			return R.failure("结束时间不能小于开始时间");
		}
		int size = leaveService.stuGetByDateArea(leaveInsertVO.getStartDate(), leaveInsertVO.getEndDate());
		if (size > 0) {
			return R.failure("该段时间内已有请假记录,请勿重复提交");
		}
		Leave leave = new Leave();
		BeanUtils.copyProperties(leaveInsertVO, leave);
		leave.setStuNum(RequestUtil.getId());
		leave.setStatus(0);
		leave.setCreateTime(LocalDateTime.now());
		//防止前端提交
		leave.setReviewerId(null);

//		2021年1月29日，按学工处要求，在10.3.0版中取消自动审批
//		leave = getAutoReview(leave);
		return R.success(leaveService.save(leave));
	}

	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping("/delete")
	@ApiOperation(value = "删除请假申请记录")
	public R delete(Long id) {
		Leave leave = new Leave();
		leave.setId(id);
		leave.setStuNum(RequestUtil.getId());
		leave.setStatus(0);
		return R.success(leaveService.remove(new QueryWrapper<>(leave)));
	}


	/**
	 * 管理员查看所有的请假记录
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/stu")
	@ApiOperation(value = "管理员查看所有的请假记录")
	public R adminSelectLeaveAll(Page<LeaveInfoVO> page, LeaveSchoolQueryParamVO leaveSchoolQueryParamVO) {
//		leaveSchoolQueryParamVO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		Page<LeaveInfoVO> leaveInfoVOPage = leaveService.adminSelectLeave(page, leaveSchoolQueryParamVO);
		leaveInfoVOPage.getRecords().forEach(leaveInfoVO -> {
			if (!StringUtils.isBlank(leaveInfoVO.getReviewerId())) {
//				CoreAdmin byId = coreAdminService.getById(leaveInfoVO.getReviewerId());
				UserInfo userInfo = userService.getUserById(leaveInfoVO.getReviewerId());
				if(userInfo!=null)
				leaveInfoVO.setReviewerId(userInfo.getName());
				else leaveInfoVO.setReviewerId("不详");
			}
		});
		return R.success(leaveInfoVOPage);
	}

	/**
	 * 管理员根据姓名或学号  查看某一个学生的请假记录
	 *
	 * @param param  / name  id or name ? 你他妈肯定要用sql啊 是啊 Sql wodou1 不分页？ 不分
	 * @return
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/stu/{param}")
	@ApiOperation(value = "管理员根据姓名或学号，查看某一个学生的请假记录")
	public R adminSelectLeaveByStuid(@PathVariable String param) {
			return R.success(leaveService.getByIdOrName(param));
	}

	/**
	 * 管理员修改某一条请假记录的状态
	 */
	@PreAuthorize("hasAuthority('student_update')")
	@PutMapping
	public R adminUpdateStatus(@RequestBody @Valid LeaceStatusUpdateVO updateVO) {
		Leave leave = leaveService.getById(updateVO.getId());
		Assert.notNull(leave, Code.RESULT_DATA_NONE);
		if (leave.getEndDate().isBefore(LocalDateTime.now())) {
			return R.failure("该条记录请假时间已过");
		}
		if (stuInfoService.isWithinDataScope(leave.getStuNum())) {
			leave.setStatus(updateVO.getStatus());
			leave.setReviewerId(RequestUtil.getId());
			return R.success(leaveService.updateById(leave));
		}
		return R.failure(Code.PERMISSION_NO_ACCESS);
	}


	/**
	 * 江苏省内一天返回 自动审核通过 ；
	 * 其余需要审批
	 * @return
	 */
	private Leave getAutoReview(Leave leave){
		if (leave.getMovement().substring(0,7).equals("江苏省 徐州市")){
			LocalDateTime startDate = leave.getStartDate();
			LocalDateTime endDate = leave.getEndDate();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String start = startDate.format(dtf);
			String end = endDate.format(dtf);
			if (end.equals(start)){
				StuInfo stuInfo = stuInfoService.getById(leave.getStuNum());
				if (stuInfo.getCounsellorNum()!=null){
					leave.setReviewerId(stuInfo.getCounsellorNum());
					leave.setStatus(1);
				}
			}
		}
   return leave;
	}

}