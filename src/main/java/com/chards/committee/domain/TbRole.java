package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 父角色
	 */
	private Long parentId;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色英文名称
	 */
	private String enname;

	/**
	 * 备注
	 */
	private String description;

	private LocalDateTime created;

	private LocalDateTime updated;


}