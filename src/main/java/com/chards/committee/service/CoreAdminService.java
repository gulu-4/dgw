package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.config.BusinessException;
import com.chards.committee.constant.Constant;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.LoginIp;
import com.chards.committee.dto.AdminPermissionDTO;
import com.chards.committee.dto.AdminRoleDTO;
import com.chards.committee.dto.CoreAdminDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.dto.UserTokenDTO;
import com.chards.committee.mapper.CoreAdminMapper;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.DepartmentMajorsAdminVO;
import com.chards.committee.vo.UserLoginRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
@Service
@Slf4j
public class CoreAdminService extends ServiceImpl<CoreAdminMapper, CoreAdmin> {

    @Autowired
    LoginIpService loginIpService;
    @Autowired
    CoreAdminMapper coreAdminMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TbRoleService tbRoleService;
    @Autowired
    StuInfoService stuInfoService;

    public UserLoginRespVO getAdminToken(String username, String password, String ip) {
        CoreAdmin coreAdmin = getById(username);
        if (coreAdmin == null || !bCryptPasswordEncoder.matches(password, coreAdmin.getPassword())) {
            BusinessException.error(Code.USER_LOGIN_ERROR);
        }
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(coreAdmin, userInfo);
        userTokenDTO.setUserInfo(userInfo);
        List<AdminRoleDTO> adminRole = tbRoleService.getAdminRole(username);
        List<String> roles = adminRole.stream().map(AdminRoleDTO::getEnname).collect(Collectors.toList());
        if (roles.contains(Constant.XUEGONG)) {
            userInfo.setDepartment("学工处");
        }
        userTokenDTO.setRoles(roles);
        List<String> permisssions = tbRoleService.getAdminPermission(
                adminRole.stream().map(adminRoleDTO -> Long.valueOf(adminRoleDTO.getId())).collect(Collectors.toList())
        ).stream().map(AdminPermissionDTO::getPermission).collect(Collectors.toList());
        userTokenDTO.setPermissionsList(permisssions);
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, userTokenDTO, 24, TimeUnit.HOURS);
        UserLoginRespVO resp = new UserLoginRespVO();
        resp.setToken(token);
        resp.setName(coreAdmin.getName());
        LoginIp byLastIp = loginIpService.getByUserId(username);
        resp.setOldIp(byLastIp != null ? byLastIp.getIp() : "空");
        if (loginIpService.addLoginIp(username, ip)) {
            resp.setNewIp(ip);
        }
        return resp;
    }

    public boolean isGtAdmin(String adminId) {
        if (RequestUtil.isRoot()) return true;
        List<AdminRoleDTO> loginRole = tbRoleService.getAdminRole(RequestUtil.getId());
        List<AdminRoleDTO> adminRole = tbRoleService.getAdminRole(adminId);
        return loginRole.size() > adminRole.size() && loginRole.containsAll(adminRole);
    }

    public boolean isContainsReturnIsGtAdmin(String adminId) {
        CoreAdmin coreAdmin = getById(adminId);
        Assert.notNull(coreAdmin, "该管理员不存在");
        return isGtAdmin(adminId);
    }


    public List<DepartmentMajorsAdminVO> getAdminByDepartment(String dep) {
        QueryWrapper<CoreAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department", dep);
        List<CoreAdmin> coreAdmins = baseMapper.selectList(queryWrapper);
        return coreAdmins.stream().map(coreAdmin -> {
            DepartmentMajorsAdminVO departmentMajorsAdminVO = new DepartmentMajorsAdminVO();
            departmentMajorsAdminVO.setAdminId(coreAdmin.getId());
            departmentMajorsAdminVO.setName(coreAdmin.getName());
            return departmentMajorsAdminVO;
        }).collect(Collectors.toList());
    }


    public List<CoreAdmin> getAll() {
        QueryWrapper<CoreAdmin> queryWrapper = new QueryWrapper<>();
        return coreAdminMapper.selectList(queryWrapper);
    }


    public Page<CoreAdminDTO> getLike(Page<CoreAdminDTO> page, String param) {
        param = "%" + param + "%";
        return baseMapper.getLike(page, param);
    }

    public List<CoreAdminDTO> getLikeList(String param) {
        param = "%" + param + "%";
        return baseMapper.getLikeList(param);
    }

    public List<CoreAdminDTO> getDepList(String param) {

        return baseMapper.getDepList(param);
    }

    public boolean isRoot(Serializable id) {
        return isRoot(getById(id));
    }

    public boolean isRoot(CoreAdmin coreAdmin) {
        if (coreAdmin == null || coreAdmin.getId() == null) return false;
        List<String> roles = tbRoleService.getAdminRole(coreAdmin.getId()).stream().map(AdminRoleDTO::getEnname).collect(Collectors.toList());
        return roles.contains(Constant.ADMIN);

    }





}
