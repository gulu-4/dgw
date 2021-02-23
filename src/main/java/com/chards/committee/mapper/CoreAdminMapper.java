package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.dto.CoreAdminDTO;

import com.chards.committee.vo.PartTimeStaffVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
public interface CoreAdminMapper extends BaseMapper<CoreAdmin> {


	Page<CoreAdminDTO> getLike(@Param("page") Page<CoreAdminDTO> page, @Param("param") String param);


	List<CoreAdminDTO> getLikeList(@Param("param") String param);


	List<CoreAdminDTO> getDepList(@Param("department") String department);

	Page<CoreAdminDTO> getDepList(@Param("department") String department,@Param("page") Page<CoreAdminDTO> page);

	Page<PartTimeStaffVO> getPartTimeStaffByDepartment(@Param("department") String department,
											   @Param("page") Page<PartTimeStaffVO> page);





}

