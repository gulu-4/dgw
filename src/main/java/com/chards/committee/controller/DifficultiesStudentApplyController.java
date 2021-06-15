package com.chards.committee.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.DifficultiesStudentApply;
import com.chards.committee.service.DifficultiesStudentApplyService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuSu
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/difficulties-student-apply")
@Api(tags = "经济贫困申请",value = "家庭经济经济贫困申请接口")
public class DifficultiesStudentApplyController {

    @Autowired
    private DifficultiesStudentApplyService difficultiesStudentApplyService;

    @ApiOperation("学生提交贫困申请表")
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/add")
    public R add(@RequestBody DifficultiesStudentApply difficultiesStudentApply){
        Long result = difficultiesStudentApplyService.applyAdd(difficultiesStudentApply);
        if (result == -1) {
            return R.failure(Code.DATA_ALREADY_EXISTED);
        }else{
            return R.success(result);
        }
    }

    @ApiOperation("申请表内容修改")
    @PreAuthorize("hasRole('FUDAOYUAN')")
    @PutMapping("/update")
    public R update(){
        // TODO 只更新某些字段 定义一个类接收
        return null;
    }

    @ApiOperation("学生档期是否填写过申请表和量化申请表")
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getHadApplied")
    public R getHadApplied() {
        return R.success(difficultiesStudentApplyService.getHadApplied());
    }

    @ApiOperation("根据id获取申请表详细信息")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('student_select')")
    @GetMapping("/getApplyById/{id}")
    public R getApplyById(@PathVariable Serializable id) {
        return R.success(difficultiesStudentApplyService.getById(id));
    }

    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('student_select')")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Serializable id) {
        return R.success(difficultiesStudentApplyService.deleteById(id));
    }

    @ApiOperation("辅导员列表获取")
    @PreAuthorize("hasRole('FUDAOYUAN')")
    @GetMapping("/getFirstList")
    public R getFirstList(DifficultiesStudentGetParamVO difficultiesStudentGetParamVO,Page<DifficultiesStudentGetVO> page){
        // 辅导员获取审核列表，默认为当前期数
//        LocalDateTime now = LocalDateTime.now();
//        Integer year = now.getYear();
//        if (difficultiesStudentGetParamVO.getStage() == null){
//            difficultiesStudentGetParamVO.setStage(year);
//        }
//        if (difficultiesStudentGetParamVO.getFirstCheck() == null){
//            difficultiesStudentGetParamVO.setFirstCheck(0);
//        }
        return R.success(difficultiesStudentApplyService.getFirstList(difficultiesStudentGetParamVO,page));
    }

    @ApiOperation("辅导员审核")
    @PreAuthorize("hasRole('FUDAOYUAN')")
    @PutMapping("/firstCheck")
    public R firstCheck(@Valid @RequestBody DifficultiesPassVO difficultiesPassVO){
        difficultiesPassVO.setFirstReviewer(RequestUtil.getLoginUser().getId() + "," + RequestUtil.getLoginUser().getName());
        difficultiesPassVO.setFirstReviewTime(LocalDateTime.now());
        return R.success(difficultiesStudentApplyService.check(difficultiesPassVO));
    }


    @ApiOperation("学工处列表获取")
    @PreAuthorize("hasRole('XUEGONG')")
    @GetMapping("/getSecondList")
    public R getSecondList(DifficultiesStudentGetParamVO difficultiesStudentGetParamVO,Page<DifficultiesStudentGetVO> page){
        // 学工处获取审核列表，默认为当前期数，审核状态默认为待审核
//        LocalDateTime now = LocalDateTime.now();
//        Integer year = now.getYear();
//        if (difficultiesStudentGetParamVO.getStage() == null){
//            difficultiesStudentGetParamVO.setStage(year);
//        }
        difficultiesStudentGetParamVO.setFirstCheck(1);
//        if (difficultiesStudentGetParamVO.getSecondCheck() == null){
//            difficultiesStudentGetParamVO.setSecondCheck(0);
//        }
        return R.success(difficultiesStudentApplyService.getSecondList(difficultiesStudentGetParamVO,page));
    }

    @ApiOperation("学工处审核")
    @PreAuthorize("hasRole('XUEGONG')")
    @PutMapping("/secondCheck")
    public R secondCheck(@Valid @RequestBody DifficultiesPassVO difficultiesPassVO){
        if (difficultiesPassVO.getFirstCheck() == 0){
            difficultiesPassVO.setFirstCheck(1);
        }
        difficultiesPassVO.setSecondReviewer(RequestUtil.getLoginUser().getId() + "," + RequestUtil.getLoginUser().getName());
        difficultiesPassVO.setSecondReviewTime(LocalDateTime.now());
        return R.success(difficultiesStudentApplyService.check(difficultiesPassVO));
    }


}

