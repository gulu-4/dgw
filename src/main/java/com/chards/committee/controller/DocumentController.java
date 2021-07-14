package com.chards.committee.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.constant.Constant;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.User;
import com.chards.committee.dto.*;
import com.chards.committee.service.*;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import com.chards.committee.vo.teachStaffExport.TeachStaffBasicExportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 远 chards_
 * @FileName:DocumentController
 * @date: 2020-07-28 10:20
 */
@RestController
@RequestMapping("/documents")
@Slf4j
public class DocumentController {
	@Autowired
	private StuInfoService stuInfoService;
	@Autowired
	RedisService redisService;
	@Autowired
	CoreAdminService coreAdminService;
	@Autowired
	BackSchoolService backSchoolService;
	@Autowired
	StuPortraitService stuPortraitService;

	@Autowired
	LeaveService leaveService;
	@Autowired
	LeaveBackService leaveBackService;

	@Autowired
	LeaveSchoolTztzAutumnService leaveSchoolTztzAutumnService;

	@Autowired
	PsychologicalLevelService psychologicalLevelService;

	@Autowired
	PsychologicalCounsellingCaseService psychologicalCounsellingCaseService;

	@Autowired
	TeachingStaffResumeService teachingStaffResumeService;

	@Autowired
	JobObtainService jobObtainService;

	@Value("${filepath}")
	String path;

	@Value("${tFilePath}")
	String tPath;
	@Value("${defaultPhoto}")
	String DEFAULE_IMG;

	@Autowired
	EasyExeclService easyExeclService;

	@Autowired
	UserService userService;

