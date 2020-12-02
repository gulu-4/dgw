package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author 远 chards_
 * @FileName:LeaveInsertVO
 * @date: 2020-08-26 14:53
 */
@Data
public class LeaveInsertVO  {
	//请假日期
	@NotNull(message = "请假开始时间不能为空")
	@Future(message = "时间必须是未来的")
	private LocalDateTime startDate;
	//结束日期
	@NotNull(message = "请假结束时间不能为空")
	@Future(message = "时间必须是未来的")
	private LocalDateTime endDate;
	//请假原因
	@NotEmpty(message = "请假原因不能为空")
	private String reason;
	//手机号
	@NotEmpty(message = "手机号码不能为空")
	private String tel;
	//紧急联系人
	@NotEmpty(message = "紧急联系人不能为空")
	private String emergencyCaller;
	//紧急联系人电话
	@NotEmpty(message = "紧急联系人电话不能为空")
	private String emergencyTel;
	//去向
	@NotEmpty(message = "去向不能为空")
	private String movement;

}
