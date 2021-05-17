package com.chards.committee.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CoreAdmin)表实体类
 *
 * @author chards
 * @since 2020-08-22 23:15:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("core_admin")
public class CoreAdmin extends Model<CoreAdmin> {
	private static final long serialVersionUID = -22641649860282886L;

	@TableId(value = "id")
	@ExcelProperty("工号")
	private String id;

	@TableField("name")
	@ExcelProperty("姓名")
	private String name;

	@TableField("password")
	@ExcelIgnore
	private String password;

	@TableField("gender")
	@ExcelProperty("性别")
	private String gender;

	@TableField("department")
	@ExcelProperty("学院")
	private String department;

	@TableField("occupation")
	@ExcelProperty("工位")
	private String occupation;

	@TableField("work")
	@ExcelProperty("工作年级")
	private String work;

	@TableField("tel")
	@ExcelProperty("座机号码")
	private String tel;

	@TableField("phone")
	@ExcelProperty("手机号码")
	private String phone;

	@TableField("is_active")
	@ExcelProperty("是否激活")
	private Integer isActive;

	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}