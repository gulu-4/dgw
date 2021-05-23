package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 *
 * @author LiuSu
 * @since 2021-05-23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("news")
public class News extends Model<News> {
	private static final long serialVersionUID = -22641639860282886L;

	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@ApiModelProperty("title")
	private String title;

	@ApiModelProperty("content")
	private String content;

	@ApiModelProperty("author")
	private String author;

	@ApiModelProperty("showState")
	private Boolean showState;

	@ApiModelProperty("createTime")
	private LocalDateTime createTime;

	@ApiModelProperty("updateTime")
	private LocalDateTime updateTime;

	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}