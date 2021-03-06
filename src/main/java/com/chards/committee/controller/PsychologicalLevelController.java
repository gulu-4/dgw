package com.chards.committee.controller;

import com.chards.committee.constant.Constant;
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
import java.util.Date;
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
        if (stuInfoService.isWithinDataScope(psychologicalLevelInsertVO.getStuNum())) {
            PsychologicalLevel psychologicalLevel = new PsychologicalLevel();
            BeanUtils.copyProperties(psychologicalLevelInsertVO,psychologicalLevel);
            // 填写者和填写时间都是后台获取
            psychologicalLevel.setRecordedTime(new Date());
            psychologicalLevel.setRecorder(RequestUtil.getLoginUser().getId());
            return R.success(psychologicalLevelService.save(psychologicalLevel));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 辅导员及学工处获取定级记录（即操作记录，包含所有提交过的信息）
     * 修改一下：辅导员能获取到该年级的所有同学的定级记录，而不是只能获取到其自己提交的定级记录——21.1.1，poplar
     * v2 这里需要返回一个字段判断该某个学生是不是第一次添加关爱情况
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getPsychologicalLevelPage")
    public R getPsychologicalLevelTest(@RequestParam(value = "checkStatus",defaultValue = "") Integer checkStatus,
                                       @RequestParam(value = "stuNum", defaultValue = "") String stuNum,
                                       Page<PsychologicalLevelCheckSelectVO> page){
        if (!stuNum.equals("")) {
            if (!stuInfoService.isWithinDataScope(stuNum)){
                return R.failure(Code.PERMISSION_NO_ACCESS);
            }
        }
//        // 获取登录账号的id
//        String recorder = RequestUtil.getLoginUser().getId();
//        // 首先判断是否是学工处账号
//        List<String> roles = RequestUtil.getRoles();
//        for (String role : roles) {
//            if (role.equals("XUEGONG")) {
//                recorder = "";
//                break;
//            }
//        }
//        return R.success(psychologicalLevelService.getPsychologicalLevelPage(page, checkStatus, recorder, stuNum));
          return R.success(psychologicalLevelService.getPsychologicalLevelPage(page, checkStatus, stuNum));
    }

    /**
     * 通过id获取定级记录
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getPsyLevelById/{id}")
    public R getPsyLevelById(@PathVariable Serializable id) {
        PsychologicalLevel psychologicalLevel = psychologicalLevelService.getById(id);
        Assert.notNull(psychologicalLevel, Code.RESULT_DATA_NONE);
        StuInfo stuInfo = stuInfoService.getById(psychologicalLevel.getStuNum());
        return stuInfo == null || stuInfoService.isWithinDataScope(stuInfo) ? R.success(psychologicalLevel) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 通过id删除定级记录
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('XUEGONG')")
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Serializable id) {
        PsychologicalLevel psychologicalLevel = psychologicalLevelService.getById(id);
        Assert.notNull(psychologicalLevel, Code.RESULT_DATA_NONE);
        return R.success(psychologicalLevelService.removeById(id));
    }

    /**
     * 通过学生学号获取定级记录
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getPsychologicalLevelGetByStuNumVO")
    public R getPsyLevelById(@RequestParam String stuNum) {
        if (stuInfoService.isWithinDataScope(stuNum)) {
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
//        非学工处、以及副书记，在记录审核通过后，就不能再改了
        if (((!RequestUtil.getRoles().contains(Constant.XUEGONG)) || (!RequestUtil.getRoles().contains(Constant.SHUJI))) && (psychologicalLevel.getCheckStatus() == 1)){
            return R.failure("当前记录已审核通过，不可以再更新");
        }

//        if (!psychologicalLevel.getRecorder().equals(RequestUtil.getId())){
//            return R.failure(Code.PERMISSION_NO_ACCESS);
//        }
        psychologicalLevelUpdateVO.setRecordedTime(LocalDateTime.now());
        return R.success(psychologicalLevelService.updatePsyLevelById(psychologicalLevelUpdateVO));
    }

    /**
     * 通过筛选条件获取心理定级记录
     * a. 筛选标准
     *  ⅰ. 学院
     *  ⅱ. 年级
     *  ⅲ. 类别（设计一个type数组，可以存储多个问题类别）与线索（六个类别里只要有一个有就返回）
     *  ⅳ. 级别
     * 在查询界面，每个学生只显示一条等级信息（后台直接取最新的一条即可）
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("getPsychologicalLevelByParams")
    public R getPsychologicalLevelByParams(PsychologicalLevelQueryNewParamVO psychologicalLQNPVO,
                                           Page<PsychologicalLevelGetByStuNumVO> page) {
        return R.success(psychologicalLevelService.getPsychologicalLevelByParams(page,psychologicalLQNPVO));
    }
}