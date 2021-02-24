package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.TbAdminRole;
import com.chards.committee.mapper.TbAdminRoleMapper;
import com.chards.committee.vo.PartTimeStaffAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
@Service
public class TbAdminRoleService extends ServiceImpl<TbAdminRoleMapper, TbAdminRole>  {
	@Autowired
	private DataScopeService dataScopeService;

	public TbAdminRole getInfoByAdminId(String adminId){
		QueryWrapper <TbAdminRole> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("admin_id",adminId);
		return baseMapper.selectOne(queryWrapper);
	}

	public int addPartTimeStaff(PartTimeStaffAddVO partTimeStaffAddVO){
		return baseMapper.addPartTimeStaff(partTimeStaffAddVO.getStaffId(),partTimeStaffAddVO.getRoleId());
	}

	@Transactional
	public int deletePartTimeStaff(String staffId) {
		// 首先出tb_admin_role中的数据
		int result1 = baseMapper.deleteByAdminId(staffId);
		// 其次删除user_data_scope中的数据，这里主要确保删除tb_admin_role中的数据
		int result2 = dataScopeService.deleteByUserId(staffId);
		return result1;
	}

}
