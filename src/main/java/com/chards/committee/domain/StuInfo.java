package com.chards.committee.domain;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

/**
 * <p>
 *
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StuInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	@ExcelProperty("学号")
	private String id;
	@ExcelProperty("姓名")
	private String name;
	@ExcelProperty("性别")
	private String gender;
	@ExcelProperty("拼音")
	private String pinyin;
	@ExcelProperty("身份证号")
	private String idCard;
	@ExcelProperty("年级")
	private String grade;
	@ExcelProperty("学院")
	private String department;
	@ExcelProperty("专业")
	private String major;
	@ExcelProperty("班级")
	private String classes;
	@ExcelProperty("民族")
	private String national;
	@ExcelProperty("学历")
	private String educationBackground;
	@ExcelProperty("毕业学院")
	private String graduatedSchool;
	@ExcelProperty("家庭住址")
	private String address;
	@ExcelProperty("宿舍")
	private String dormitory;
	@ExcelProperty("邮箱")
	private String email;
	@ExcelProperty("手机号")
	private String phone;
	@ExcelProperty("奖项")
	private String awards;
	@ExcelProperty("紧急联系人")
	private String emergencyContact;
	@ExcelProperty("紧急联系电话")
	private String emergencyPhone;

	@ExcelProperty("经济资助")
	private String aid;
	@ExcelProperty("心理等级")
	private String psychologicalLevel;
	@ExcelProperty("辅导员姓名")
	private String counsellorName;
	@ExcelProperty("辅导员工号")
	private String counsellorNum;
	@ExcelIgnore
	private String counsellorPhone;
	@ExcelIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String priority;
	@ExcelProperty("政治面貌")
	private String politicalStatus;
	@ExcelProperty("生源地")
	private String originLocation;
	@ExcelProperty("状态")
	private String state;
	@ExcelProperty("备注")
	private String note;

	@ExcelIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;


}