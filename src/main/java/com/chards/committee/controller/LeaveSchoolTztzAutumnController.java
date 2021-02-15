/**
 * 在10.4版（21年2月）的大重构中，本某块未重构，若日后要使用，还得一个个测试修改一下！
 */
package com.chards.committee.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.LeaveSchoolTztzAutumn;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.LeaveSchoolTztzAutumnAdminGetAndUpdateDTO;
import com.chards.committee.dto.PassInfoDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.LeaveSchoolTztzAutumnService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * (LeaveSchoolTztzAutumnMapper) 表控制层
 *
 * @author LiuSu
 * @create 2021/1/4 16:13
 */
@RestController
@RequestMapping("/leaveSchoolTztzAutumn")
public class LeaveSchoolTztzAutumnController {
    /**
     * 服务对象
     */
    @Autowired
    private LeaveSchoolTztzAutumnService leaveSchoolTztzAutumnService;

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private CoreAdminService coreAdminService;

    /**
     * 分页查询所有数据
     *
     * @param pass 状态
     * @return 所有数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping
    public R selectAll(Page<LeaveSchoolTztzAutumnGetALLVO> page, LeaveSchoolTztzQueryParamVO leaveSchoolTztzQueryParamVO) {
//        LeaveSchoolTztzAutumnAdminGetAndUpdateDTO leaveSchoolTztzAutumnAdminGetAndUpdateDTO = new LeaveSchoolTztzAutumnAdminGetAndUpdateDTO();
//        leaveSchoolTztzAutumnAdminGetAndUpdateDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
//        leaveSchoolTztzAutumnAdminGetAndUpdateDTO.setPass(pass);
        leaveSchoolTztzQueryParamVO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
        return R.success(leaveSchoolTztzAutumnService.getAdminManagementStudentLeaveSchoolTztzAutumn(page,leaveSchoolTztzQueryParamVO));
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @GetMapping("/info")
    public R getLeaveInfoById() {
        UserInfo userInfo = RequestUtil.getLoginUser();
        return R.success(leaveSchoolTztzAutumnService.getById(userInfo.getId()));
    }

    /**
     * 新增数据
     *
     * @param leaveSchoolTztzAutumn 实体对象
     * @return 新增结果
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @PostMapping
    public R insert(@RequestBody LeaveSchoolTztzAutumn leaveSchoolTztzAutumn) {
        UserInfo userInfo = RequestUtil.getLoginUser();
        leaveSchoolTztzAutumn.setStuNum(userInfo.getId());
        leaveSchoolTztzAutumn.setPass(0);   //   0代表未审核
        return R.success(leaveSchoolTztzAutumnService.save(leaveSchoolTztzAutumn));
    }


    /**
     * 学生重新提交数据
     *
     * @param leaveSchoolTztzAutumn 实体对象
     * @return 重新提交（要先删除申请数据才能请求成功）
     */
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/resubmitPass")
    public R updatePassInfo(@RequestBody LeaveSchoolTztzAutumn leaveSchoolTztzAutumn) {
        UserInfo userInfo = RequestUtil.getLoginUser();
        leaveSchoolTztzAutumn.setStuNum(userInfo.getId());
        leaveSchoolTztzAutumn.setPass(0);   //   0代表未批
        return R.success(leaveSchoolTztzAutumnService.save(leaveSchoolTztzAutumn));
    }


