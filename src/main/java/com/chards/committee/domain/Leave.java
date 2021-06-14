package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Leave)表实体类
 *
 * @author chards
 * @since 2020-08-26 14:51:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_leave")
public class Leave extends Model<Leave> {
	private static final long serialVersionUID = -45443249898790332L;
	//唯一
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	//学号
	@TableField("stu_num")
	private String stuNum;
	//请假日期
	@TableField("start_date")
	private LocalDateTime startDate;
	//结束日期
	@TableField("end_date")
	private LocalDateTime endDate;
	//请假原因
	@TableField("reason")
	private String reason;
	//状态判断 未审核：0 |同意：1 |拒绝：2 |销假：3
	@TableField("status")
	private Integer status;
	//手机号
	@TableField("tel")
	private String tel;
	//紧急联系人
	@TableField("emergency_caller")
	private String emergencyCaller;
	//紧急联系人电话
	@TableField("emergency_tel")
	private String emergencyTel;
	//去向
	@TableField("movement")
	private String movement;
	//创建时间
	@TableField("create_time")
	private LocalDateTime createTime;
	//审核人Id
	@TableField("reviewerId")
	private String reviewerId;

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