package com.chards.committee.controller;


import com.chards.committee.dto.AdminRoleDTO;
import com.chards.committee.dto.UserDataScope;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.DataScopeService;
import com.chards.committee.service.TbRoleService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.R;
import com.chards.committee.vo.UserPermissionInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author poplar
 * @create 2021/2/22 18:05
 */

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理模块", value = "统一的用户管理模块")
public class UserController {

    @Autowired
    DataScopeService dataScopeService;
    @Autowired
    TbRoleService tbRoleService;

    @GetMapping("/permissionInfo")
    @PreAuthorize("hasAuthority('teacher_own')")
    @ApiOperation(value = "获取个人信息、角色和管辖权限")
    public R getUserPermissionInfo(){
        UserInfo userInfo = RequestUtil.getLoginUser();
        List<UserDataScope> userDataScopeList = dataScopeService.getUserDataScope(userInfo.getId());
        List<AdminRoleDTO> adminRole = tbRoleService.getAdminRole(userInfo.getId());
        List<String> roles = adminRole.stream().map(AdminRoleDTO::getEnname).collect(Collectors.toList());
        UserPermissionInfoVO userPermissionInfoVO = new UserPermissionInfoVO();
        BeanUtils.copyProperties(userInfo, userPermissionInfoVO);
        userPermissionInfoVO.setUserDataScopeList(userDataScopeList);
        userPermissionInfoVO.setRoles(roles);
        return R.success(userPermissionInfoVO);
    }
}
