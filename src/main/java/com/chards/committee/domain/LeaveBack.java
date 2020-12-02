package com.chards.committee.domain;

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
 * (LeaveBack)表实体类
 *
 * @author chards
 * @since 2020-09-25 20:52:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("leave_back")
public class LeaveBack extends Model<LeaveBack> {
	private static final long serialVersionUID = 374506489809870668L;
	//学号
	@TableId(value = "stu_num")
	@ExcelProperty("学号")
	private String stuNum;
	//请描述必要的返校原因
	@TableField("reason")
	@ExcelProperty("入校原因")
	private String reason;
	//到校日期
	@TableField("date")
	@ExcelProperty("到校时间")
	private String date;
	//个人行程
	@TableField("note")
	@ExcelProperty("个人行程")
	private String note;
	//居住地
	@TableField("loc")
	@ExcelProperty("出发地")
	private String loc;
	//是否批准  （0就是没批 1批准 2拒绝）
	@TableField("pass")
	@ExcelProperty("是否批准")
	private Integer pass;
	//呼吸道感染
	@TableField("q1")
	@ExcelProperty("呼吸道感染")
	private Integer q1;
	//境外旅居史
	@TableField("q2")
	@ExcelProperty("境外旅居史")
	private Integer q2;
	//接触境外归国人员
	@TableField("q3")
	@ExcelProperty("接触境外归国人员")
	private Integer q3;
	//发热、干咳、乏力等症状
	@TableField("q4")
	@ExcelProperty("发热、干咳、乏力等症状")
	private Integer q4;
	//审核人
	@TableField("reviewed_by")
	@ExcelProperty("审核人")
	private String reviewedBy;
	//抵徐地点
	@TableField("pickup_loc")
	@ExcelProperty("抵徐地点")
	private String pickupLoc;
	//家长监护人姓名
	@TableField("guardian")
	@ExcelProperty("家长监护人姓名")
	private String guardian;
	//抵徐时间
	@TableField("hour")
	@ExcelProperty("抵徐时间")
	private Integer hour;
	//紧急联系人
	@TableField("emergency_callee")
	@ExcelProperty("紧急联系人")
	private String emergencyCallee;
	//紧急联系电话
	@TableField("emergency_phone")
	@ExcelProperty("紧急联系电话")
	private String emergencyPhone;
	//居住地
	@TableField("address")
	@ExcelProperty("居住地")
	private String address;
	//由（）站
	@TableField("departure")
	@ExcelProperty("由（）站")
	private String departure;
	//到（）站
	@TableField("destination")
	@ExcelProperty("到（）站")
	private String destination;
	//交通工具
	@TableField("transport")
	@ExcelProperty("交通工具")
	private String transport;
	//座位号
	@TableField("seat")
	@ExcelProperty("座位号")
	private String seat;
	//陪同人员
	@TableField("company")
	@ExcelProperty("陪同人员")
	private String company;

	//车牌号/车次
	@TableField("car")
	@ExcelProperty("车牌号/车次")
	private String car;

	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.stuNum;
	}
}