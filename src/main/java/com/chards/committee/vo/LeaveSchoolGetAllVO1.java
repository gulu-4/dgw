package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:LeaveSchoolGetAllVO1
 * @date: 2020-08-27 14:49
 */

@Data
public class LeaveSchoolGetAllVO1 {

	@TableId(value = "stu_num")
	@ExcelProperty("学号")
	private String stuNum;

	@ExcelProperty("姓名")
	private String name;

	@ExcelProperty("性别")
	private String gender;

	@ExcelProperty("身份证")
	private String idCard;

	@ExcelProperty("学院")
	private String department;

	@ExcelProperty("年级")
	private String grade;

	@ExcelProperty("专业班级")
	private String classes;

	//居住地
	@ExcelProperty("居住地")
	private String address1;

	@ExcelProperty("宿舍")
	private String dormitory;

	@ExcelProperty("学历")
	private String educationBackground;

	@ExcelProperty("政治面貌")
	private String politicalStatus;

	@ExcelProperty("手机号")
	private String phone;


	@ExcelProperty("紧急联系人")
	private String emergencyCaller;

	@ExcelProperty("请假起始时间")
	private String startDate;

	@ExcelProperty("请假结束时间")
	private String endDate;

	@ExcelProperty("请假去向")
	private String movement;

	@ExcelProperty("请假原因")
	private String reason;


	@ExcelProperty("紧急联系电话")
	private String emergencyTel;


	//是否批准  （0就是没批 1批准 2拒绝 ）
	@TableField("status")
	@ExcelProperty("是否批准")
	private String status;


	//审核人
	@TableField("reviewerId")
	@ExcelProperty("审核人")
	private String reviewerId;

}
