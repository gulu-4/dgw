package com.chards.committee.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chards.committee.constant.Constant;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.CoreAdminDTO;
import com.chards.committee.dto.LeaveBackDateAreaDTO;
import com.chards.committee.dto.LeaveDateAreaDTO;
import com.chards.committee.dto.StuInfoPageDTO;
import com.chards.committee.dto.UserTokenDTO;
import com.chards.committee.service.*;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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

	@Value("${filepath}")
	String path;
	@Value("${defaultPhoto}")
	String DEFAULE_IMG;

	@Autowired
	EasyExeclService easyExeclService;

	/**
	 * 我觉得和权限判断有关的 都是走那一套
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
					if (RequestUtil.getId().equals(id) || stuInfoService.isContainsReturnIsWork(id)) {
						return getFileByte(file);
					}
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

	@GetMapping(value = "/execls/stuinfo/keywords")
	public void getStuInfoKeywordsExecl(String token, StuInfoPageDTO stuInfoPageDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			stuInfoPageDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
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

	@GetMapping(value = "/execls/stuinfo/backSchoolInfo")
	public void getStuInfoAdvanceKeywordsExecl(String token, BackSchoolDateAreaDTO backSchoolDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			backSchoolDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
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



	@GetMapping(value = "/execls/stuinfo/leavebackSchoolInfo")
	public void getStuInfoAdvanceKeywordsExecl(String token, LeaveBackDateAreaDTO backSchoolDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			backSchoolDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
			List<LeaveBackGetAllVO> dataList = leaveBackService.getAdminManagementStudentBackSchoolByDateArea(backSchoolDateAreaDTO);
			List<LeaveBackGetAllVO1> backSchoolGetAllVO1List = new ArrayList<>();
			dataList.forEach(data -> {
				LeaveBackGetAllVO1 backSchoolGetAllVO1 = setVO1ByVO(data);
				backSchoolGetAllVO1List.add(backSchoolGetAllVO1);
			});
			easyExeclService.writeToResponse(response, "leavebackSchool - " + System.currentTimeMillis(), backSchoolGetAllVO1List, LeaveBackGetAllVO1.class);
			return;
		}
		response.getWriter().write("no permission");
	}


	@GetMapping(value = "/execls/stuinfo/leaveInfo")
	public void getStuInfoLeaveInfoExecl(String token, LeaveDateAreaDTO leaveDateAreaDTO, HttpServletResponse response) throws IOException {
		UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
		if (userTokenDTO != null && userTokenDTO.getPermissionsList().contains(Constant.PERMISSION_STUDENT_SELECT)) {
			RequestUtil.setUserTokenDTO(userTokenDTO);
			leaveDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
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
			backSchoolDateAreaDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
			List<LeaveSchoolTztzAutumnGetALLVO> dataList = leaveSchoolTztzAutumnService.getAdminManagementStudentLeaveSchoolByDateArea(backSchoolDateAreaDTO);
			List<LeaveSchoolTztzAutumnGetALLVO1> leaveSchoolTztzAutumnGetALLVO1List = new ArrayList<>();
			dataList.forEach(data -> {
				LeaveSchoolTztzAutumnGetALLVO1 leaveSchoolTztzAutumnGetALLVO1 = setVO1ByVO(data);
				leaveSchoolTztzAutumnGetALLVO1List.add(leaveSchoolTztzAutumnGetALLVO1);
			});
			easyExeclService.writeToResponse(response, "leaveSchoolTztzAutumn - " + System.currentTimeMillis(), leaveSchoolTztzAutumnGetALLVO1List, LeaveSchoolTztzAutumnGetALLVO1.class);
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
			CoreAdmin coreAdmin = coreAdminService.getById(backSchoolGetAllVO.getReviewedBy());
			backSchoolGetAllVO1.setReviewedBy(coreAdmin.getName());
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
			CoreAdmin coreAdmin = coreAdminService.getById(leaveSchoolGetAllVO.getReviewerId());
			if (coreAdmin!=null)
			leaveSchoolGetAllVO1.setReviewerId(coreAdmin.getName());
			else leaveSchoolGetAllVO1.setReviewerId("5704");
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
			CoreAdmin coreAdmin = coreAdminService.getById(backSchoolGetAllVO.getReviewedBy());
			backSchoolGetAllVO1.setReviewedBy(coreAdmin.getName());
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
			CoreAdmin coreAdmin = coreAdminService.getById(leaveSchoolTztzAutumnGetALLVO.getReviewedBy());
			leaveSchoolTztzAutumnGetALLVO1.setReviewedBy(coreAdmin.getName());
		}
		return leaveSchoolTztzAutumnGetALLVO1;
	}



}
