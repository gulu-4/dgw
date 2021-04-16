package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.DepartmentMajor;
import com.chards.committee.domain.LoginIp;
import com.chards.committee.mapper.CoreAdminMapper;
import com.chards.committee.mapper.DepartmentMajorMapper;
import com.chards.committee.vo.CascaderClassesVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public List<CascaderClassesVO> getCascaderClasses() {
		List<DepartmentMajor> departmentMajors = baseMapper.selectList(new QueryWrapper<>());
		List<CascaderClassesVO> cascaderClassesVOS = new ArrayList<>();
		Set<String> departmentMajorset = new HashSet<>();
		departmentMajors.forEach(departmentMajor -> {
			departmentMajorset.add(departmentMajor.getDepartment());
		});
		for (String department : departmentMajorset) {
			CascaderClassesVO cascaderClassesVO = new CascaderClassesVO();
			cascaderClassesVO.setValue(department);
			List<CascaderClassesVO> cascaderClassesVOS1 = new ArrayList<>();
			List<DepartmentMajor> departmentMajors1 = departmentMajors.stream().filter(DepartmentMajor -> DepartmentMajor.getDepartment().equals(department)).collect(Collectors.toList());
			Set<String> departmentMajorset1 = new HashSet<>();
			departmentMajors1.forEach(departmentMajor -> {
				departmentMajorset1.add(departmentMajor.getMajor());
			});
			for (String major : departmentMajorset1){
				CascaderClassesVO cascaderClassesVO1 = new CascaderClassesVO();
				cascaderClassesVO1.setValue(major);
				List<CascaderClassesVO> cascaderClassesVOS2 = new ArrayList<>();
				List<DepartmentMajor> departmentMajors2 = departmentMajors.stream().filter(DepartmentMajor -> DepartmentMajor.getMajor().equals(major)).collect(Collectors.toList());
				Set<String> departmentMajorset2 = new HashSet<>();
				departmentMajors2.forEach(departmentMajor -> {
					departmentMajorset2.add(departmentMajor.getClasses());
				});
				for (String classes : departmentMajorset2) {
					CascaderClassesVO cascaderClassesVO2 = new CascaderClassesVO();
					cascaderClassesVO2.setValue(classes);
					cascaderClassesVOS2.add(cascaderClassesVO2);
				}
				cascaderClassesVO1.setChildren(cascaderClassesVOS2);
				cascaderClassesVOS1.add(cascaderClassesVO1);
			}
			cascaderClassesVO.setChildren(cascaderClassesVOS1);
			cascaderClassesVOS.add(cascaderClassesVO);
		}
		return cascaderClassesVOS;
	}


}