package com.chards.committee.config;

import com.alibaba.fastjson.JSON;
import com.chards.committee.dto.UserTokenDTO;
import com.chards.committee.service.RedisService;
import com.chards.committee.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤器
@Component
@Slf4j
public class HttpSecurityFilter extends OncePerRequestFilter {
	@Autowired
	RedisService redisService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		// 在这里获取token的用户和权限
		String authenticated = httpServletRequest.getHeader("Authorization");
		// 查找token对应的信息
		UserTokenDTO userTokenDTO = redisService.getStringValue(authenticated, UserTokenDTO.class);
		if (userTokenDTO != null) {
			log.info(
					"user id : {} , uri : {} , method : {} , params : {}",
					userTokenDTO.getUserInfo().getId(),
					httpServletRequest.getRequestURI(),
					httpServletRequest.getMethod(),
					JSON.toJSONString(httpServletRequest.getParameterMap())
			);
//            // 组装authentication对象，构造参数是Principal Credentials 与 Authorities
//            // 后面的拦截器里面会用到 grantedAuthorities 方法
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userTokenDTO.getUserInfo(), userTokenDTO, userTokenDTO.getAuthorities());
//            // 将authentication信息放入到上下文对象中
//            // 那个注解前会判断这个authentication的权限
//            SecurityContextHolder.getContext().setAuthentication(authentication);

			RequestUtil.setUserTokenDTO(userTokenDTO);
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
