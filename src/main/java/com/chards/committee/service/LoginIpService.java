package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.LoginIp;
import com.chards.committee.mapper.LoginIpMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GY
 * @since 2020-07-25
 */
@Service
public class LoginIpService extends ServiceImpl<LoginIpMapper, LoginIp> {

	public LoginIp getByUserId(String userid) {
		QueryWrapper<LoginIp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userid", userid);
		queryWrapper.orderByDesc("create_time");
		queryWrapper.last("limit 1");
		return getOne(queryWrapper);
	}


	public List<LoginIp> getByUserIdLimitTen(String userid) {
		QueryWrapper<LoginIp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userid", userid);
		queryWrapper.orderByDesc("create_time");
		queryWrapper.last("limit 10");
		return baseMapper.selectList(queryWrapper);
	}


	public boolean addLoginIp(String userid, String ip) {
		LoginIp loginIp = new LoginIp();
		loginIp.setUserid(userid).setIp(ip).setCreateTime(LocalDateTime.now());
		return save(loginIp);
	}


	public int getLoginsCount() {
		QueryWrapper<LoginIp> loginIpQueryWrapper=new QueryWrapper<>();
		return baseMapper.selectCount(loginIpQueryWrapper);
	}


	public List<Integer> getCountAbooutFiveDay() {
		List<Integer> list = new ArrayList<>();
		list.add(baseMapper.getCountAboutoday());
		list.add(baseMapper.getCountAboutoday1());
		list.add(baseMapper.getCountAboutoday2());
		list.add(baseMapper.getCountAboutoday3());
		list.add(baseMapper.getCountAboutoday4());
		return list;
	}


}
