package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:PortraitVO
 * @date: 2020-10-09 20:22
 */

@Data
public class PortraitVO {

	@TableId(value = "stu_num")
	@ExcelProperty("学号")
	private String stuNum;

	@ExcelProperty("姓名")
	private String name;

	@ExcelProperty("学院")
	private String department;

	@ExcelProperty("年级")
	private String grade;

}
