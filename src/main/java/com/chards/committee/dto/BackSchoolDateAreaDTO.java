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
}
