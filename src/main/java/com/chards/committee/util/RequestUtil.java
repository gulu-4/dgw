package com.chards.committee.util;

import com.chards.committee.constant.Constant;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.dto.UserTokenDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @author 远 chards_
 * @FileName:RequestUtil
 * @date: 2020-07-24 10:40
 */

/**
 * 获取当前登录用户的一些信息
 */
public class RequestUtil {
    public static UserInfo getLoginUser() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserTokenDTO getLoginUserTokenDTO() {
        return (UserTokenDTO) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public static boolean isRoot() {
        return getRoles().contains(Constant.ADMIN);
    }


    public static List<String> getRoles() {
        return getLoginUserTokenDTO().getRoles();
    }


    public static List<String> getWork() {
        return Arrays.asList(getLoginUser().getWork().split(","));
    }

    public static String getDepartment() {
        return getLoginUser().getDepartment();
    }

    public static String getId() {
        return getLoginUser().getId();
    }

    public static void setUserTokenDTO(UserTokenDTO userTokenDTO) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userTokenDTO.getUserInfo(), userTokenDTO, userTokenDTO.getAuthorities()));
    }

    public static AdminWorkDTO getAdminWorkDTO() {
        AdminWorkDTO adminWorkDTO = new AdminWorkDTO();
        adminWorkDTO.setIdentity(isRoot() ? 1 : 0);
        adminWorkDTO.setWorks(getWork());
        adminWorkDTO.setDepartment(getDepartment());
        return adminWorkDTO;
    }

}
