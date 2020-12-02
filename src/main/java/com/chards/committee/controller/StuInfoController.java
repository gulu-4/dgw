package com.chards.committee.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.ParentsInfo;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.StuInfoPageDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.ParentsInfoService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 学生端信息处理
 * </p>
 *
 * @author chards
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/stu-info")
public class StuInfoController {
	@Resource
	private StuInfoService stuInfoService;
	@Resource
	private ParentsInfoService parentsInfoService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	CoreAdminService coreAdminService;

	@Autowired
	RedisTemplate redisTemplate;

	/**
	 * 新增学生信息
	 *
	 * @param stuInfoAddVO {@link StuInfo}
	 * @return {@link R}
	 */
	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("add")
	public R create(@Valid @RequestBody StuInfoAddVO stuInfoAddVO) {
		StuInfo byId = stuInfoService.getById(stuInfoAddVO.getId());
		if (byId!=null){
			BusinessException.error(Code.DATA_ALREADY_EXISTED);
		}
		StuInfo stuInfo=new StuInfo();
		BeanUtils.copyProperties(stuInfoAddVO,stuInfo);
		CoreAdmin coreAdmin = coreAdminService.getById(stuInfoAddVO.getCounsellorNum());
		Assert.notNull(coreAdmin,"该辅导员工号不存在");
		if (!stuInfoService.isWork(coreAdmin,stuInfo,true))BusinessException.error("辅导员没有该学院年级的权限");

		return R.success(stuInfoService.save(stuInfo));
	}

	/**
	 * 删除某一学生信息
	 *
	 * @param stuInfoId {@code Long}
	 * @return {@link R}
	 */
	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping("remove/{stuInfoId}")
	public R remove(@PathVariable String stuInfoId) {
		return R.success(stuInfoService.removeById(stuInfoId));
	}

	/**
	 * 修改
	 *
	 * @param stuInfoUpdateVO {@link StuInfoUpdateVO}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('student_update')")
	@PostMapping("/update")
	public R update(@Valid @RequestBody StuInfoUpdateVO stuInfoUpdateVO) {
		if (stuInfoService.isContainsReturnIsWork(stuInfoUpdateVO.getStuInfo().getId())) {
			return R.success(stuInfoService.updateStuInfoAndStuParentsInfo(stuInfoUpdateVO.getStuInfo(), stuInfoUpdateVO.getParentsInfo()));
		}
		return R.failure(Code.PERMISSION_NO_ACCESS);
	}


	/**
	 * 学生个人信息查看
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping("/me")
	public R getLoginInfo() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		return R.success(stuInfoService.getById(userInfo.getId()));
	}

	/**
	 * 学生登录密码重置
	 * 原密码  ->  新密码
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping("/own/password")
	public R updatePassword(@Valid @RequestBody UserPwdVO userPwdVO, @RequestHeader(value = "Authorization") String token, BindingResult bindingResult) {
		// 表单验证
		if (bindingResult.hasErrors()) {
			return R.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
		}
		UserInfo userInfo = RequestUtil.getLoginUser();
		boolean b = stuInfoService.updateStuPwd(userInfo.getId(), userPwdVO.getOldpwd(), userPwdVO.getNewpwd());
		if (b) {
			redisTemplate.delete(token);
			return R.success("密码更新成功");
		}

		return R.failure("密码更新失败");
	}


	/**
	 * 自己的父母信息查看
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping("/parents")
	public R getParentsInfo() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		return R.success(stuInfoService.getParentsInfo(userInfo.getId()));
	}

	/**
	 * 查看某一学生的父母信息
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/parents/{id}")
	public R getParentsInfoByStuId(@PathVariable String id) {
		if (stuInfoService.isContainsReturnIsWork(id)) {
			return R.success(stuInfoService.getParentsInfo(id));
		}
		return R.failure(Code.PERMISSION_NO_ACCESS);
	}

	/**
	 * 学生父母信息修改
	 *
	 * @return
	 */
	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@PostMapping("/parents")
	public R updateParentsInfo(@RequestBody ParentsInfo parentsInfo) {
		UserInfo userInfo = RequestUtil.getLoginUser();
		ParentsInfo parentsInfo1 = parentsInfo.setStuNum(userInfo.getId());
		return R.success(parentsInfoService.updateById(parentsInfo1));
	}


	/**
	 * 管理员获取某一学生信息
	 *
	 * @param stuInfoId {@code Long}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/get/{stuInfoId}")
	public R get(@PathVariable String stuInfoId) {
		StuInfo stuInfo = stuInfoService.getById(stuInfoId);
		Assert.notNull(stuInfo, Code.RESULT_DATA_NONE);
		if (stuInfoService.isWork(stuInfo)) {
			if (stuInfo.getCounsellorNum() != null && !StringUtils.isBlank(stuInfo.getCounsellorNum())
					&& !stuInfo.getCounsellorNum().equals("无")) {
				CoreAdmin coreAdmin = coreAdminService.getById(stuInfo.getCounsellorNum());
				if (coreAdmin != null) {
					stuInfo.setCounsellorName(coreAdmin.getName());
					stuInfo.setCounsellorPhone(coreAdmin.getPhone());
				}
				return R.success(stuInfo);
			}
			return R.success(stuInfo);
		}
		return R.failure(Code.PERMISSION_NO_ACCESS);
	}

	/**
	 * 模糊查询
	 * 辅导员只能查看自己工作范围内所管理的学生
	 *
	 * @param stuInfoPageDTO
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/page")
	public R getStuInfoByParam(StuInfoPageDTO stuInfoPageDTO, Page<StuInfoPageVO> page) {
		stuInfoPageDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		return R.success(stuInfoService.getLike(page, stuInfoPageDTO));
	}


	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/updateStuPwd")
	public R updateStuPwd(@RequestParam("id") String id) {
		StuInfo stuInfo = stuInfoService.getById(id);
		stuInfo.setPassword(bCryptPasswordEncoder.encode(id));
		return R.success(stuInfoService.updateById(stuInfo));
	}

	/**
	 * 高级检索的接口
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/senior")
	public R seniorSearch(Page<StuInfoPageVO> page, StuInfoSeniorVO stuInfoSeniorVO) {
		return R.success(stuInfoService.getSeniorSearch(page, stuInfoSeniorVO));
	}

}
