package com.chards.committee.mapper;

import com.chards.committee.domain.TbRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.dto.AdminPermissionDTO;
import com.chards.committee.dto.AdminRoleDTO;
import com.chards.committee.vo.AdminRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
public interface TbRoleMapper extends BaseMapper<TbRole> {
    //获取管理员的角色
    List<AdminRoleDTO> getRoleByUserId(@Param("id") String adminId);

    //获取管理员所有角色的权限
    List<AdminPermissionDTO> getPermissionByUserId(@Param("ids") List<Long> roles);
}
