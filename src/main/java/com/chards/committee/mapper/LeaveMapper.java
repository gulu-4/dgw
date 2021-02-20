package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.Leave;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.LeaveByIdOrNameDTO;
import com.chards.committee.dto.LeaveDateAreaDTO;
import com.chards.committee.dto.LeaveInfoDTO;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.BackSchoolGetAllVO;
import com.chards.committee.vo.LeaveInfoVO;
import com.chards.committee.vo.LeaveSchoolGetAllVO;
import com.chards.committee.vo.LeaveSchoolQueryParamVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Leave)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-26 14:51:51
 */
public interface LeaveMapper extends BaseMapper<Leave> {
    @DataScope(studentState = "")
    Page<LeaveInfoVO> adminSelectLeave(@Param("page") Page<LeaveInfoVO> page, @Param("param") LeaveSchoolQueryParamVO leaveSchoolQueryParamVO);


    /**
     * 获取管理员管理的学生的请假审核 在某一段时间柔
     *
     * @param leaveDateAreaDTO
     * @return
     */
    @DataScope(studentState = "")
    List<LeaveSchoolGetAllVO> getAdminManagementStudentLeaveSchoolByDateArea(@Param("param") LeaveDateAreaDTO leaveDateAreaDTO);

    @DataScope(studentState = "")
    List<LeaveInfoVO> getByIdOrName(@Param("param")LeaveByIdOrNameDTO leaveByIdOrNameDTO);


}