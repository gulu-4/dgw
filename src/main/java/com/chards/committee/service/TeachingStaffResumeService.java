package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.TeachingStaffResume;
import com.chards.committee.mapper.TeachingStaffResumeMapper;
import com.chards.committee.vo.PsychologicalCounselingCaseDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiuSu
 * @create 2021/2/21 0:42
 */
@Service("teachingStaffResumeService")
public class TeachingStaffResumeService extends ServiceImpl<TeachingStaffResumeMapper, TeachingStaffResume> {

    @Autowired
    UserService userService;

    public TeachingStaffResume getByStaffId(String staffId){
        return baseMapper.getByStaffId(staffId);
    }

    public Page<TeachingStaffResume> getPage(@Param("page") Page<TeachingStaffResume> page, Integer checkStatus){

        Page<TeachingStaffResume> teachingStaffResumePage = baseMapper.getPage(page, checkStatus);
        List<TeachingStaffResume> teachingStaffResumeList = teachingStaffResumePage.getRecords();
        for (TeachingStaffResume teachingStaffResume: teachingStaffResumeList){
            teachingStaffResume.setName(userService.getUserById(teachingStaffResume.getStaffId()).getName());
        }
        teachingStaffResumePage.setRecords(teachingStaffResumeList);
        return teachingStaffResumePage;
    }
}
