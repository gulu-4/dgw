package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.JobObtain;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.mapper.JobObtainMapper;
import com.chards.committee.vo.JobObtainGetInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author LiuSu
 * @create 2021/1/26 12:39
 */
@Service("jobObtainService")
public class JobObtainService extends ServiceImpl<JobObtainMapper, JobObtain> {
    @Autowired
    private CoreAdminService coreAdminService;

    @Autowired
    private UserService userService;

    public Page<JobObtainGetInfoVO> getAdminManagementStudentJobObtain(Page<JobObtainGetInfoVO> page) {
        return baseMapper.getAdminManagementStudentJobObtain(page);
    }

    public JobObtainGetInfoVO getInfoByStuNum(String stuNum){
        JobObtainGetInfoVO jobObtainGetInfoVO = baseMapper.getInfoByStuNum(stuNum);
        if (jobObtainGetInfoVO != null){
            jobObtainGetInfoVO.setRecorder(getCoreAdminName(jobObtainGetInfoVO.getRecorder()));
        }
        return jobObtainGetInfoVO;
    }

    /**
     * 根据admin工号返回姓名
     */
    private String getCoreAdminName(String id){
//        CoreAdmin coreAdmin = coreAdminService.getById(id);
        UserInfo userInfo = userService.getUserById(id);
        return userInfo.getName();
    }
}
