package com.chards.committee.controller;

import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.LoginIpService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.IpGetServiceImpl;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.R;
import com.chards.committee.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 远 chards_
 * @FileName:LoginController
 * @date: 2020-07-22 15:11
 */

@RestController
@RequestMapping("/logins")
public class LoginController {

	@Resource
	private StuInfoService stuInfoService;

	@Resource
	private CoreAdminService coreAdminService;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	private  LoginIpService loginIpService;

	@Autowired
	private IpGetServiceImpl ipGetService;


	/**
	 * 学生登陆
	 */
	@PostMapping("/student")
	public R studentLogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
		return R.success(stuInfoService.getUserToken(userLoginVO.getUsername(), userLoginVO.getPassword(),ipGetService.getIpAddr(request)));
	}
	/**
	 * 学生统一身份认证登录
	 */
	@PostMapping("/ldap/student")
	public R studentLdapLogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
		return R.success(stuInfoService.getUserTokenLdap(userLoginVO.getUsername(), userLoginVO.getPassword(),ipGetService.getIpAddr(request)));
	}

	/**
	 * 管理员登陆
	 */
	@PostMapping("/admin")
	public R adminlogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
		return R.success(coreAdminService.getAdminToken(userLoginVO.getUsername(), userLoginVO.getPassword(),ipGetService.getIpAddr(request)));
	}


	/**
	 * 超级管理员登陆某个用户的号，学生的和老师的都可以，时效为1小时
	 */
	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/root/user")
	public R rootLoginUser(@RequestBody Map params, HttpServletRequest request) {
		return R.success(coreAdminService.getUserTokenForRoot((String) params.get("username"), ipGetService.getIpAddr(request)));
	}


	/**
	 * 管理员统一身份认证登陆
	 */
	@PostMapping("/ldap/admin")
	public R adminLdaplogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
		return R.success(coreAdminService.getAdminTokenLdap(userLoginVO.getUsername(), userLoginVO.getPassword(),ipGetService.getIpAddr(request)));
	}

	/**
	 * 退出登录
	 * @param token
	 * @return
	 */
	@PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
	@PostMapping("/out")
	public R loginOut(@RequestHeader(value = "Authorization") String token) {
		return R.success(redisTemplate.delete(token));
	}


	/**
	 * 查询登录ip记录
	 * @return
	 */
	@PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
	@GetMapping("/ip")
   public R getLoginIps(){
		return R.success(loginIpService.getByUserIdLimitTen(RequestUtil.getId()));
	}


	/**
	 * token时间测试
	 * @return
	 */
	@PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
	@GetMapping("/token")
   public R setToken(@RequestHeader(value = "Authorization") String token,int min,int variate){
		TimeUnit seconds = TimeUnit.SECONDS;
		if (variate==0){
			seconds=	TimeUnit.SECONDS;
		}else if (variate==1){
			seconds=	TimeUnit.MINUTES;
		}else if (variate==2){
			seconds=	TimeUnit.HOURS;
		}
		redisTemplate.expire(token,min,seconds);
		return R.success("成功");
	}






}
