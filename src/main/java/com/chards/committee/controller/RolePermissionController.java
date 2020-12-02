package com.chards.committee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chards.committee.domain.TbRolePermission;
import com.chards.committee.service.TbRolePermissionService;
import com.chards.committee.service.TbRoleService;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author 远 chards_
 * @FileName:RolePermissionController
 * @date: 2020-07-24 16:13
 */

@RestController
@RequestMapping("/role-permission")
public class RolePermissionController {
    @Autowired
    private TbRolePermissionService tbRolePermissionService;
    @Autowired
    TbRoleService tbRoleService;

    /**
     * 给角色添加权限
     *
     * @param tbRolePermission
     * @return
     */
    @PreAuthorize("hasRole('ROOT')")
    @PostMapping
    public R addRolePermission(@Valid @RequestBody TbRolePermission tbRolePermission) {
        tbRolePermission.setId(null);
        return tbRoleService.isContainsReturnIsGtRole(tbRolePermission.getRoleId()) ?
                R.success(tbRolePermissionService.save(tbRolePermission)) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 给角色删除权限
     * @param roleId
     * @param permissionId
     * @return
     */
    @PreAuthorize("hasRole('ROOT')")
    @PostMapping("/delete")
    public R addRolePermission(@RequestParam("rolePermissionId") Long roleId, @RequestParam("permissionId") Long permissionId) {
        if (tbRoleService.isContainsReturnIsGtRole(roleId)) {
            TbRolePermission tbRolePermission = new TbRolePermission();
            tbRolePermission.setRoleId(roleId);
            tbRolePermission.setPermissionId(permissionId);
            return R.success(tbRolePermissionService.remove(new QueryWrapper<>(tbRolePermission)));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }
}
