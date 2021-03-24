package com.chards.committee.mapper;

import com.chards.committee.domain.TbAdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 用户角色表 Mapper 接口
    * </p>
*
* @author GY
* @since 2020-07-22
*/
public interface TbAdminRoleMapper extends BaseMapper<TbAdminRole> {

    int addPartTimeStaff(@Param("adminId") String adminId,
                         @Param("roleId") Long roleId);

    int deleteByAdminId(@Param("adminId") String adminId,
                        @Param("roleId") String roleId);


    int addAdminRoles(@Param("adminId") String adminId,
                      @Param("list") List<Long> roleIds);

}
