package com.chards.committee.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

/**
 * <p>
 *
 * </p>
 *
 * @author GY
 * @since 2020-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentsInfo implements Serializable {

	private static final long serialVersionUID = 1269248155331246838L;

	@TableId(value = "stu_num")
	private String stuNum;

	private String name1;

	private String relation1;

	private String pid1;

	private String job1;

	private String tel1;

	private String addr1;

	private String name2;

	private String relation2;

	private String pid2;

	private String job2;

	private String tel2;

	private String addr2;


}