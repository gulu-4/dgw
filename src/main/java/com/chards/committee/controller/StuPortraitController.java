package com.chards.committee.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.StuPortrait;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.service.StuPortraitService;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import com.chards.committee.vo.StuPortraitInsertVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * (StuPortrait)表控制层
 *
 * @author chards
 * @since 2020-08-01 16:05:10
 */
@RestController
@RequestMapping("/stu-portraits")
public class StuPortraitController {
    /**
     * 服务对象
     */
    @Autowired
    private StuPortraitService stuPortraitService;
    @Autowired
    StuInfoService stuInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param stuId 学生id
     * @return 所有数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping
    public R selectAll(Page<StuPortrait> page, @RequestParam("stuId") String stuId, @RequestParam(value = "kind", defaultValue = "-1") Integer kind) {
        if (stuInfoService.isContainsReturnIsWork(stuId)) {
            StuPortrait stuPortrait = new StuPortrait();
            stuPortrait.setStuNum(stuId);
            if (kind != -1) {
                stuPortrait.setKind(kind);
            }
            return R.success(stuPortraitService.page(page, new QueryWrapper<>(stuPortrait)));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/{id}")
    public R selectOne(@PathVariable Serializable id) {
        StuPortrait stuPortrait = stuPortraitService.getById(id);
        Assert.notNull(stuPortrait, Code.RESULT_DATA_NONE);
        StuInfo stuInfo = stuInfoService.getById(stuPortrait.getStuNum());
        return stuInfo == null || stuInfoService.isWork(stuInfo) ? R.success(stuPortrait) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 新增数据
     *
     * @param stuPortraitInsertVO 实体对象
     * @return 新增结果
     */
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping
    public R insert(@RequestBody @Valid StuPortraitInsertVO stuPortraitInsertVO) {
        if (stuInfoService.isContainsReturnIsWork(stuPortraitInsertVO.getStuNum())) {
            StuPortrait stuPortrait = new StuPortrait();
            BeanUtils.copyProperties(stuPortraitInsertVO, stuPortrait);
            stuPortrait.setNumber(RequestUtil.getId());
            stuPortrait.setCreateTime(LocalDateTime.now());
            return R.success(stuPortraitService.save(stuPortrait));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }
}