package com.chards.committee.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 远 chards_
 * @FileName:PassInfoDTO
 * @date: 2020-08-19 21:33
 */

@Data
public class PassInfoDTO implements Serializable {

	//学号
	private  String stuId;

	//学生姓名
	private  String name;

	//返校日期
	private  String date;

	//抵徐地点
	private String loc;

	//审核人
	private String reviewedBy;

//	审核时间
	private LocalDateTime reviewedTime;

	//家长
	private String emergencyCallee;

	//学院信息
	private String department;
//	是否全程留校
	private boolean stayInSchool;
//	全程留校理由
	private String stayInSchoolReason;

}
