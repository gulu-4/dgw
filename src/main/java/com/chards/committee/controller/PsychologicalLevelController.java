package com.chards.committee.controller;

import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.service.PsychologicalLevelService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LiuSu
 * @create 2020/12/11 18:58
 */
@RestController
@RequestMapping("/psychological_level")
public class PsychologicalLevelController {
    @Autowired
    private PsychologicalLevelService psychologicalLevelService;

    @Autowired
    private StuInfoService stuInfoService;

    /**
     * 新增心理定级
     */
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping("/insert")
    public R insert(@RequestBody @Valid PsychologicalLevelInsertVO psychologicalLevelInsertVO){
        if (stuInfoService.isContainsReturnIsWork(psychologicalLevelInsertVO.getStuNum())) {
            PsychologicalLevel psychologicalLevel = new PsychologicalLevel();
            BeanUtils.copyProperties(psychologicalLevelInsertVO,psychologicalLevel);
            // 填写者和填写时间都是后台获取
            psychologicalLevel.setRecordedTime(LocalDateTime.now());
            psychologicalLevel.setRecorder(RequestUtil.getLoginUser().getId());
            return R.success(psychologicalLevelService.save(psychologicalLevel));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 辅导员及学工处获取定级记录
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getPsychologicalLevelPage")
    public R getPsychologicalLevelTest(@RequestParam(value = "checkStatus",defaultValue = "0") Integer checkStatus,
                                       @RequestParam(value = "stuNum", defaultValue = "") String stuNum,
                                       Page<PsychologicalLevel> page){
        if (!stuNum.equals("")) {
            if (!stuInfoService.isContainsReturnIsWork(stuNum)){
                return R.failure(Code.PERMISSION_NO_ACCESS);
            }
        }
        // 获取登录账号的id
        String recorder = RequestUtil.getLoginUser().getId();
        // 首先判断是否是学工处账号
        List<String> roles = RequestUtil.getRoles();
        for (String role : roles) {
            if (role.equals("XUEGONG")) {
                recorder = "";
                break;
            }
        }
        return R.success(psychologicalLevelService.getPsychologicalLevelPage(page, checkStatus, recorder, stuNum));
    }

    /**
     * 通过id获取顶级记录
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getPsyLevelById/{id}")
    public R getPsyLevelById(@PathVariable Serializable id) {
        PsychologicalLevel psychologicalLevel = psychologicalLevelService.getById(id);
        Assert.notNull(psychologicalLevel, Code.RESULT_DATA_NONE);
        StuInfo stuInfo = stuInfoService.getById(psychologicalLevel.getStuNum());
        return stuInfo == null || stuInfoService.isWork(stuInfo) ? R.success(psychologicalLevel) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 通过学生学号获取定级记录
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getPsychologicalLevelGetByStuNumVO")
    public R getPsyLevelById(@RequestParam String stuNum) {
        if (stuInfoService.isContainsReturnIsWork(stuNum)) {
            return R.success(psychologicalLevelService.getPsychologicalLevelGetByStuNumVO(stuNum));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 心理中心对定级表的审核，通过记录id
     */
    @PreAuthorize("hasRole('XUEGONG')")
    @PutMapping("/chekStatus")
    public R checkStatus(@RequestBody PsychologicalLevelCheckStatusVO psychologicalLevelCheckStatusVO){
        if (psychologicalLevelCheckStatusVO.getId() == null){
            return R.failure(Code.PARAM_NOT_COMPLETE);
        }
        PsychologicalLevel psychologicalLevel = psychologicalLevelService.getById(psychologicalLevelCheckStatusVO.getId());
        if (psychologicalLevel == null) {
            return R.failure("定级记录不存在");
        }
        String reviewer = RequestUtil.getLoginUser().getId();
        return R.success(psychologicalLevelService.checkStatus(psychologicalLevelCheckStatusVO,reviewer));
    }

    /**
     * 辅导员对定级表的更新
     * @param psychologicalLevelUpdateVO
     */
    @PreAuthorize("hasAuthority('student_update')")
    @PutMapping("/updatePsyLevel")
    public R updatePsyLevelById(@RequestBody @Valid PsychologicalLevelUpdateVO psychologicalLevelUpdateVO){
        /**
         * 进行审核状态的判断，如果是审核通过则不可以在更新
         * 还要进行recorder 和 登录人 id的判断  一样才可以更新这里的问题
         */
        if (psychologicalLevelUpdateVO.getId() == null){
            return R.failure(Code.PARAM_NOT_COMPLETE);
        }
        PsychologicalLevel psychologicalLevel = psychologicalLevelService.getById(psychologicalLevelUpdateVO.getId());
        if (psychologicalLevel == null) {
            return R.failure("定级记录不存在");
        }
        if (psychologicalLevel.getCheckStatus() == 1){
            return R.failure("当前记录已审核通过，不可以再更新");
        }
        if (!psychologicalLevel.getRecorder().equals(RequestUtil.getId())){
            return R.failure(Code.PERMISSION_NO_ACCESS);
        }
        psychologicalLevelUpdateVO.setRecordedTime(LocalDateTime.now());
        return R.success(psychologicalLevelService.updatePsyLevelById(psychologicalLevelUpdateVO));
    }

}