	/**
	 * 我觉得和权限判断有关的 都是走那一套
	 * 学生头像接口
	 *
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getDocuments(@PathVariable String id, String token) throws IOException {
		try {
			UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
			if (userTokenDTO != null) {
				File file = new File(path, id + ".jpg");
				if (file.exists()) {
					RequestUtil.setUserTokenDTO(userTokenDTO);
					// 本人查看放在前面  后面查看是否work （ 学生查看的话会报错NPE ）
					if (RequestUtil.getId().equals(id) || stuInfoService.isWithinDataScope(id)) {
						return getFileByte(file);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return getFileByte(new File(path, DEFAULE_IMG));
	}

	/**
	 * 获取教职工头像接口
	 * @param id
	 * @param token
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/staff/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getAdminDocuments(@PathVariable String id, String token) throws IOException {
		try {
			UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
			if (userTokenDTO != null) {
				File file = new File(tPath, id + ".png");
				if (file.exists()) {
					RequestUtil.setUserTokenDTO(userTokenDTO);
					// 本人查看放在前面  后面是ROOT查看
					if (RequestUtil.getId().equals(id) || RequestUtil.getRoles().contains("ROOT")) {
						return getFileByte(file);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return getFileByte(new File(path, DEFAULE_IMG));
	}

	/**
	 * 获取租借场地图片接口
	 * @param id
	 * @param token
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/field/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getFieldDocuments(@PathVariable String id, String token) throws IOException {
		try {
			UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
			if (userTokenDTO != null) {
				File file = new File(path + "/field/", id + ".png");
				if (file.exists()) {
					RequestUtil.setUserTokenDTO(userTokenDTO);
					return getFileByte(file);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return getFileByte(new File(path, DEFAULE_IMG));
	}

	/**
	 * 获取教师培训经历附件照片图片接口
	 * @param id
	 * @param token
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/qualification/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getQualificationDocuments(@PathVariable String id, String token) throws IOException {
		try {
			UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
			if (userTokenDTO != null) {
				File file = new File(path + "/qualification/", id + ".png");
				if (file.exists()) {
					RequestUtil.setUserTokenDTO(userTokenDTO);
					return getFileByte(file);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return getFileByte(new File(path, DEFAULE_IMG));
	}

	private byte[] getFileByte(File file) throws IOException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes, 0, inputStream.available());
			return bytes;
		} catch (IOException e) {
			if (inputStream != null) inputStream.close();
			return null;
		}
	}

	/**
	 * 获取管理员excel表格
	 * 通过检索导出教职工名单
	 *
	 * @param token
	 * @param param
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/admin/param")
	public void getAdminByParamExecl(String token, String param, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_TEACHER_SELECT)) {
			List<CoreAdminDTO> likeList = coreAdminService.getLikeList(param);
			easyExeclService.writeToResponse(response, "admininfo-" + System.currentTimeMillis(), likeList, CoreAdminDTO.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 通过学院筛选导出教职工
	 * @param token
	 * @param department
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/admin/department")
	public void getAdminByDepartmentExecl(String token, String department, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_TEACHER_SELECT)) {
			List<CoreAdminDTO> depList = coreAdminService.getDepList(department);
			easyExeclService.writeToResponse(response, "admininfo - " + System.currentTimeMillis(), depList, CoreAdminDTO.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 导出学生画像
	 * @param token
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/stuInfo/portrait")
	public void getStuInfoPortrait(String token, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_TEACHER_SELECT)) {
			List<String> stuNums = stuPortraitService.getAllstuNum();
			List<PortraitVO> res=new ArrayList<>();
			for (String a:stuNums) {
				PortraitVO portraitVO=new PortraitVO();
				StuInfo byId = stuInfoService.getById(a);
				if(byId!=null){
					portraitVO.setStuNum(a);
					portraitVO.setName(byId.getName());
					portraitVO.setDepartment(byId.getDepartment());
					portraitVO.setGrade(byId.getGrade());
				  res.add(portraitVO);
				}
			}
			easyExeclService.writeToResponse(response, "portrait - " + System.currentTimeMillis(), res, PortraitVO.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 通过关键词导出学生信息
	 * @param token
	 * @param stuInfoPageDTO
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/stuinfo/keywords")
	public void getStuInfoKeywordsExecl(String token, StuInfoPageDTO stuInfoPageDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
//			stuInfoPageDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
			List<StuInfoPageVO> likeLists = stuInfoService.getLikeList(stuInfoPageDTO);
			List<StuInfo> stuInfoList = new ArrayList<>();
			likeLists.forEach(s -> {
				StuInfo stuinfo = stuInfoService.getById(s.getId());
				stuInfoList.add(stuinfo);
			});
			easyExeclService.writeToResponse(response, "stuinfo - " + System.currentTimeMillis(), stuInfoList, StuInfo.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	@GetMapping(value = "/execls/stuinfo/Advancekeywords")
	public void getStuInfoAdvanceKeywordsExecl(String token, StuInfoSeniorVO stuInfoSeniorVO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			List<StuInfoPageVO> likeLists = stuInfoService.getSeniorSearchList(stuInfoSeniorVO);
			List<StuInfo> stuInfoList = new ArrayList<>();
			likeLists.forEach(s -> {
				StuInfo stuinfo = stuInfoService.getById(s.getId());
				stuInfoList.add(stuinfo);
			});
			easyExeclService.writeToResponse(response, "stuinfo - " + System.currentTimeMillis(), stuInfoList, StuInfo.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 导出返校申请信息，可以根据筛选条件进行导出，不传条件则导出所有申请信息
	 * 学号，学院，年级，时间区间
	 * @param token
	 * @param backSchoolDateAreaDTO
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/stuinfo/backSchoolInfo")
	public void getStuInfoAdvanceKeywordsExecl(String token, BackSchoolDateAreaDTO backSchoolDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
//			backSchoolDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
			List<BackSchoolGetAllVO> dataList = backSchoolService.getAdminManagementStudentBackSchoolByDateArea(backSchoolDateAreaDTO);
			List<BackSchoolGetAllVO1> backSchoolGetAllVO1List = new ArrayList<>();
			dataList.forEach(data -> {

				BackSchoolGetAllVO1 backSchoolGetAllVO1 = setVO1ByVO(data);
				backSchoolGetAllVO1List.add(backSchoolGetAllVO1);
			});
			easyExeclService.writeToResponse(response, "backSchool - " + System.currentTimeMillis(), backSchoolGetAllVO1List, BackSchoolGetAllVO1.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 导出尚未填写申请的学生信息
	 * 可以使用学号，学院，年级进行筛选
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/stuinfo/backSchoolNotApplyInfo")
	public void getDoNotApplyForBackSchoolByParams(String token, BackSchoolDateAreaDTO backSchoolDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
//			backSchoolDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
			List<StuInfo> dataList = backSchoolService.getDoNotApplyForBackSchoolByParams(backSchoolDateAreaDTO);
			easyExeclService.writeToResponse(response, "backSchoolNotApply - " + System.currentTimeMillis(), dataList, StuInfo.class);
			return;
		}
		response.getWriter().write("no permission");
	}


	/**
	 * 导出销假申请，暂时弃用
	 * @param token
	 * @param
	 * @param response
	 * @throws IOException
	 */
//	@GetMapping(value = "/execls/stuinfo/leavebackSchoolInfo")
//	public void getStuInfoAdvanceKeywordsExecl(String token, LeaveBackDateAreaDTO backSchoolDateAreaDTO, HttpServletResponse response) throws IOException {
//		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
//		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
//			RequestUtil.setUserTokenDTO(userTokenDTO);
//			backSchoolDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
//			List<LeaveBackGetAllVO> dataList = leaveBackService.getAdminManagementStudentBackSchoolByDateArea(backSchoolDateAreaDTO);
//			List<LeaveBackGetAllVO1> backSchoolGetAllVO1List = new ArrayList<>();
//			dataList.forEach(data -> {
//				LeaveBackGetAllVO1 backSchoolGetAllVO1 = setVO1ByVO(data);
//				backSchoolGetAllVO1List.add(backSchoolGetAllVO1);
//			});
//			easyExeclService.writeToResponse(response, "leavebackSchool - " + System.currentTimeMillis(), backSchoolGetAllVO1List, LeaveBackGetAllVO1.class);
//			return;
//		}
//		response.getWriter().write("no permission");
//	}


