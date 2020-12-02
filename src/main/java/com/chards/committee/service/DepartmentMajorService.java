package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.DepartmentMajor;
import com.chards.committee.domain.LoginIp;
import com.chards.committee.mapper.CoreAdminMapper;
import com.chards.committee.mapper.DepartmentMajorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * (DepartmentMajor)表服务类
 *
 * @author chards
 * @since 2020-08-16 16:22:23
 */
@Service("departmentMajorService")
public class DepartmentMajorService extends ServiceImpl<DepartmentMajorMapper, DepartmentMajor> {

public Set<String> getDepAll(){

	List<DepartmentMajor> departmentMajors = baseMapper.selectList(new QueryWrapper<>());
	Set<String> departmentMajorset =new HashSet<>();
	departmentMajors.forEach(departmentMajor -> {
		departmentMajorset.add(departmentMajor.getDepartment());
	});
	return departmentMajorset;
}


}