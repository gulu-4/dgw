package com.chards.committee.controller;

import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.LoginIpService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.service.UserService;
import com.chards.committee.util.IpGetServiceImpl;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import com.chards.committee.vo.UserLoginVO;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 远 chards_
 * @FileName:LoginController
 * @date: 2020-07-22 15:11
 */

@RestController
@RequestMapping("/logins")
@Api(tags = "登录模块",value = "用户登录相关接口")
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

	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@Autowired
	private UserService userService;


	/**
	 * 学生登陆 10.6.10起关停
	 */
//	@PostMapping("/student")
//	public R studentLogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
//		return R.success(stuInfoService.getUserToken(userLoginVO.getUsername(), userLoginVO.getPassword(),ipGetService.getIpAddr(request)));
//	}
	/**
	 * 学生统一身份认证登录
	 */
	@PostMapping("/ldap/student")
	public R studentLdapLogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
		return R.success(stuInfoService.getUserTokenLdap(userLoginVO.getUsername(), userLoginVO.getPassword(),userLoginVO.getCaptchaId(),userLoginVO.getCaptcha(),ipGetService.getIpAddr(request)));
	}

	/**
	 * 管理员登陆 10.6.10起关停
	 */
//	@PostMapping("/admin")
//	public R adminlogin(@RequestBody @Valid UserLoginVO userLoginVO, HttpServletRequest request) {
//		return R.success(coreAdminService.getAdminToken(userLoginVO.getUsername(), userLoginVO.getPassword(),ipGetService.getIpAddr(request)));
//	}


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
		return R.success(coreAdminService.getAdminTokenLdap(userLoginVO.getUsername(), userLoginVO.getPassword(),userLoginVO.getCaptchaId(),userLoginVO.getCaptcha(),ipGetService.getIpAddr(request)));
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
	 * 小程序端保持用户登录状态用，判断用户登录token是否过期，
	 * @param token
	 * @return
	 */
	@GetMapping("/tokenIsExpired")
	@ApiOperation(value = "判断token是否过期，过期则返回重新登录，未过期则延长过期时间并且返回登录时相同对象")
	public R tokenIsExpired(@RequestHeader(value = "Authorization") String token, HttpServletRequest request){
		return R.success(userService.tokenIsExpired(token,ipGetService.getIpAddr(request)));
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

	@ApiOperation(value = "验证码")
	@GetMapping(value = "/captcha")
	public R getCaptcha(){
		//---------------------------生成验证码----------------------
		//获取验证码文本内容
		String text = defaultKaptcha.createText();
		// 将验证码放到redis中，生成对应的id和失效时间 {"captchaId": "text"} 过期时间五分钟
		String captchaId = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(captchaId, text, 5, TimeUnit.MINUTES);
		//根据文本内容创建图形验证码
		BufferedImage image = defaultKaptcha.createImage(text);
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			//输出流输出图片,格式为jpg
			ImageIO.write(image,"jpeg",outputStream);
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encode(outputStream.toByteArray());
			String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n","");
			Map<String,String> result = new HashMap<String,String>();
			result.put("captchaId",captchaId);
			result.put("captchaBase64",captchaBase64);
			return new R(0,"验证码五分钟内有效",result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return R.failure(Code.SYSTEM_INNER_ERROR);
	}
}