	@GetMapping(value = "/execls/stuinfo/leaveInfo")
	public void getStuInfoLeaveInfoExecl(String token, LeaveDateAreaDTO leaveDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
//			leaveDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
			List<LeaveSchoolGetAllVO> leaveSchoollist = leaveService.getAdminManagementStudentLeaveSchoolByDateArea(leaveDateAreaDTO);
			List<LeaveSchoolGetAllVO1> leaveSchoolGetAllVO1List = new ArrayList<>();
			leaveSchoollist.forEach(leaveSchool->{
				LeaveSchoolGetAllVO1 leaveSchoolGetAllVO1 = setVO1ByVO(leaveSchool);
				leaveSchoolGetAllVO1List.add(leaveSchoolGetAllVO1);
			});
			easyExeclService.writeToResponse(response, "leaveSchool - " + System.currentTimeMillis(), leaveSchoolGetAllVO1List, LeaveSchoolGetAllVO1.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 2020年秋季离校申请导出
	 * 2021年夏季学期离校申请复用
	 * @param token
	 * @param backSchoolDateAreaDTO
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/stuinfo/leaveSchoolTztzAutumnInfo")
	public void getLeaveSchoolTztzAutumnInfoKeywordsExecl(String token, BackSchoolDateAreaDTO backSchoolDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);

			List<LeaveSchoolTztzAutumnGetALLVO> dataList = leaveSchoolTztzAutumnService.getAdminManagementStudentLeaveSchoolByDateArea(backSchoolDateAreaDTO);
			List<LeaveSchoolTztzAutumnGetALLVO1> leaveSchoolTztzAutumnGetALLVO1List = new ArrayList<>();
			dataList.forEach(data -> {
				LeaveSchoolTztzAutumnGetALLVO1 leaveSchoolTztzAutumnGetALLVO1 = setVO1ByVO(data);
				leaveSchoolTztzAutumnGetALLVO1List.add(leaveSchoolTztzAutumnGetALLVO1);
			});
			easyExeclService.writeToResponse(response, "leaveSchool - " + System.currentTimeMillis(), leaveSchoolTztzAutumnGetALLVO1List, LeaveSchoolTztzAutumnGetALLVO1.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 关爱信息导出
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/psychological_level")
	public void getPsychologicalLevelByParams(String token, PsychologicalLevelQueryNewParamVO psychologicalLQNPVO,HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			List<PsychologicalLevelGetByStuNumVO1> psychologicalLevelGetByStuNumVO1List = psychologicalLevelService.getPsychologicalLevelByParams1(psychologicalLQNPVO);
			easyExeclService.writeToResponse(response, "psychologicalLevel - " + System.currentTimeMillis(), psychologicalLevelGetByStuNumVO1List, PsychologicalLevelGetByStuNumVO1.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 * 心理咨询记录导出
	 * @throws IOException
	 */
	@GetMapping(value = "/execls/psychological_counseling_case")
	public void getPsychologicalLevelByParams(String token,PsychologicalCounsellingCaseSelectVO psychologicalCounsellingCaseSelectVO,HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
//		这个仅学工处及以上和咨询师可导
		if (userTokenDTO != null && (userTokenDTO.getRoles().contains(Constant.PCOUNSELOR) ||  userTokenDTO.getRoles().contains(Constant.XUEGONG))) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			List<PsychologicalCounselingCaseExportVO> psychologicalCounselingCaseExportVOS = psychologicalCounsellingCaseService.getAllCounselingCaseByParams(psychologicalCounsellingCaseSelectVO);
			easyExeclService.writeToResponse(response, "psychologicalCounselingCase - " + System.currentTimeMillis(), psychologicalCounselingCaseExportVOS, PsychologicalCounselingCaseExportVO.class);
			return;
		}
		response.getWriter().write("no permission");
	}


	/**
	 * 教职工简历导出
	 */
	@GetMapping(value = "/execls/teachingStaffResumeExport")
	public void test(String token,HttpServletResponse response, HttpServletRequest request) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token,UserTokenDTO.class);
		// 这里仅仅可以ROOT导出
		if (userTokenDTO != null && userTokenDTO.getRoles().contains("ROOT")) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			List<TeachStaffBasicExportVO> list = teachingStaffResumeService.getList();
			easyExeclService.writeMoreToResponse(response,request,"教职工简历 - " + System.currentTimeMillis(), list, TeachStaffBasicExportVO.class);
			return;
		}
		response.getWriter().write("no permission");
		return;
	}

	@GetMapping(value = "/execls/jobObtain/getList")
	public void getJobObtainList(String token, JobObtainGetParamVO jobObtainGetParamVO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			List<JobObtainGetInfoVO> jobObtainGetInfoVOList = jobObtainService.getList(jobObtainGetParamVO);
			easyExeclService.writeToResponse(response, "jobObtain - " + System.currentTimeMillis(), jobObtainGetInfoVOList, JobObtainGetInfoVO.class);
			return;
		}
		response.getWriter().write("no permission");
	}

