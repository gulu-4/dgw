package com.chards.committee.controller;

import com.chards.committee.domain.DifficultiesStudentVisit;
import com.chards.committee.service.DifficultiesStudentVisitService;
import com.chards.committee.vo.DifficultiesStudentVisitGetParamVO;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/7/6 18:51
 */
@RestController
@RequestMapping("difficulties-student-visit")
@Api(tags = "经济贫困生走访情况",value = "家庭贫困生")
public class DifficultiesStudentVisitController {

    @Autowired
    private DifficultiesStudentVisitService difficultiesStudentVisitService;

    @ApiOperation(value = "新增走访情况")
    @PreAuthorize("hasAuthority('student_select')")
    @PostMapping(value = "/add")
    public R add(@RequestBody DifficultiesStudentVisit difficultiesStudentVisit){
        difficultiesStudentVisit.setCreateTime(LocalDateTime.now());
        difficultiesStudentVisit.setUpdateTime(LocalDateTime.now());
        return R.success(difficultiesStudentVisitService.save(difficultiesStudentVisit));
    }

    @ApiOperation(value = "根据筛选条件获取相关走访记录")
    @PreAuthorize("hasAuthority('student_select')")
    @PostMapping(value = "/getByParams")
    public R getByParams(@RequestBody DifficultiesStudentVisitGetParamVO difficultiesStudentVisitGetParamVO){
        return R.success(difficultiesStudentVisitService.getStudentsVisits(difficultiesStudentVisitGetParamVO));
    }

    @ApiOperation(value = "根据id获取单个走访记录")
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping(value = "/getById/{id}")
    public R getById(@PathVariable Serializable id){
        return R.success(difficultiesStudentVisitService.getById(id));
    }

    @ApiOperation(value = "根据id删除某个走访记录")
    @PreAuthorize("hasAuthority('student_delete')")
    @GetMapping(value = "/deleteById/{id}")
    public R deleteById(@PathVariable Serializable id){
        return R.success(difficultiesStudentVisitService.removeById(id));
    }


}
