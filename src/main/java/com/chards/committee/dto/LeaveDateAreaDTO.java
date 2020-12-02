package com.chards.committee.dto;

import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:LeaveDateAreaDTO
 * @date: 2020-08-27 14:27
 */

@Data
public class LeaveDateAreaDTO extends LeaveInfoDTO {

	private String beginDate;
	private String endDate;
	private long start;
	private long end;

}
