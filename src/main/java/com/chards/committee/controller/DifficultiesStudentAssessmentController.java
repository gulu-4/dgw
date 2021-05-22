package com.chards.committee.controller;


import com.chards.committee.domain.DifficultiesStudentAssessment;
import com.chards.committee.service.DifficultiesStudentAssessmentService;
import com.chards.committee.vo.R;
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
@RequestMapping("/difficulties-student-assessment")
@Api(tags = "经济情况量化评估",value = "家庭经济情况量化评估接口")
public class DifficultiesStudentAssessmentController {

    @Autowired
    private DifficultiesStudentAssessmentService difficultiesStudentAssessmentService;

    @ApiOperation("学生提交家庭经济量化评估信息")
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/add")
    public R add(@Valid @RequestBody DifficultiesStudentAssessment difficultiesStudentAssessment){
        difficultiesStudentAssessment.setApply(1);
        difficultiesStudentAssessment.setCreateTime(LocalDateTime.now());
        difficultiesStudentAssessment.setUpdateTime(LocalDateTime.now());
        return R.success(difficultiesStudentAssessmentService.save(difficultiesStudentAssessment));
    }

    @ApiOperation("根据id删除评估表信息")
    @PreAuthorize("hasRole('ROOT')")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Serializable id) {
        return R.success(difficultiesStudentAssessmentService.removeById(id));
    }

    @ApiOperation("根据applyId获取详细信息")
    @PreAuthorize("hasRole('FUDAOYUAN')")
    @GetMapping("/getByApplyId/{applyId}")
    public R add(@PathVariable Serializable applyId){
        return R.success(difficultiesStudentAssessmentService.getByApplyId(applyId));
    }

}

