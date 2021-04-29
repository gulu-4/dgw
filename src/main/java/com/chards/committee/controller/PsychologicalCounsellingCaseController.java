package com.chards.committee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.service.PsychologicalCounsellingCaseService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/11 14:50
 */
@RequestMapping("/psychological_counselling_case")
@RestController
@Api(tags = "心理咨询管理")
public class PsychologicalCounsellingCaseController {
    /**
     * 服务对象
     */
    @Autowired
    private PsychologicalCounsellingCaseService psychologicalCounsellingCaseService;

    @Autowired
    private StuInfoService stuInfoService;

    /**
     * 新增数据
     *
     * @param psychologicalCounsellingCaseInsertVO 请求内容
     * @return 新增结果
     */
    @PreAuthorize("hasRole('PCOUNSELOR')")
    @PostMapping("/insert")
    @ApiOperation(value = "新增心理咨询记录")
    public R insert(@RequestBody @Valid PsychologicalCounsellingCaseInsertVO psychologicalCounsellingCaseInsertVO) {
        PsychologicalCounsellingCase psychologicalCounsellingCase = new PsychologicalCounsellingCase();
        BeanUtils.copyProperties(psychologicalCounsellingCaseInsertVO,psychologicalCounsellingCase);
        // 填写者和填写时间都是后台获取
        psychologicalCounsellingCase.setRecordedTime(LocalDateTime.now());
        psychologicalCounsellingCase.setRecorder(RequestUtil.getLoginUser().getId());
        return R.success(psychologicalCounsellingCaseService.save(psychologicalCounsellingCase));

    }


    /**
     * 通过学生学号获取所有该学生咨询记录和测试记录
     * @return 某位同学的基本信息中嵌套其心理咨询记录和心理测评结果
     */
    @PreAuthorize("hasRole('PCOUNSELOR') or hasRole('XUEGONG')")
    @GetMapping("/getAll")
    @ApiOperation(value = "通过学生学号获取所有该学生咨询记录和测试记录")
    public R getAll(@RequestParam String stuNum){
        return R.success(psychologicalCounsellingCaseService.getAll(stuNum));
    }


    /**
     * 通过学生学号或时间段获取所有咨询记录和测试记录详情
     * 此模块只给心理中心用，因此没有做单独的权限管理的处理(有时间还是有必要给补上)
     * poplar 21.1.1
     * @return 分页的咨询记录列表，每条咨询记录中嵌套学生基本信息和心理测评记录
     */
    @PreAuthorize("hasRole('PCOUNSELOR') or hasRole('XUEGONG')")
    @GetMapping("/getAllCounselingCaseByParams")
    @ApiOperation(value = "通过学生学号或时间段获取所有咨询记录和测试记录详情")
    public R getAll(PsychologicalCounsellingCaseSelectVO psychologicalCounsellingCaseSelectVO,
                    Page<PsychologicalCounselingCaseDetailVO> page){
        return R.success(psychologicalCounsellingCaseService.getAllCounselingCaseByParams(page,psychologicalCounsellingCaseSelectVO));
    }

    /**
     * 更新心理咨询
     * @param psychologicalCounsellingCaseInsertVO
     * @return
     */
    @PreAuthorize("hasRole('PCOUNSELOR') or hasRole('XUEGONG')")
    @PutMapping("/updateCounsellingCase")
    @ApiOperation(value = "更新心理咨询")
    public R update(@RequestBody @Valid PsychologicalCounsellingCaseInsertVO psychologicalCounsellingCaseInsertVO){
        PsychologicalCounsellingCase psychologicalCounsellingCase = new PsychologicalCounsellingCase();
        BeanUtils.copyProperties(psychologicalCounsellingCaseInsertVO,psychologicalCounsellingCase);
        psychologicalCounsellingCase.setRecordedTime(LocalDateTime.now());
        psychologicalCounsellingCase.setRecorder(RequestUtil.getLoginUser().getId());
        return R.success(psychologicalCounsellingCaseService.updateById(psychologicalCounsellingCase));
    }

    /**
     * 通过id删除心理咨询记录
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('PCOUNSELOR') or hasRole('XUEGONG')")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "通过id删除心理咨询记录")
    public R delete(@PathVariable String id){
        return R.success(psychologicalCounsellingCaseService.removeById(id));
    }
}