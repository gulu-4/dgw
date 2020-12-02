package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.TbRole;
import com.chards.committee.dto.AdminPermissionDTO;
import com.chards.committee.dto.AdminRoleDTO;
import com.chards.committee.mapper.TbRoleMapper;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
@Service
public class TbRoleService extends ServiceImpl<TbRoleMapper, TbRole> {


    public List<AdminRoleDTO> getAdminRole(String adminId) {
        return baseMapper.getRoleByUserId(adminId);
    }


    public List<AdminPermissionDTO> getAdminPermission(List<Long> roles) {
        return baseMapper.getPermissionByUserId(roles);
    }

    /**
     * 登录的用户的角色是不是大于tbRole
     * @param tbRole
     * @return
     */
    public boolean isGtRole(TbRole tbRole) {
        List<String> roles = RequestUtil.getRoles();
        String tbRoleEnname = tbRole.getEnname();
        return RequestUtil.isRoot() || (roles.contains(tbRoleEnname) && !roles.get(0).equals(tbRole.getEnname()));
    }

    public boolean isContainsReturnIsGtRole(Long roleId) {
        TbRole tbRole = getById(roleId);
        Assert.notNull(tbRole, "没有找到该角色");
        return isGtRole(tbRole);
    }
}
