package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增管理员
 *
 * @author 远 chards_
 * @FileName:CoreAdminAddVO
 * @date: 2020-08-24 21:48
 */

@Data
public class CoreAdminAddVO implements Serializable {

	@TableId(value = "id")
	@NotBlank(message = "工号不能为空")
	private String id;

	@TableField("name")
	@NotBlank(message = "姓名不能为空")
	private String name;

	@TableField("password")
	private String password;

	@TableField("gender")
	@NotBlank(message = "性别不能为空")
	private String gender;

	@TableField("department")
	@NotBlank(message = "学院/部门不能为空")
	private String department;

	@TableField("occupation")
	@NotBlank(message = "职位不能为空")
	private String occupation;

	@TableField("work")
//	@NotBlank(message = "工作年级不能为空")
	private String work;

	@TableField("tel")
	private String tel;

	@TableField("phone")
	private String phone;



}
