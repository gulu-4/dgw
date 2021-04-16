package com.chards.committee.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.DepartmentMajor;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.DepartmentMajorService;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 学院专业
 *
 * @author chards
 * @since 2020-08-16 16:24:00
 */
@RestController
@RequestMapping("/departmentMajors")
@Api(tags = "学院专业管理")
public class DepartmentMajorController {
	/**
	 * 服务对象
	 */
	@Autowired
	private DepartmentMajorService departmentMajorService;

	@Autowired
	private CoreAdminService coreAdminService;

	/**
	 * 分页查询所有数据
	 *
	 * @param departmentMajor 查询实体
	 * @return 所有数据
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('teacher_own')")
	public R selectAll( DepartmentMajor departmentMajor) {
		return R.success(departmentMajorService.list(new QueryWrapper<>(departmentMajor)));
	}
	/**
	 * 查询学院，专业，班级列表
	 * @return 所有数据
	 */
	@GetMapping("/getCascaderClasses")
	@PreAuthorize("hasAuthority('teacher_own')")
	public R getCascaderClasses() {
		return R.success(departmentMajorService.getCascaderClasses());
	}
	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@GetMapping("{id}")
	public R selectOne(@PathVariable Serializable id) {
		return R.success(departmentMajorService.getById(id));
	}

	/**
	 * 新增数据
	 *
	 * @param departmentMajor 实体对象
	 * @return 新增结果
	 */
	@PostMapping
	public R insert(@RequestBody DepartmentMajor departmentMajor) {
		return R.success(departmentMajorService.save(departmentMajor));
	}

	/**
	 * 修改数据
	 *
	 * @param departmentMajor 实体对象
	 * @return 修改结果
	 */
	@PostMapping("/update")
	public R update(@RequestBody DepartmentMajor departmentMajor) {
		return R.success(departmentMajorService.updateById(departmentMajor));
	}

	/**
	 * 删除数据
	 *
	 * @param idList 主键结合
	 * @return 删除结果
	 */
	@PostMapping("/delete")
	public R delete(@RequestParam("idList") List<Long> idList) {
		return R.success(departmentMajorService.removeByIds(idList));
	}

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('teacher_own')")
  public R getAdminByDepartment(String department){
		return R.success(coreAdminService.getAdminByDepartment(department));
	}

	@GetMapping("/dep")
	@PreAuthorize("hasAuthority('teacher_own')")
	public R getSetDep(){
		return R.success(departmentMajorService.getDepAll());
	}



}