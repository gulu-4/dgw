package com.chards.committee.controller;

import com.chards.committee.domain.PsychologicalInvention;
import com.chards.committee.service.PsychologicalInventionService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.PsychologicalInventionInsertVO;
import com.chards.committee.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/11 17:24
 */
@RestController
@RequestMapping("/psychological_invention")
public class PsychologicalInventionController {
    @Autowired
    private PsychologicalInventionService psychologicalInventionService;

    @Autowired
    private StuInfoService stuInfoService;

    /**
     * 新增
     */
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping("/insert")
    public R insert(@RequestBody @Valid PsychologicalInventionInsertVO psychologicalInventionInsertVO) {
        // 通过这里进行判断的时候会导致研究生的学生没有办法由ROOT之外的权限进行操作。(通过isWithinDataScope就可以啦)
        if (stuInfoService.isWithinDataScope(psychologicalInventionInsertVO.getStuNum())) {
            PsychologicalInvention psychologicalInvention = new PsychologicalInvention();
            BeanUtils.copyProperties(psychologicalInventionInsertVO,psychologicalInvention);
            // 填写者和填写时间都是后台获取
            psychologicalInvention.setRecordedTime(LocalDateTime.now());
            psychologicalInvention.setRecorder(RequestUtil.getLoginUser().getId());
            return R.success(psychologicalInventionService.save(psychologicalInvention));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 通过学生学号查询
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getInventionsByStuNum")
    public R getAll(@RequestParam String stuNum){
        if (stuInfoService.isWithinDataScope(stuNum)){
            return R.success(psychologicalInventionService.getInventionsByStuNum(stuNum));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }
}
