package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.TeachingStaffResume;
import com.chards.committee.mapper.TeachingStaffResumeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author LiuSu
 * @create 2021/2/21 0:42
 */
@Service("teachingStaffResumeService")
public class TeachingStaffResumeService extends ServiceImpl<TeachingStaffResumeMapper, TeachingStaffResume> {

    public TeachingStaffResume getByStaffId(String staffId){
        return baseMapper.getByStaffId(staffId);
    }

    public Page<TeachingStaffResume> getPage(@Param("page") Page<TeachingStaffResume> page){
        return baseMapper.getPage(page);
    }
}
