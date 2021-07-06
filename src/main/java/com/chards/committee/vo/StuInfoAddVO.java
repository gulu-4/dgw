package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 远 chards_
 * @FileName:StuInfoAddVO
 * @date: 2020-08-24 23:28
 */
@Data
public class StuInfoAddVO implements Serializable {

	@TableId(value = "id")
	@NotBlank(message = "学号不能为空")
	private String id;
	@NotBlank(message = "姓名不能为空")
	private String name;
	@NotBlank(message = "性别不能为空")
	private String gender;
	@NotBlank(message = "拼音不能为空")
	private String pinyin;
	@NotBlank(message = "身份证号不能为空")
	private String idCard;
	@NotBlank(message = "年级不能为空")
	private String grade;
	@NotBlank(message = "学院不能为空")
	private String department;
	@NotBlank(message = "专业不能为空")
	private String major;
	@NotBlank(message = "专业班级不能为空")
	private String classes;
	@NotBlank(message = "民族不能为空")
	private String national;
	@NotBlank(message = "教育背景不能为空")
	private String educationBackground;
	private String graduatedSchool;
	private String address;
	@NotBlank(message = "宿舍不能为空")
	private String dormitory;
	private String email;
	private String phone;
	private String awards;
	private String emergencyContact;
	private String aid;
	@NotBlank(message = "心理等级不能为空")
	private String psychologicalLevel;
	private String counsellorName;
	@NotBlank(message = "辅导员公号不能为空")
	private String counsellorNum;
	private String counsellorPhone;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String priority;
	@NotBlank(message = "政治背景不能为空")
	private String politicalStatus;
	private String originLocation;
	private String state;
	private String note;

//	@NotBlank(message = "学生就读状态不能为空")
	private String statusOfCurrentStudents;

}
