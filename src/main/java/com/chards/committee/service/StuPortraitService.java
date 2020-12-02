package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.StuPortrait;
import com.chards.committee.mapper.CoreAdminMapper;
import com.chards.committee.mapper.StuPortraitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * (StuPortrait)表服务类
 *
 * @author chards
 * @since 2020-08-01 16:05:10
 */
@Service("stuPortraitService")
public class StuPortraitService extends ServiceImpl<StuPortraitMapper, StuPortrait> {

	@Autowired
	StuPortraitMapper stuPortraitMapper;

	public List<String> getAllstuNum(){
		Set<String> set=new HashSet<>();
		QueryWrapper<StuPortrait> queryWrapper=new QueryWrapper<>();
		List<StuPortrait> stuPortraits = stuPortraitMapper.selectList(queryWrapper);
		stuPortraits.forEach(s->{
			set.add(s.getStuNum());
		});
		List<String> stunums=new ArrayList<>();
		if (set.size()!=0)
			stunums.addAll(set);
		return stunums;
	}


}