package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.TbAdminRole;
import com.chards.committee.mapper.TbAdminRoleMapper;
import org.springframework.stereotype.Service;

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

	public TbAdminRole getInfoByAdminId(String adminId){
		QueryWrapper <TbAdminRole> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("admin_id",adminId);
		return baseMapper.selectOne(queryWrapper);
	}

}