    /**
     * 修改一条数据（审核）
     *
     * @param leaveSchoolPassVO 实体对象，含学号和pass
     * @return 修改结果
     */
    @PreAuthorize("hasAuthority('student_update')")
    @PostMapping("/update")
    public R update(@Valid @RequestBody BackSchoolPassVO leaveSchoolPassVO) {
        if (stuInfoService.isContainsReturnIsWork(leaveSchoolPassVO.getStuNum())) {
            LeaveSchoolTztzAutumn leaveSchoolTztzAutumn = new LeaveSchoolTztzAutumn();
            leaveSchoolTztzAutumn.setStuNum(leaveSchoolPassVO.getStuNum());
            leaveSchoolTztzAutumn.setPass(leaveSchoolPassVO.getPass());
            leaveSchoolTztzAutumn.setReviewedBy(RequestUtil.getId());
            leaveSchoolTztzAutumn.setReviewedTime(LocalDateTime.now());
            return R.success(leaveSchoolTztzAutumnService.updateById(leaveSchoolTztzAutumn));
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
        LeaveSchoolTztzAutumnAdminGetAndUpdateDTO leaveSchoolTztzAutumnAdminGetAndUpdateDTO = new LeaveSchoolTztzAutumnAdminGetAndUpdateDTO();
        leaveSchoolTztzAutumnAdminGetAndUpdateDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
        leaveSchoolTztzAutumnAdminGetAndUpdateDTO.setPass(pass == null ? 2 : pass == 2 ? 2 : 1);
        leaveSchoolTztzAutumnAdminGetAndUpdateDTO.setReviewedTime(LocalDateTime.now());
        leaveSchoolTztzAutumnAdminGetAndUpdateDTO.setReviewedBy(RequestUtil.getId());
        return R.success(leaveSchoolTztzAutumnService.updateAdminManagementStudentLeaveSchoolPass(leaveSchoolTztzAutumnAdminGetAndUpdateDTO));
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
        return R.success(leaveSchoolTztzAutumnService.removeById(userInfo.getId()));
    }

    /**
     * 辅导员删除自己管理学生返校数据
     *
     * @return 删除结果
     */
	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping("/deleteStu/{id}")
	public R deleteStuLeaveInfo(@PathVariable String id) {
      if (leaveSchoolTztzAutumnService.getById(id)==null){
          BusinessException.error(Code.RESULT_DATA_NONE);
      }
      return R.success(leaveSchoolTztzAutumnService.removeById(id));
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
        LeaveSchoolTztzAutumn lSTA = leaveSchoolTztzAutumnService.getById(userInfo.getId());
        return lSTA != null ? R.success(passInfo(lSTA.getPass())) : R.success("未填写");
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
        LeaveSchoolTztzAutumn lSTA = leaveSchoolTztzAutumnService.getById(userInfo.getId());
        StuInfo stuInfo = stuInfoService.getById(userInfo.getId());
        CoreAdmin coreAdmin = coreAdminService.getById(lSTA.getReviewedBy());
        return  R.success(getPassInfoDTO(lSTA,stuInfo,coreAdmin));
    }

//    /**
//     * 微信小程序 -报道
//     * @return
//     */
//    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
//    @PostMapping("/reports")
//    public R setReports(){
//        LeaveSchoolTztzAutumn lSTA = leaveSchoolTztzAutumnService.getById(userInfo.getId());
//        lSTA.setPass(3); //3 是已报道
//        return R.success( backSchoolService.updateById(backSchool));
//    }



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
     * 这里先使用原返校相关内容，之后再确定需要信息进行更新
     * @param lSTA
     * @param stuInfo
     * @param coreAdmin
     * @return
     */
    private PassInfoDTO getPassInfoDTO(LeaveSchoolTztzAutumn lSTA , StuInfo stuInfo, CoreAdmin coreAdmin){
        PassInfoDTO passInfoDTO=new PassInfoDTO();
        passInfoDTO.setStuId(stuInfo.getId());
        passInfoDTO.setName(stuInfo.getName());
        passInfoDTO.setDate(lSTA.getDate());
        passInfoDTO.setLoc(lSTA.getLoc());
        passInfoDTO.setReviewedBy(coreAdmin.getName());
        passInfoDTO.setReviewedTime(lSTA.getReviewedTime());
        passInfoDTO.setEmergencyCallee(lSTA.getEmergencyCallee());
        passInfoDTO.setDepartment(stuInfo.getDepartment());
       return passInfoDTO;
    }


}