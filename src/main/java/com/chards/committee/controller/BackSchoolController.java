package com.chards.committee.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.BackSchool;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.BackSchoolAdminGetAndUpdateDTO;
import com.chards.committee.dto.PassInfoDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.BackSchoolService;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.service.UserService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.BackSchoolGetAllVO;
import com.chards.committee.vo.BackSchoolPassVO;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * (BackSchool)表控制层
 *
 * @author chards
 * @since 2020-08-17 21:47:46
 */
@RestController
@RequestMapping("/backSchools")
@Api(tags = "返校相关接口", value = "返校接口")
public class BackSchoolController {
    /**
     * 服务对象
     */
    @Autowired
    private BackSchoolService backSchoolService;

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private CoreAdminService coreAdminService;

    @Autowired
    private UserService userService;

    /**
     * 分页查询所有数据
     *
     * @param pass 状态
     * @return 所有数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping
    @ApiOperation(value = "【管理员】分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pass", value = "审核状态(0未审；1拒绝；2通过；3已报到)", required = false, dataType = "Integer"),
    })

    public R selectAll(Page<BackSchoolGetAllVO> page,Integer pass) {
        BackSchoolAdminGetAndUpdateDTO backSchoolAdminGetAndUpdateDTO = new BackSchoolAdminGetAndUpdateDTO();
//        backSchoolAdminGetAndUpdateDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
        backSchoolAdminGetAndUpdateDTO.setPass(pass);
        return R.success(backSchoolService.getAdminManagementStudentBackSchool(page,backSchoolAdminGetAndUpdateDTO));
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @GetMapping("/info")
    @ApiOperation(value = "【学】获取个人的返校申请数据")
    public R getBackInfoById() {
        UserInfo userInfo = RequestUtil.getLoginUser();
        return R.success(backSchoolService.getById(userInfo.getId()));
    }

    /**
     * 新增数据
     *
     * @param backSchool 实体对象
     * @return 新增结果
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @PostMapping
    @ApiOperation(value = "【学】提交新的返校申请数据")
    public R insert(@RequestBody BackSchool backSchool) {
        UserInfo userInfo = RequestUtil.getLoginUser();
        backSchool.setStuNum(userInfo.getId());
        backSchool.setPass(0);   //   0代表未批
        return R.success(backSchoolService.save(backSchool));
    }


    /**
     * 新增数据
     *
     * @param backSchool 实体对象
     * @return 新增结果
     */
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/resubmitPass")
    @ApiOperation(value = "【学】重新提交返校申请数据")
    public R updatePassInfo(@RequestBody BackSchool backSchool) {
        UserInfo userInfo = RequestUtil.getLoginUser();
        backSchool.setStuNum(userInfo.getId());
        backSchool.setPass(0);   //   0代表未批
        return R.success(backSchoolService.save(backSchool));
    }


