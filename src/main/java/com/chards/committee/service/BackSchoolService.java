package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.BackSchool;
import com.chards.committee.dto.BackSchoolAdminGetAndUpdateDTO;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.mapper.BackSchoolMapper;
import com.chards.committee.vo.BackSchoolGetAllVO;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (BackSchool)表服务类
 *
 * @author chards
 * @since 2020-08-17 13:46:29
 */
@Service("backSchoolService")
public class BackSchoolService extends ServiceImpl<BackSchoolMapper, BackSchool> {
	public Page<BackSchoolGetAllVO> getAdminManagementStudentBackSchool(Page<BackSchoolGetAllVO> page,BackSchoolAdminGetAndUpdateDTO backSchoolAdminGetAndUpdateDTO) {
		return baseMapper.getAdminManagementStudentBackSchool(page,backSchoolAdminGetAndUpdateDTO);
	}

	public boolean updateAdminManagementStudentBackSchoolPass(BackSchoolAdminGetAndUpdateDTO backSchoolAdminGetAndUpdateDTO) {
		return baseMapper.updateAdminManagementStudentBackSchoolPass(backSchoolAdminGetAndUpdateDTO);
	}

	public List<BackSchoolGetAllVO> getAdminManagementStudentBackSchoolByDateArea(BackSchoolDateAreaDTO backSchoolDateAreaDTO) {
		return baseMapper.getAdminManagementStudentBackSchoolByDateArea(backSchoolDateAreaDTO);
	}
}