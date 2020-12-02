package com.chards.committee.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 学生信息模糊查询字段
 * </p>
 *
 * @author 远 chards_
 * @FileName:StuInfoPageVO
 * @date: 2020-07-27 15:37
 */
@Data
public class StuInfoPageDTO implements Serializable {

	/**
	 * 默认查询：学号 姓名 拼音 电话 身份证号
	 */
	private String param;

	/**
	 * 判断增加父母查询选项 1/0 查询 / 忽略
	 */
	private int parentsName;

	/**
	 * 判断增加家庭地址查询选项 1/0 查询 / 忽略
	 */
	private int address;
	/**
	 * 寝室 1/0 查询 / 忽略 默认0
	 */
	private int qinshi;
	/**
	 * 紧急联系人 1/0 查询 / 忽略 默认0
	 */
	private int emergencyContact;

	/**
	 * 紧急联系电话 1/0 查询 / 忽略 默认0
	 */
	private int emergencyPhone;
	/**
	 * 紧急联系电话 1/0 查询 / 忽略 默认0
	 */
	private int parentsPhone;

	private AdminWorkDTO adminWorkDTO;

}
