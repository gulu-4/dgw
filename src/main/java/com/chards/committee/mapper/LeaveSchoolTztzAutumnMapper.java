package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.LeaveSchoolTztzAutumn;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.LeaveSchoolTztzAutumnAdminGetAndUpdateDTO;
import com.chards.committee.vo.LeaveSchoolTztzAutumnGetALLVO;
import com.chards.committee.vo.LeaveSchoolTztzQueryParamVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiuSu
 * @create 2021/1/4 17:19
 */
public interface LeaveSchoolTztzAutumnMapper extends BaseMapper<LeaveSchoolTztzAutumn> {
    //获取管理员管理的学生的离校审核
    Page<LeaveSchoolTztzAutumnGetALLVO> getAdminManagementStudentLeaveSchoolTztzAutumn(@Param("page") Page<LeaveSchoolTztzAutumnGetALLVO> page, @Param("param") LeaveSchoolTztzQueryParamVO leaveSchoolTztzQueryParamVO);

    boolean updateAdminManagementStudentLeaveSchoolPass(@Param("param") LeaveSchoolTztzAutumnAdminGetAndUpdateDTO leaveSchoolTztzAutumnAdminGetAndUpdateDTO);

    /**
     * 获取管理员管理的学生的返校审核 在某一段时间内
     *
     * @param backSchoolDateAreaDTO
     * @return
     */
    List<LeaveSchoolTztzAutumnGetALLVO> getAdminManagementStudentLeaveSchoolByDateArea(@Param("param") BackSchoolDateAreaDTO backSchoolDateAreaDTO);
}
