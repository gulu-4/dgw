package com.chards.committee.controller;

import com.chards.committee.service.LoginIpService;
import com.chards.committee.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 远 chards_
 * @FileName:LoginIpController
 * @date: 2020-08-28 13:33
 */
@RestController
@RequestMapping("/loginsIp")
public class LoginIpController {


	@Autowired
	private LoginIpService loginIpService;


	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping
	public R getLoginIPCount(){
		return R.success(loginIpService.getLoginsCount());
	}

	@PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
	@GetMapping("/four")
	public R getFourDaysCount(){
		return R.success(loginIpService.getCountAbooutFiveDay());
	}

}