	/**
	 *
	 * @param backSchoolGetAllVO
	 * @return
	 */
	private BackSchoolGetAllVO1 setVO1ByVO(BackSchoolGetAllVO backSchoolGetAllVO) {
		BackSchoolGetAllVO1 backSchoolGetAllVO1 = new BackSchoolGetAllVO1();
		BeanUtils.copyProperties(backSchoolGetAllVO, backSchoolGetAllVO1);
		if (backSchoolGetAllVO.getQ1() == 1) {
			backSchoolGetAllVO1.setQ1("是");
		} else backSchoolGetAllVO1.setQ1("否");
		if (backSchoolGetAllVO.getQ2() == 1) {
			backSchoolGetAllVO1.setQ2("是");
		} else backSchoolGetAllVO1.setQ2("否");
		if (backSchoolGetAllVO.getQ3() == 1) {
			backSchoolGetAllVO1.setQ3("是");
		} else backSchoolGetAllVO1.setQ3("否");
		if (backSchoolGetAllVO.getQ4() == 1) {
			backSchoolGetAllVO1.setQ4("是");
		} else backSchoolGetAllVO1.setQ4("否");

		if (backSchoolGetAllVO.getPass() == 3||backSchoolGetAllVO.getPass() == 2){
			backSchoolGetAllVO1.setPass("已批准");
			if (backSchoolGetAllVO.getPass() == 3)
		  	backSchoolGetAllVO1.setReports("已报到");
		}
		if(backSchoolGetAllVO.getPass()==1){
			backSchoolGetAllVO1.setPass("未通过");
		}
		if (!StringUtils.isBlank(backSchoolGetAllVO.getReviewedBy())){
//			CoreAdmin coreAdmin = coreAdminService.getById(backSchoolGetAllVO.getReviewedBy());
			UserInfo userInfo = userService.getUserById((backSchoolGetAllVO.getReviewedBy()));
			backSchoolGetAllVO1.setReviewedBy(userInfo.getName());
		}
    return backSchoolGetAllVO1;
	}

