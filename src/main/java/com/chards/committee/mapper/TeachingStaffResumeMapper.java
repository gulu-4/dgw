package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.TeachingStaffResume;
import com.chards.committee.vo.TeachingStaffResumeVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author LiuSu
 * @create 2021/2/21 0:41
 */
public interface TeachingStaffResumeMapper extends BaseMapper<TeachingStaffResume> {
    TeachingStaffResume getByStaffId(@Param(value = "staffId") String staffId);

    Page<TeachingStaffResumeVO> getPage(@Param("page") Page<TeachingStaffResumeVO> page, @Param("checkStatus")  Integer checkStatus);
}
