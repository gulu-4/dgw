package com.chards.committee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chards.committee.domain.TbAdminRole;
import com.chards.committee.dto.AdminRoleDTO;
import com.chards.committee.service.TbAdminRoleService;
import com.chards.committee.service.TbRoleService;
import com.chards.committee.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 远 chards_
 * @FileName:RoleController
 * @date: 2020-08-23 02:44
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	TbRoleService roleService;

	@Autowired
	private TbAdminRoleService tbAdminRoleService;

	@PreAuthorize("hasAuthority('teacher_select')")
	@GetMapping
	public R getAll() {
		return R.success(roleService.list());
	}


	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/updateAdminRole")
	public R setAdminRole(
			@RequestBody  AdminRoleDTO adminRoleDTO) {
		QueryWrapper<TbAdminRole> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("admin_id",adminRoleDTO.getName());
		TbAdminRole tbAdminRole = tbAdminRoleService.getOne(queryWrapper);
		tbAdminRole.setRoleId(Long.valueOf(adminRoleDTO.getEnname()));
		return R.success(tbAdminRoleService.updateById(tbAdminRole));
	}

	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/addAdminRole")
	public R addAdminRole(
			@RequestBody  AdminRoleDTO adminRoleDTO) {
		TbAdminRole tbAdminRole =new TbAdminRole();
		tbAdminRole.setAdminId(adminRoleDTO.getName());
		tbAdminRole.setRoleId(Long.valueOf(adminRoleDTO.getEnname()));
		return R.success(tbAdminRoleService.save(tbAdminRole));
	}


}
