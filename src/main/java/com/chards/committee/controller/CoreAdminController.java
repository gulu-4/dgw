package com.chards.committee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.TbAdminRole;
import com.chards.committee.domain.TbRole;
import com.chards.committee.dto.CoreAdminDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.CoreAdminService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.service.TbAdminRoleService;
import com.chards.committee.service.TbRoleService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author chards
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/core-admin")
@Api(tags = "管理员模块")
public class CoreAdminController {

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Resource
	private CoreAdminService coreAdminService;

	@Resource
	private StuInfoService stuInfoService;

	@Autowired
	private TbAdminRoleService tbAdminRoleService;

	@Autowired
	private TbRoleService tbRoleService;

//    /**
//     * 新增需要给管理员添加角色 接口先不写
//     *
//     * @param coreAdmin {@link CoreAdmin}
//     * @return {@link R}
//     */
//    @PreAuthorize("hasAuthority('teacher_insert')")
//    @PostMapping("create")
//    public R create(@Valid @RequestBody CoreAdmin coreAdmin) {
//        List<AdminRoleDTO> adminRole = tbRoleService.getAdminRole(coreAdmin.getId());
//        List<String> loginRoles = RequestUtil.getLoginUserTokenDTO().getRoles();
//        List<String> adminRoles = adminRole.stream().map(AdminRoleDTO::getEnname).collect(Collectors.toList());
//        if (loginRoles.size() <= adminRole.size() || !loginRoles.containsAll(adminRoles))
//            BusinessException.error(Code.PERMISSION_NO_ACCESS);
//        // 业务逻辑
//        boolean created = coreAdminService.save(coreAdmin);
//        if (created) {
//            return R.success("创建成功");
//        }
//        return null;
//    }

	/**
	 * 增加管理员（ROOT）
	 * @param coreAdminAddVO
	 * @return
	 */
	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/add")
	public R addRoleByROOT(@Valid @RequestBody CoreAdminAddVO coreAdminAddVO){
		CoreAdmin coreAdmin=new CoreAdmin();
		BeanUtils.copyProperties(coreAdminAddVO,coreAdmin);
		coreAdmin.setPassword(bCryptPasswordEncoder.encode(coreAdminAddVO.getId()));
    return  R.success(coreAdminService.save(coreAdmin));
	}


	/**
	 * 这里只需要传递一个学号，然后传递一个权限即可，然后在tb_admin_role表中加入一个字段，同时需要设置user_data_scope
	 * @param partTimeStaffAddVO
	 * @return
	 */
	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/addPartTimeStaff")
	public R addPartTimeStaff(@Valid @RequestBody PartTimeStaffAddVO partTimeStaffAddVO){
		if (partTimeStaffAddVO.getRoleId() == null) {
			partTimeStaffAddVO.setRoleId((long) 4);
		}
		return  R.success(tbAdminRoleService.addPartTimeStaff(partTimeStaffAddVO));
	}

	@PreAuthorize("hasRole('ROOT')")
	@DeleteMapping("/removePTStaffById/{staffId}")
	public R deletePartTimeStaff(@PathVariable String staffId) {
		if (staffId == null) {
			return R.failure(Code.PARAM_IS_BLANK);
		}
		return R.success(tbAdminRoleService.deletePartTimeStaff(staffId));
	}



