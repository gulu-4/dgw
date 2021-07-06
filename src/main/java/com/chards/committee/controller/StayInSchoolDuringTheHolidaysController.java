package com.chards.committee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.StayInSchoolDuringTheHolidays;
import com.chards.committee.mapper.StayInSchoolDuringTheHolidaysMapper;
import com.chards.committee.service.StayInSchoolDuringTheHolidaysService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysCheckVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysInfoVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysInsertVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysQueryParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stayInSchoolDuringTheHolidays")
@Api(tags = "假期留校信息模块【暂弃】")
public class StayInSchoolDuringTheHolidaysController {
    @Autowired
    StayInSchoolDuringTheHolidaysService stayInSchoolDuringTheHolidaysService;

    @Autowired
    StuInfoService stuInfoService;

    @Autowired
    StayInSchoolDuringTheHolidaysMapper stayInSchoolDuringTheHolidaysMapper;

    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @GetMapping
    @ApiOperation(value = "查看自己的留校申请")
    public R get(){
        QueryWrapper<StayInSchoolDuringTheHolidays> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_num", RequestUtil.getId());
        StayInSchoolDuringTheHolidays stayInSchoolDuringTheHolidays = stayInSchoolDuringTheHolidaysMapper.selectOne(queryWrapper);
        if (stayInSchoolDuringTheHolidays != null){
            return R.success(stayInSchoolDuringTheHolidays);
        }
        return R.failure("无留校数据");
    }

    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @PostMapping
    @ApiOperation(value = "新增留校信息")
    public R create(@RequestBody @Valid StayInSchoolDuringTheHolidaysInsertVO stayInSchoolDuringTheHolidaysInsertVO){
        QueryWrapper<StayInSchoolDuringTheHolidays> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_num", RequestUtil.getId());
        StayInSchoolDuringTheHolidays stayInSchoolDuringTheHolidays0 = stayInSchoolDuringTheHolidaysMapper.selectOne(queryWrapper);

        if (stayInSchoolDuringTheHolidays0 != null){
            return R.failure(Code.DATA_ALREADY_EXISTED);
        }
        StayInSchoolDuringTheHolidays stayInSchoolDuringTheHolidays = new StayInSchoolDuringTheHolidays();
        BeanUtils.copyProperties(stayInSchoolDuringTheHolidaysInsertVO, stayInSchoolDuringTheHolidays);
        stayInSchoolDuringTheHolidays.setStuNum(RequestUtil.getId());
        stayInSchoolDuringTheHolidays.setPass(0);
        stayInSchoolDuringTheHolidays.setCreated_time(LocalDateTime.now());
        return R.success(stayInSchoolDuringTheHolidaysService.save(stayInSchoolDuringTheHolidays));
    }

    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
    @PostMapping("/delete/own")
    @ApiOperation(value = "删除个人的留校信息")
    public R delete_own(){
        StayInSchoolDuringTheHolidays stayInSchoolDuringTheHolidays = stayInSchoolDuringTheHolidaysService.getById(RequestUtil.getId());
        if (stayInSchoolDuringTheHolidays != null){
            if (stayInSchoolDuringTheHolidays.getPass()!=2)
                return R.success(stayInSchoolDuringTheHolidays);
            else
                return R.failure("审核通过的留校信息不能自行删除，若需修改，请联系辅导员老师进行删除！");
        }
        return R.failure("无留校数据");
    }

    @PreAuthorize("hasAnyAuthority('student_select')")
    @GetMapping("/admin/list")
    @ApiOperation(value = "管理员查看所有留校信息")
    public R adminSelectAll(Page<StayInSchoolDuringTheHolidaysInfoVO> page, StayInSchoolDuringTheHolidaysQueryParamVO stayInSchoolDuringTheHolidaysQueryParamVO){
        Page<StayInSchoolDuringTheHolidaysInfoVO> stayInSchoolDuringTheHolidaysInfoVOList = stayInSchoolDuringTheHolidaysService.adminSelectStayInSchoolDuringTheHolidays(page, stayInSchoolDuringTheHolidaysQueryParamVO);
        return R.success(stayInSchoolDuringTheHolidaysInfoVOList);
    }

    @PreAuthorize("hasAnyAuthority('student_select')")
    @PostMapping("/admin/check")
    @ApiOperation("管理员对留校申请做审核")
    public R adminCheck(@RequestBody @Valid StayInSchoolDuringTheHolidaysCheckVO stayInSchoolDuringTheHolidaysCheckVO){
        if (stuInfoService.isWithinDataScope(stayInSchoolDuringTheHolidaysCheckVO.getStuNum())){
            StayInSchoolDuringTheHolidays stayInSchoolDuringTheHolidays = stayInSchoolDuringTheHolidaysService.getById(stayInSchoolDuringTheHolidaysCheckVO.getStuNum());
            if (stayInSchoolDuringTheHolidays != null){
                stayInSchoolDuringTheHolidays.setPass(stayInSchoolDuringTheHolidaysCheckVO.getPass());
                stayInSchoolDuringTheHolidays.setChecked_by(RequestUtil.getId());
                stayInSchoolDuringTheHolidays.setChecked_time(LocalDateTime.now());
                return R.success(stayInSchoolDuringTheHolidaysService.save(stayInSchoolDuringTheHolidays));
            }
            return R.failure(Code.RESULT_DATA_NONE);
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

}
