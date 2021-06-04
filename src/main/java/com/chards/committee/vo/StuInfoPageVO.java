package com.chards.committee.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 远 chards_
 * @FileName:StuInfoPageDTO
 * @date: 2020-07-27 17:04
 */

@Data
public class StuInfoPageVO implements Serializable {

	private String id;

	private String name;

	private String department;

	private String grade;

	private String classes;

	private String dormitory;


}
