package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:BackSchoolGetAllVO1
 * @date: 2020-08-25 18:33
 */

@Data
public class BackSchoolGetAllVO1 {

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
	private String emergencyContact;

	@ExcelProperty("紧急联系电话")
	private String phone1;

	//请描述必要的返校原因
	@TableField("reason")
	@ExcelProperty("入校原因")
	private String reason;


	//到校日期
	@TableField("date")
	@ExcelProperty("到校时间")
	private String date;


	//个人行程
	@TableField("back_school.note")
	@ExcelProperty("个人行程")
	private String note;

	//由（）站
	@TableField("departure")
	@ExcelProperty("由（）站")
	private String departure;
	//到（）站
	@TableField("destination")
	@ExcelProperty("到（）站")
	private String destination;


	//居住地
	@TableField("loc")
	@ExcelProperty("出发地")
	private String loc;

	//抵徐地点
	@TableField("pickup_loc")
	@ExcelProperty("抵徐地点")
	private String pickupLoc;

	//抵徐时间
	@TableField("hour")
	@ExcelProperty("抵徐时间")
	private Integer hour;


	//交通工具
	@TableField("transport")
	@ExcelProperty("交通工具")
	private String transport;

	//车牌号/车次
	@TableField("car")
	@ExcelProperty("车牌号/车次")
	private String car;


	//座位号
	@TableField("seat")
	@ExcelProperty("座位号")
	private String seat;
	//陪同人员
	@TableField("company")
	@ExcelProperty("陪同人员")
	private String company;


	//家长监护人姓名
	@TableField("guardian")
	@ExcelProperty("家长监护人姓名")
	private String guardian;

	//呼吸道感染
	@TableField("q1")
	@ExcelProperty("呼吸道感染")
	private String q1;
	//境外旅居史
	@TableField("q2")
	@ExcelProperty("境外旅居史")
	private String q2;
	//接触境外归国人员
	@TableField("q3")
	@ExcelProperty("接触境外归国人员")
	private String q3;
	//发热、干咳、乏力等症状
	@TableField("q4")
	@ExcelProperty("发热、干咳、乏力等症状")
	private String q4;
	//完成新冠疫苗接种
	@TableField("q5")
	@ExcelProperty("完成新冠疫苗接种")
	private String q5;



	//是否批准  （0就是没批 1批准 2拒绝）
	@TableField("pass")
	@ExcelProperty("是否批准")
	private String pass;


	//审核人
	@TableField("reviewed_by")
	@ExcelProperty("审核人")
	private String reviewedBy;


	//审核人
	@TableField("reviewed_by")
	@ExcelProperty("是否报道")
	private String reports;

}