	/**
	 * 删除
	 *
	 * @param coreAdminId {@code Long}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_delete')")
	@PostMapping("remove/{coreAdminId}")
	public R remove(@PathVariable String coreAdminId) {
		if (!coreAdminService.isContainsReturnIsGtAdmin(coreAdminId)) {
			BusinessException.error(Code.PERMISSION_NO_ACCESS);
		}
		return R.success(coreAdminService.removeById(coreAdminId));
	}

	/**
	 * 更新管理员数据
	 *
	 * @param coreAdmin {@link CoreAdmin}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_update')")
	@PostMapping("update")
	public R update(@Valid @RequestBody CoreAdmin coreAdmin) {
		if (!coreAdminService.isContainsReturnIsGtAdmin(coreAdmin.getId())) {
			BusinessException.error(Code.PERMISSION_NO_ACCESS);
		}
		//传入的数据自带加密密码
		return R.success(coreAdminService.updateById(coreAdmin));
	}

	/**
	 * 修改自己的信息(只准许修改座机号)
	 *
	 * @param coreAdmin {@link CoreAdmin}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_own') and (not hasRole('STUDENT')) ")
	@PostMapping("/update/own")
	public R updateOwn(@RequestBody CoreAdmin coreAdmin) {
		CoreAdmin byId = coreAdminService.getById(coreAdmin.getId());
		byId.setTel(coreAdmin.getTel());
		byId.setPhone(coreAdmin.getPhone());
		return R.success(coreAdminService.updateById(byId));
	}


	/**
	 * 根据id获取管理员信息
	 *
	 * @param coreAdminId {@code Long}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_select')")
	@GetMapping("get/{coreAdminId}")
	public R get(@PathVariable String coreAdminId) {
		if (!coreAdminService.isContainsReturnIsGtAdmin(coreAdminId)) {
			BusinessException.error(Code.PERMISSION_NO_ACCESS);
		}
		return R.success(coreAdminService.getById(coreAdminId));
	}

	/**
	 * 模糊查询管理员信息
	 * 姓名 工号 电话 座机
	 *
	 * @param param {@code String}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_select')")
	@GetMapping("get/coreAdmin")
	public R getcoreAdmin(String param, Page<CoreAdminDTO> page) {
		return R.success(coreAdminService.getLike(page, param));
	}


	/**
	 * 根据学院 查询管理员信息 这里是ROOT才拥有的吧
	 *
	 * @param department {@code String}
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_select')")
	@GetMapping("get/department")
	public R getByDepartment(String department, Page<CoreAdmin> page) {
		QueryWrapper<CoreAdmin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("department", department);
		return R.success(coreAdminService.page(page, queryWrapper));
	}

	/**
	 * 根据学院 查询管理员信息 这里包括 这里是ROOT才拥有的吧
	 * @param department
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasAuthority('teacher_select')")
	@GetMapping("/getStaffByDepartment")
	public R getStaffByDepartment(String department, Page<CoreAdminDTO> page) {
		return R.success(coreAdminService.getDepList(department,page));
	}



	/**
	 * 根据学院  查询兼职管理员信息
	 * @param department
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasAuthority('teacher_select')")
	@GetMapping("/getPartTimeStaffByDepartment")
	public R getPartTimeStaffByDepartment(String department, Page<PartTimeStaffVO> page) {
		return R.success(coreAdminService.getPartTimeStaffByDepartment(department,page));
	}


	/**
	 * 获取
	 *
	 * @return {@link R}
	 */
	@PreAuthorize("hasAuthority('teacher_own') and (not hasRole('STUDENT')) ")
	@GetMapping("get/own")
	public R getOwnInfo() {
		UserInfo userInfo = RequestUtil.getLoginUser();
		CoreAdmin coreAdmin = coreAdminService.getById(userInfo.getId());
		return R.success(coreAdmin);
	}


	@PreAuthorize("hasAuthority('teacher_own') and (not hasRole('STUDENT')) ")
	@PostMapping("/updatePwd")
	public R updatePwd(@Valid @RequestBody UserPwdVO pwd, @RequestHeader(value = "Authorization") String token) {
		UserInfo userInfo = RequestUtil.getLoginUser();
		String id = userInfo.getId();
		CoreAdmin coreAdmin = coreAdminService.getById(id);
		if (!bCryptPasswordEncoder.matches(pwd.getOldpwd(), coreAdmin.getPassword())) {
			BusinessException.error("旧密码错误");
		}
		coreAdmin.setPassword(bCryptPasswordEncoder.encode(pwd.getNewpwd()));
		redisTemplate.delete(token);
		return R.success(coreAdminService.updateById(coreAdmin));
	}


	@PreAuthorize("hasAuthority('teacher_own') and (not hasRole('STUDENT')) ")
	@GetMapping("/get")
	@ApiOperation(value = "管理员获取自己的个人信息")
	public R getAdminInfo() {
		String adminId = RequestUtil.getId();
		CoreAdmin coreAdmin = coreAdminService.getById(adminId);
		TbAdminRole tbAdminRole = tbAdminRoleService.getInfoByAdminId(adminId);
		TbRole tbRole = tbRoleService.getById(tbAdminRole.getRoleId());
		if (tbRole.getId() == 1) {
			coreAdmin.setOccupation("学工处");
		} else coreAdmin.setOccupation(tbRole.getName());
		return R.success(coreAdmin);
	}


}
