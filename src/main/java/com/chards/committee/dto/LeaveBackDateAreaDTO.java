package com.chards.committee.dto;

import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:LeaveBackDateAreaDTO
 * @date: 2020-09-25 21:14
 */
@Data
public class LeaveBackDateAreaDTO extends LeaveBackAdminGetAndUpdateDTO {

	private String beginDate;
	private String endDate;
	private long start;
	private long end;
}
