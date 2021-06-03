package com.chards.committee.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import com.chards.committee.util.UploadUtil;
import com.chards.committee.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
@Api(tags = "学生信息管理")
@Slf4j
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

	@Value("${filepath}")
	String path;

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
//		if (!stuInfoService.isWork(coreAdmin,stuInfo,true))BusinessException.error("辅导员没有该学院年级的权限");
		stuInfo.setState("在籍");  //新增学生时在籍状态默认为 在籍
		return R.success(stuInfoService.save(stuInfo));
	}

	/**
	 * 删除某一学生信息
	 *
	 * @param stuInfoId {@code Long}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('student_update')")
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
		if (stuInfoService.isWithinDataScope(stuInfoUpdateVO.getStuInfo().getId())) {
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
		if (stuInfoService.isWithinDataScope(id)) {
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
		if (stuInfoService.isWithinDataScope(stuInfo)) {
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
	 * 根据学号查看某一学生的信息，向咨询师放开数据权限限制
	 * @param stuInfoId
	 * @return
	 */
	@PreAuthorize("hasRole('PCOUNSELOR')")
	@GetMapping("/get/psychological_counselor/{stuInfoId}")
	public R getForPsychologicalCounselor(@PathVariable String stuInfoId) {
		StuInfo stuInfo = stuInfoService.getById(stuInfoId);
		Assert.notNull(stuInfo, Code.RESULT_DATA_NONE);
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
//		stuInfoPageDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		return R.success(stuInfoService.getLike(page, stuInfoPageDTO));
	}

	/**

	 * 心理咨询师专用
	 * 模糊查询
	 * 可以不受数据权限控制查全校所有学生信息
	 * @param stuInfoPageDTO
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasRole('PCOUNSELOR')")
	@GetMapping("/psychological_counselor/page")
	public R getStuByParamForPsychologicalCounselorInfo(StuInfoPageDTO stuInfoPageDTO, Page<StuInfoPageVO> page) {
//		stuInfoPageDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		return R.success(stuInfoService.getLikeForPsychologicalCounselor(page, stuInfoPageDTO));
	}


	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/updateStuPwd")
	public R updateStuPwd(@RequestParam("id") String id) {
		if (stuInfoService.isWithinDataScope(id)){
			StuInfo stuInfo = stuInfoService.getById(id);
			stuInfo.setPassword(bCryptPasswordEncoder.encode(id));
			return R.success(stuInfoService.updateById(stuInfo));
		}
		return R.failure(Code.PERMISSION_NO_ACCESS);
	}

	/**
	 * 高级检索的接口
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/senior")
	public R seniorSearch(Page<StuInfoPageVO> page, StuInfoSeniorVO stuInfoSeniorVO) {
		return R.success(stuInfoService.getSeniorSearch(page, stuInfoSeniorVO));
	}

	/**
	 * 根据学生学号查询该学生的舍友信息
	 */
	@PreAuthorize("hasAuthority('student_select')")
	@GetMapping("/getStudentsByDorStuNumber/{id}")
	public R getStudentsByDorStuNumber(@PathVariable String id) {
		return R.success(stuInfoService.getStudentsByDorStuNumber(id));
	}

	/**
	 * 有管辖权限下的教职工可以修改
	 */
	@PreAuthorize("hasAuthority('student_update')")
	@PostMapping("/updateStudentAvatar/{stuNumber}")
	@ApiOperation(value = "修改学生头像")
	public R updateStudentAvatar(@RequestParam(value = "file") MultipartFile file,
								 @PathVariable(value = "stuNumber") String stuNumber,
								 HttpServletRequest request) throws IOException {
		if (stuInfoService.isWithinDataScope(stuNumber)) {
			if (file.isEmpty()) {
				log.error("文件不能为空");
			}
			String extension = getFileExtension(file); // 后缀名
			// 对文件后缀名进行判断,如果不是png或者jpg则返回
			if (!extension.equals(".jpg") && !extension.equals(".png")) {
				return R.failure("图片后缀只能为jpg或者png");
			}
			// 对文件大小进行限定，如果超过某一大小则进行压缩
			// https://blog.csdn.net/qq_42476834/article/details/108886493
			// TODO
			String fileName = "/" + stuNumber + ".jpg"; // 新文件名
			File dest = new File(path + fileName);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			try {
				file.transferTo(dest);
			} catch (IOException e) {
				log.error(e.toString());
				return R.failure(Code.ERROR);
			}
			return R.success(Code.SUCCESS);
		}else{
			return R.failure(Code.PERMISSION_NO_ACCESS);
		}
	}

	/**
	 * 修改学生在籍状态  通过id  修改state  为非在籍
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('student_update')")
	@PutMapping("/graduateStudent/{id}")
	public R graduateStudent(@PathVariable String id){
		UpdateWrapper<StuInfo> updateWrapper = new UpdateWrapper<StuInfo>();
		updateWrapper.set("state","非在籍");
		updateWrapper.eq("id",id);
		return R.success(stuInfoService.update(updateWrapper));
	}

	/**
	 * 传递多个学生学号，一键让多个学生毕业
	 * @param ids
	 * @return
	 */
	// TODO

	private String getFileExtension(MultipartFile File) {
		String originalFileName = File.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

}
