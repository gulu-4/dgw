package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.Leave;
import com.chards.committee.dto.LeaveByIdOrNameDTO;
import com.chards.committee.dto.LeaveDateAreaDTO;
import com.chards.committee.dto.LeaveInfoDTO;
import com.chards.committee.mapper.LeaveMapper;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.LeaveInfoVO;
import com.chards.committee.vo.LeaveSchoolGetAllVO;
import com.chards.committee.vo.LeaveSchoolQueryParamVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (Leave)表服务类
 *
 * @author chards
 * @since 2020-08-26 14:51:51
 */
@Slf4j
@Service("leaveService")
public class LeaveService extends ServiceImpl<LeaveMapper, Leave> {

	public List<LeaveSchoolGetAllVO> getAdminManagementStudentLeaveSchoolByDateArea(LeaveDateAreaDTO leaveDateAreaDTO) {
		return baseMapper.getAdminManagementStudentLeaveSchoolByDateArea(leaveDateAreaDTO);
	}


	public Page<LeaveInfoVO> adminSelectLeave(Page<LeaveInfoVO> page, LeaveSchoolQueryParamVO leaveSchoolQueryParamVO) {
		return baseMapper.adminSelectLeave(page, leaveSchoolQueryParamVO);
	}


	public int stuGetByDateArea(LocalDateTime startDate, LocalDateTime endDate) {
		return stuGetByDateArea(startDate.toString(), endDate.toString());
	}

	/**
	 * 登录学生查找一段时间内的请假记录
	 * 0 未审核 | 1 批准 | 2 拒绝
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int stuGetByDateArea(String startDate, String endDate) {
		QueryWrapper<Leave> leaveQueryWrapper = new QueryWrapper<>();
		leaveQueryWrapper
				.eq("stu_num", RequestUtil.getId())
				.ne("status", 2)
			  .ge("start_date", startDate).ge("end_date", endDate).le("start_date",endDate) // 中间时间
				.or(i->i.le("end_date", endDate).le("start_date", startDate).ge("end_date",startDate)	.ne("status", 2).eq("stu_num", RequestUtil.getId())) //包含
				.or(i->i.le("start_date", startDate).ge("end_date", endDate).ne("status", 2 ).eq("stu_num", RequestUtil.getId()))//左包含
				.or(i->i.le("end_date", endDate).ge("start_date", startDate).ne("status", 2).eq("stu_num", RequestUtil.getId()));//右包含
		return count(leaveQueryWrapper);
	}

	public  List<LeaveInfoVO> getByIdOrName(String param){
		LeaveByIdOrNameDTO leaveByIdOrNameDTO=new LeaveByIdOrNameDTO();
		leaveByIdOrNameDTO.setParam(param);
//		leaveByIdOrNameDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
		return baseMapper.getByIdOrName(leaveByIdOrNameDTO);
	}
}