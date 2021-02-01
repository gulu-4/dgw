package com.chards.committee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.service.PsychologicalCounsellingCaseService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
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
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping("/insert")
    public R insert(@RequestBody @Valid PsychologicalCounsellingCaseInsertVO psychologicalCounsellingCaseInsertVO) {
        if (stuInfoService.isContainsReturnIsWork(psychologicalCounsellingCaseInsertVO.getStuNum())) {
            PsychologicalCounsellingCase psychologicalCounsellingCase = new PsychologicalCounsellingCase();
            BeanUtils.copyProperties(psychologicalCounsellingCaseInsertVO,psychologicalCounsellingCase);
            // 填写者和填写时间都是后台获取
            psychologicalCounsellingCase.setRecordedTime(LocalDateTime.now());
            psychologicalCounsellingCase.setRecorder(RequestUtil.getLoginUser().getId());
            return R.success(psychologicalCounsellingCaseService.save(psychologicalCounsellingCase));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }


    /**
     * 通过学生学号获取所有该学生咨询记录和测试记录
     * @return 某位同学的基本信息中嵌套其心理咨询记录和心理测评结果
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getAll")
    public R getAll(@RequestParam String stuNum){
        if (stuInfoService.isContainsReturnIsWork(stuNum)){
            return R.success(psychologicalCounsellingCaseService.getAll(stuNum));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }


    /**
     * 通过学生学号或时间段获取所有咨询记录和测试记录详情
     * 此模块只给心理中心用，因此没有做单独的权限管理的处理(有时间还是有必要给补上)
     * poplar 21.1.1
     * @return 分页的咨询记录列表，每条咨询记录中嵌套学生基本信息和心理测评记录
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getAllCounselingCaseByParams")
    public R getAll(PsychologicalCounsellingCaseSelectVO psychologicalCounsellingCaseSelectVO,
                    Page<PsychologicalCounselingCaseDetailVO> page){
//        System.out.println(psychologicalCounsellingCaseSelectVO);
//        System.out.println(page);
        return R.success(psychologicalCounsellingCaseService.getAllCounselingCaseByParams(page,psychologicalCounsellingCaseSelectVO));
    }
}