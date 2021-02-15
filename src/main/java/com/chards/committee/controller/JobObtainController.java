package com.chards.committee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.JobObtain;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.JobObtainService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.JobObtainGetInfoVO;
import com.chards.committee.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/1/26 12:41
 */
@RestController
@RequestMapping("/jobObtain")
public class JobObtainController {
    @Autowired
    private JobObtainService jobObtainService;

    @Autowired
    private StuInfoService stuInfoService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/selectAll")
    public R selectAll(Page<JobObtainGetInfoVO> page) {
//        AdminWorkDTO adminWorkDTO = RequestUtil.getAdminWorkDTO();
        return R.success(jobObtainService.getAdminManagementStudentJobObtain(page));
    }

    /**
     * 通过学生学号查询单条数据
     *
     * @return 单条数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/getInfoByStuNum")
    public R getInfoByStuNum(@RequestParam String stuNum) {
        if (stuInfoService.isWithinDataScope(stuNum)) {
            return R.success(jobObtainService.getInfoByStuNum(stuNum));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 新增数据
     *
     * @param jobObtain 实体对象
     * @return 新增结果
     */
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping
    public R insert(@RequestBody JobObtain jobObtain) {
        if (stuInfoService.isWithinDataScope(jobObtain.getStuNum())) {
            // 判断是否已经有添加信息
            JobObtainGetInfoVO jobObtainGetInfoVO = jobObtainService.getInfoByStuNum(jobObtain.getStuNum());
            if (jobObtainGetInfoVO != null){
                return R.failure(Code.DATA_ALREADY_EXISTED);
            }
            jobObtain.setRecorder(RequestUtil.getLoginUser().getId());
            jobObtain.setRecordedTime(LocalDateTime.now());
            return R.success(jobObtainService.save(jobObtain));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    @PreAuthorize("hasAuthority('student_insert')")
    @PutMapping
    public R update(@RequestBody JobObtain jobObtain) {
        if (stuInfoService.isWithinDataScope(jobObtain.getStuNum())) {
            jobObtain.setRecordedTime(LocalDateTime.now());
            return R.success(jobObtainService.updateById(jobObtain));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }
}
