package com.chards.committee.dto;

import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:BackSchoolExeclDTO
 * @date: 2020-08-24 00:23
 */
@Data
public class BackSchoolDateAreaDTO extends BackSchoolAdminGetAndUpdateDTO {
	private String beginDate;
	private String endDate;
	private long start;
	private long end;

//	下边这几个筛选项先加着，避免SQL冲突，也可备用
	private String stuNum; //学号

	private String department;  // 学院

	private String grade; // 年级


}
