package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.BackSchool;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.BackSchoolAdminGetAndUpdateDTO;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.BackSchoolGetAllVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (BackSchool)表数据库访问层
 *
 * @author chards
 * @since 2020-08-17 13:46:29
 */
public interface BackSchoolMapper extends BaseMapper<BackSchool> {
	//获取管理员管理的学生的返校审核
	@DataScope()
	Page<BackSchoolGetAllVO> getAdminManagementStudentBackSchool(@Param("page") Page<BackSchoolGetAllVO> page, @Param("param") BackSchoolAdminGetAndUpdateDTO backSchoolAdminGetAndUpdateDTO);

	boolean updateAdminManagementStudentBackSchoolPass(@Param("param") BackSchoolAdminGetAndUpdateDTO backSchoolAdminGetAndUpdateDTO);

	/**
	 * 获取管理员管理的学生的返校审核 在某一段时间柔
	 *
	 * @param backSchoolDateAreaDTO
	 * @return
	 */
	@DataScope()
	List<BackSchoolGetAllVO> getAdminManagementStudentBackSchoolByDateArea(@Param("param") BackSchoolDateAreaDTO backSchoolDateAreaDTO);

	@DataScope()
	List<StuInfo> getDoNotApplyForBackSchoolByParams(@Param("param") BackSchoolDateAreaDTO backSchoolDateAreaDTO);
}