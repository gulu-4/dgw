package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (DepartmentMajor)表实体类
 *
 * @author chards
 * @since 2020-08-16 16:22:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("department_major")
public class DepartmentMajor extends Model<DepartmentMajor> {
	private static final long serialVersionUID = 240038474224866828L;

	@TableField("department")
	private String department;

	@TableField("major")
	private String major;

	@TableField("classes")
	private String classes;
}