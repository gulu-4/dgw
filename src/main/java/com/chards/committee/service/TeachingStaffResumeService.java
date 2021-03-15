package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.TeachingStaffResume;
import com.chards.committee.mapper.TeachingStaffResumeMapper;
import com.chards.committee.vo.PsychologicalCounselingCaseDetailVO;
import com.chards.committee.vo.TeachingStaffResumeVO;
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

    public Page<TeachingStaffResumeVO> getPage(@Param("page") Page<TeachingStaffResumeVO> page, Integer checkStatus){

        Page<TeachingStaffResumeVO> teachingStaffResumeVOPage = baseMapper.getPage(page, checkStatus);
        List<TeachingStaffResumeVO> teachingStaffResumeVOList = teachingStaffResumeVOPage.getRecords();
        for (TeachingStaffResumeVO teachingStaffResumeVO: teachingStaffResumeVOList){
            teachingStaffResumeVO.setName(userService.getUserById(teachingStaffResumeVO.getStaffId()).getName());
        }
        teachingStaffResumeVOPage.setRecords(teachingStaffResumeVOList);
        return teachingStaffResumeVOPage;
    }
}