    /**
     * 修改一条数据
     *
     * @param backSchoolPassVO 实体对象
     * @return 修改结果
     */
    @PreAuthorize("hasAuthority('student_update')")
    @PostMapping("/update")
    @ApiOperation(value = "【管】管理员审核返校数据")
    public R update(@Valid @RequestBody BackSchoolPassVO backSchoolPassVO) {
        if (stuInfoService.isWithinDataScope(backSchoolPassVO.getStuNum())) {
            BackSchool backSchool = new BackSchool();
            backSchool.setStuNum(backSchoolPassVO.getStuNum());
            backSchool.setPass(backSchoolPassVO.getPass());
            backSchool.setReviewedBy(RequestUtil.getId());
            backSchool.setReviewedTime(LocalDateTime.now());
            return R.success(backSchoolService.updateById(backSchool));
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
    @ApiOperation(value = "【管】一键审核所管辖的所有学生")
    public R updateAll(Integer pass) {
        BackSchoolAdminGetAndUpdateDTO backSchoolAdminGetAndUpdateDTO = new BackSchoolAdminGetAndUpdateDTO();
//        backSchoolAdminGetAndUpdateDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
        backSchoolAdminGetAndUpdateDTO.setUserDataScopeList(RequestUtil.getUserDataScopeListForFuzzyQuery());
        backSchoolAdminGetAndUpdateDTO.setPass(pass == null ? 2 : pass == 2 ? 2 : 1);
        backSchoolAdminGetAndUpdateDTO.setReviewedBy(RequestUtil.getId());
        backSchoolAdminGetAndUpdateDTO.setReviewedTime(LocalDateTime.now());
        return R.success(backSchoolService.updateAdminManagementStudentBackSchoolPass(backSchoolAdminGetAndUpdateDTO));
    }


    /**
     * 学生删除自己已填写返校数据
     *
     * @return 删除结果
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @PostMapping("/delete")
    @ApiOperation(value = "【学】删除自己的申请数据")
    public R deleteStu() {
        UserInfo userInfo = RequestUtil.getLoginUser();
        return R.success(backSchoolService.removeById(userInfo.getId()));
    }

    /**
     * 辅导员删除自己管理学生返校数据
     *
     * @return 删除结果
     */
	@PreAuthorize("hasAuthority('teacher_own')")
	@PostMapping("/deleteStu/{id}")
    @ApiOperation(value = "辅导员删除自己学生的申请")
	public R deleteStuBackInfo(@PathVariable String id) {
        if (backSchoolService.getById(id)==null){
          BusinessException.error(Code.RESULT_DATA_NONE);
        }
        if (stuInfoService.isWithinDataScope(id)) {
            return R.success(backSchoolService.removeById(id));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
	}

    /**
     * 获取审核步骤信息
     *
     * @return
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @GetMapping("/passInfo")
    @ApiOperation(value = "【学】获取审核步骤信息")
    public R getPassInfo() {
        UserInfo userInfo = RequestUtil.getLoginUser();
        BackSchool bs = backSchoolService.getById(userInfo.getId());
        return bs != null ? R.success(passInfo(bs.getPass())) : R.success("未填写");
    }

    /**
     * 返回具体的返校详情信息用于pdf生成
     *
     * @return
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @GetMapping("/passInfoPdf")
    @ApiOperation(value = "返回具体的返校详情信息用于pdf生成")
    public R getPassInfoPdf() {
        UserInfo userInfo = RequestUtil.getLoginUser();
        BackSchool bs = backSchoolService.getById(userInfo.getId());
        StuInfo stuInfo = stuInfoService.getById(userInfo.getId());
//        CoreAdmin coreAdmin = coreAdminService.getById(bs.getReviewedBy());
        UserInfo reviewedByUserInfo = userService.getUserById(bs.getReviewedBy());
        return  R.success(getPassInfoDTO(bs,stuInfo,reviewedByUserInfo));
    }

    /**
     * 微信小程序 -报道
     * @return
     */
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @PostMapping("/reports")
    @ApiOperation(value = "【学】报道")
    public R setReports(){
        BackSchool backSchool = backSchoolService.getById(RequestUtil.getId());
        backSchool.setPass(3); //3 是已报道
        return R.success( backSchoolService.updateById(backSchool));
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

    private PassInfoDTO getPassInfoDTO(BackSchool bs ,StuInfo stuInfo, UserInfo userInfo){
        PassInfoDTO passInfoDTO=new PassInfoDTO();
        passInfoDTO.setStuId(stuInfo.getId());
        passInfoDTO.setName(stuInfo.getName());
        passInfoDTO.setDate(bs.getDate());
        passInfoDTO.setLoc(bs.getPickupLoc());
        passInfoDTO.setReviewedBy(userInfo.getName());
        passInfoDTO.setReviewedTime(bs.getReviewedTime());
        passInfoDTO.setEmergencyCallee(bs.getEmergencyCallee());
        passInfoDTO.setDepartment(stuInfo.getDepartment());
       return passInfoDTO;
    }


}