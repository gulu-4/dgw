package com.chards.committee;

import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.TbAdminRole;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.TbAdminRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 远 chards_
 * @FileName:CommitteeApplicationTests
 * @date: 2020-07-22 17:42
 */

@SpringBootTest
public class CommitteeApplicationTests {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CoreAdminService coreAdminService;

	@Autowired
	private TbAdminRoleService tbAdminRoleService;

	@Test
	void test() {
		System.out.println((bCryptPasswordEncoder.encode("57041")));
	}


	@Test
	void addAdminPwd() {
		List<CoreAdmin> coreAdminList = coreAdminService.getAll();
		coreAdminList.forEach(coreAdmin -> {
			coreAdmin.setPassword(bCryptPasswordEncoder.encode(coreAdmin.getId()));
			coreAdminService.updateById(coreAdmin);
		});
		System.out.println("ok!");
	}

	/**
	 * 这里知道转  js不知道转?????????????? ??????????绕口ling???
	 *
	 * zhihu
	 */

	@Test
	void addAdmin() {
		List<CoreAdmin> coreAdminList = coreAdminService.getAll();
		coreAdminList.forEach(s -> {
			TbAdminRole tbAdminRole = new TbAdminRole();
			if (s.getOccupation().equals("专职辅导员") || s.getOccupation().equals("其它") || s.getOccupation().equals("优才专项辅导员")) {
				tbAdminRole.setAdminId(s.getId());
				tbAdminRole.setRoleId(4L);
			} else if ( s.getOccupation().equals("副书记")) {
				tbAdminRole.setAdminId(s.getId());
				tbAdminRole.setRoleId(3L);
			} else {
				tbAdminRole.setAdminId(s.getId());
				tbAdminRole.setRoleId(2L);
			}
			tbAdminRoleService.save(tbAdminRole);
		});

		System.out.println("ok!");
	}



	@Test
	void test22(){
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime dateTime= LocalDateTime.parse("2020-09-24T12:12:12");
		System.out.println(startDate.isBefore(dateTime));
	}
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		System.out.println(startDate.format(dtf));	}

}
