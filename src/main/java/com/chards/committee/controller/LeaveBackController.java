/**
 * 在10.4版（21年2月）的大重构中，本某块未重构，若日后要使用，还得一个个测试修改一下！
 */
package com.chards.committee.controller;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.BackSchool;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.Leave;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.AdminWorkDTO;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.LeaveBack;
import com.chards.committee.dto.BackSchoolAdminGetAndUpdateDTO;
import com.chards.committee.dto.LeaveBackAdminGetAndUpdateDTO;
import com.chards.committee.dto.PassInfoDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.LeaveBackService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.BackSchoolPassVO;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.LeaveBackGetAllVO;
import com.chards.committee.vo.LeaveBackPassVO;
import com.chards.committee.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * (LeaveBack)表控制层
 *
 * @author chards
 * @since 2020-09-25 20:52:36
 */
@RestController
@RequestMapping("/leaveBacks")
public class LeaveBackController {
	/**
	 * 服务对象
	 */
	@Autowired
	private LeaveBackService leaveBackService;

	@Autowired
	private StuInfoService stuInfoService;

	@Autowired
	private CoreAdminService coreAdminService;

	/**
	 * 分页查询所有数据
	 *
	 * @param page      分页对象
	 * @return 所有数据
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping
	public R selectAll(Page<LeaveBackGetAllVO> page, Integer pass) {
		LeaveBackAdminGetAndUpdateDTO leaveBackAdminGetAndUpdateDTO = new LeaveBackAdminGetAndUpdateDTO();
		leaveBackAdminGetAndUpdateDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		leaveBackAdminGetAndUpdateDTO.setPass(pass);
		return R.success(leaveBackService.getAdminManagementStudentBackSchool(page, leaveBackAdminGetAndUpdateDTO));
	}

	/**
	 * 通过主键查询单条数据
	 *
	 * @return 单条数据
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping("/info")
	public R getBackInfoById() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		return R.success(leaveBackService.getById(userInfo.getId()));
	}
	/**
	 * 新增数据
	 *
	 * @param leaveBack 实体对象
	 * @return 新增结果
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping
	public R insert(@RequestBody LeaveBack leaveBack) {
		UserInfo userInfo = RequestUtil.getLoginUser();
		leaveBack.setStuNum(userInfo.getId());
		leaveBack.setPass(0);  //   0代表未审核
		return R.success(leaveBackService.save(leaveBack));
	}


	/**
	 * 新增数据
	 *
	 * @param leaveBack 实体对象
	 * @return 新增结果
	 */
	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping("/resubmitPass")
	public R updatePassInfo(@RequestBody LeaveBack leaveBack) {
		UserInfo userInfo = RequestUtil.getLoginUser();
		leaveBack.setStuNum(userInfo.getId());
		leaveBack.setPass(0);   //   0代表未批
		return R.success(leaveBackService.save(leaveBack));
	}


	/**
	 * 学生删除自己已填写返校数据
	 *
	 * @return 删除结果
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping("/delete")
	public R deleteStu() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		return R.success(leaveBackService.removeById(userInfo.getId()));
	}


	/**
	 * 辅导员删除自己管理学生返校数据
	 *
	 * @return 删除结果
	 */
	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping("/deleteStu/{id}")
	public R deleteStuBackInfo(@PathVariable String id) {
		if (leaveBackService.getById(id)==null){
			BusinessException.error(Code.RESULT_DATA_NONE);
		}
		return R.success(leaveBackService.removeById(id));
	}


	/**
	 * 获取审核步骤信息
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping("/passInfo")
	public R getPassInfo() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		LeaveBack bs = leaveBackService.getById(userInfo.getId());
		return bs != null ? R.success(passInfo(bs.getPass())) : R.success("未填写");
	}


	private String passInfo(int pass) {
		if (pass == 0) {
			return "未审核";
		}

		if (pass == 1) {
			return "审核未通过";
		}
		if (pass == 2) {
			return "审核通过";
		} else
			return "已报到";
	}


	/**
	 * 返回具体的返校详情信息用于pdf生成
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping("/passInfoPdf")
	public R getPassInfoPdf() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		LeaveBack bs = leaveBackService.getById(userInfo.getId());
		StuInfo stuInfo = stuInfoService.getById(userInfo.getId());
		CoreAdmin coreAdmin = coreAdminService.getById(bs.getReviewedBy());
		return  R.success(getPassInfoDTO(bs,stuInfo,coreAdmin));
	}

	private PassInfoDTO getPassInfoDTO(LeaveBack bs , StuInfo stuInfo, CoreAdmin coreAdmin){
		PassInfoDTO passInfoDTO=new PassInfoDTO();
		passInfoDTO.setStuId(stuInfo.getId());
		passInfoDTO.setName(stuInfo.getName());
		passInfoDTO.setDate(bs.getDate());
		passInfoDTO.setLoc(bs.getPickupLoc());
		passInfoDTO.setReviewedBy(coreAdmin.getName());
		passInfoDTO.setEmergencyCallee(bs.getEmergencyCallee());
		passInfoDTO.setDepartment(stuInfo.getDepartment());
		return passInfoDTO;
	}

	/**
	 * 微信小程序 -报道
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping("/reports")
	public R setReports(){
		LeaveBack backSchool = leaveBackService.getById(RequestUtil.getId());
		backSchool.setPass(3); //3 是已报道
		return R.success( leaveBackService.updateById(backSchool));
	}


	/**
	 * 修改一条数据
	 *
	 * @param leaveBackPassVO 实体对象
	 * @return 修改结果
	 */
	@PreAuthorize("hasAuthority('student_update')")
	@PostMapping("/update")
	public R update(@Valid @RequestBody LeaveBackPassVO leaveBackPassVO) {
		if (stuInfoService.isContainsReturnIsWork(leaveBackPassVO.getStuNum())) {
			LeaveBack leaveBack = new LeaveBack();
			leaveBack.setStuNum(leaveBackPassVO.getStuNum());
			leaveBack.setPass(leaveBackPassVO.getPass());
			leaveBack.setReviewedBy(RequestUtil.getId());
			return R.success(leaveBackService.updateById(leaveBack));
		}
		return R.failure(Code.PERMISSION_NO_ACCESS);
	}


	/**
	 * 修改管理员管理的学生的所有审核中的数据
	 *
	 * @return 修改结果
	 */
	@PreAuthorize("hasAuthority('student_update')")
	@PostMapping("/all")
	public R updateAll(Integer pass) {
		LeaveBackAdminGetAndUpdateDTO leaveBackAdminGetAndUpdateDTO = new LeaveBackAdminGetAndUpdateDTO();
		leaveBackAdminGetAndUpdateDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		leaveBackAdminGetAndUpdateDTO.setPass(pass == null ? 2 : pass == 2 ? 2 : 1);
		return R.success(leaveBackService.updateAdminManagementStudentBackSchoolPass(leaveBackAdminGetAndUpdateDTO));
	}

}