	/**
	 *
	 * @param leaveSchoolGetAllVO
	 * @return
	 */
	private LeaveSchoolGetAllVO1 setVO1ByVO(LeaveSchoolGetAllVO leaveSchoolGetAllVO) {
		LeaveSchoolGetAllVO1 leaveSchoolGetAllVO1 = new LeaveSchoolGetAllVO1();
		BeanUtils.copyProperties(leaveSchoolGetAllVO, leaveSchoolGetAllVO1);

		if (leaveSchoolGetAllVO.getStatus() == 1){
			leaveSchoolGetAllVO1.setStatus("已批准");
		}	if (leaveSchoolGetAllVO.getStatus() == 2){
			leaveSchoolGetAllVO1.setStatus("已拒绝");
		}
		if(leaveSchoolGetAllVO.getStatus()==0){
			leaveSchoolGetAllVO1.setStatus("未审核");
		}

		if (!StringUtils.isBlank(leaveSchoolGetAllVO.getReviewerId())){
//			CoreAdmin coreAdmin = coreAdminService.getById(leaveSchoolGetAllVO.getReviewerId());
			UserInfo userInfo = userService.getUserById(leaveSchoolGetAllVO.getReviewerId());
			if (userInfo!=null)
			leaveSchoolGetAllVO1.setReviewerId(userInfo.getName());
			else leaveSchoolGetAllVO1.setReviewerId("管理员");
		}
    return leaveSchoolGetAllVO1;
	}

	/**
	 *
	 * @param backSchoolGetAllVO
	 * @return
	 */
	private LeaveBackGetAllVO1 setVO1ByVO(LeaveBackGetAllVO backSchoolGetAllVO) {
		LeaveBackGetAllVO1 backSchoolGetAllVO1 = new LeaveBackGetAllVO1();
		BeanUtils.copyProperties(backSchoolGetAllVO, backSchoolGetAllVO1);
		if (backSchoolGetAllVO.getQ1() == 1) {
			backSchoolGetAllVO1.setQ1("是");
		} else backSchoolGetAllVO1.setQ1("否");
		if (backSchoolGetAllVO.getQ2() == 1) {
			backSchoolGetAllVO1.setQ2("是");
		} else backSchoolGetAllVO1.setQ2("否");
		if (backSchoolGetAllVO.getQ3() == 1) {
			backSchoolGetAllVO1.setQ3("是");
		} else backSchoolGetAllVO1.setQ3("否");
		if (backSchoolGetAllVO.getQ4() == 1) {
			backSchoolGetAllVO1.setQ4("是");
		} else backSchoolGetAllVO1.setQ4("否");

		if (backSchoolGetAllVO.getPass() == 3||backSchoolGetAllVO.getPass() == 2){
			backSchoolGetAllVO1.setPass("已批准");
			if (backSchoolGetAllVO.getPass() == 3)
		  	backSchoolGetAllVO1.setReports("已报到");
		}
		if(backSchoolGetAllVO.getPass()==1){
			backSchoolGetAllVO1.setPass("未通过");
		}
		if (!StringUtils.isBlank(backSchoolGetAllVO.getReviewedBy())){
			UserInfo userInfo = userService.getUserById(backSchoolGetAllVO.getReviewedBy());
			backSchoolGetAllVO.setReviewedBy(userInfo.getName());
		}
    return backSchoolGetAllVO1;
	}

	/**
	 *
	 * @param leaveSchoolTztzAutumnGetALLVO
	 * @return
	 */
	private LeaveSchoolTztzAutumnGetALLVO1 setVO1ByVO(LeaveSchoolTztzAutumnGetALLVO leaveSchoolTztzAutumnGetALLVO) {
		LeaveSchoolTztzAutumnGetALLVO1 leaveSchoolTztzAutumnGetALLVO1 = new LeaveSchoolTztzAutumnGetALLVO1();
		BeanUtils.copyProperties(leaveSchoolTztzAutumnGetALLVO, leaveSchoolTztzAutumnGetALLVO1);

		if (leaveSchoolTztzAutumnGetALLVO.getPass() == 0){
			leaveSchoolTztzAutumnGetALLVO1.setPass("未审核");
		}
		if (leaveSchoolTztzAutumnGetALLVO.getPass() == 2){
			leaveSchoolTztzAutumnGetALLVO1.setPass("已批准");
		}
		if(leaveSchoolTztzAutumnGetALLVO.getPass()==1){
			leaveSchoolTztzAutumnGetALLVO1.setPass("未通过");
		}
		if (!StringUtils.isBlank(leaveSchoolTztzAutumnGetALLVO.getReviewedBy())){
			UserInfo userInfo = userService.getUserById(leaveSchoolTztzAutumnGetALLVO.getReviewedBy());
			leaveSchoolTztzAutumnGetALLVO1.setReviewedBy(userInfo.getName());

		}
		return leaveSchoolTztzAutumnGetALLVO1;
	}



}
