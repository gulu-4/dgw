package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.Leave;
import com.chards.committee.domain.LeaveBack;
import com.chards.committee.dto.BackSchoolAdminGetAndUpdateDTO;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.LeaveBackAdminGetAndUpdateDTO;
import com.chards.committee.dto.LeaveBackDateAreaDTO;
import com.chards.committee.dto.LeaveDateAreaDTO;
import com.chards.committee.vo.BackSchoolGetAllVO;
import com.chards.committee.vo.LeaveBackGetAllVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (LeaveBack)表数据库访问层
 *
 * @author chards
 * @since 2020-09-25 20:52:35
 */
public interface LeaveBackMapper extends BaseMapper<LeaveBack> {
	//获取管理员管理的学生的返校审核
	Page<LeaveBackGetAllVO> getAdminManagementStudentBackSchool(@Param("page") Page<LeaveBackGetAllVO> page, @Param("param") LeaveBackAdminGetAndUpdateDTO leaveBackAdminGetAndUpdateDTO);

	boolean updateAdminManagementStudentBackSchoolPass(@Param("param") LeaveBackAdminGetAndUpdateDTO leaveBackAdminGetAndUpdateDTO);

	/**
	 * 获取管理员管理的学生的返校审核 在某一段时间柔
	 *
	 * @param leaveBackDateAreaDTO
	 * @return
	 */
	List<LeaveBackGetAllVO> getAdminManagementStudentBackSchoolByDateArea(@Param("param") LeaveBackDateAreaDTO leaveBackDateAreaDTO);
}