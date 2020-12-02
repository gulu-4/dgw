package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.LeaveBack;
import com.chards.committee.dto.BackSchoolAdminGetAndUpdateDTO;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.LeaveBackAdminGetAndUpdateDTO;
import com.chards.committee.dto.LeaveBackDateAreaDTO;
import com.chards.committee.mapper.LeaveBackMapper;
import com.chards.committee.vo.BackSchoolGetAllVO;
import com.chards.committee.vo.LeaveBackGetAllVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (LeaveBack)表服务类
 *
 * @author chards
 * @since 2020-09-25 20:52:36
 */
@Service("leaveBackService")
public class LeaveBackService extends ServiceImpl<LeaveBackMapper, LeaveBack> {

	public Page<LeaveBackGetAllVO> getAdminManagementStudentBackSchool(Page<LeaveBackGetAllVO> page, LeaveBackAdminGetAndUpdateDTO leaveBackAdminGetAndUpdateDTO) {
		return baseMapper.getAdminManagementStudentBackSchool(page,leaveBackAdminGetAndUpdateDTO);
	}

	public boolean updateAdminManagementStudentBackSchoolPass(LeaveBackAdminGetAndUpdateDTO leaveBackAdminGetAndUpdateDTO) {
		return baseMapper.updateAdminManagementStudentBackSchoolPass(leaveBackAdminGetAndUpdateDTO);
	}

	public List<LeaveBackGetAllVO> getAdminManagementStudentBackSchoolByDateArea(LeaveBackDateAreaDTO leaveBackDateAreaDTO) {
		return baseMapper.getAdminManagementStudentBackSchoolByDateArea(leaveBackDateAreaDTO);
	}


}