package com.chards.committee.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
public class UserTokenDTO implements Serializable {
	private UserInfo userInfo;
	private List<String> roles = new ArrayList<>();
	private List<String> permissionsList = new ArrayList<>();
	// 这里权限 注解发挥作用的地方
	public Collection<? extends GrantedAuthority> getAuthorities() {


		//authorities里面就是存所有权限的  权限 （但是你如果注解hasRole(就会加上前缀ROLE_ 到authorities去找的)）
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role -> {

			list.add(new SimpleGrantedAuthority("ROLE_" + role));
		});
		permissionsList.forEach(permissions -> {
			list.add(new SimpleGrantedAuthority(permissions));
		});
		return list;
	}
}
