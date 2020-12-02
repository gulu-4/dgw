package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (Conversation)表实体类
 *
 * @author chards
 * @since 2020-08-01 12:13:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("conversation")
public class Conversation extends Model<Conversation> {
    private static final long serialVersionUID = 319803036181383075L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("stu_num")
    private String stuNum;

    @TableField("content")
    private String content;

    @TableField("datetime")
    private LocalDateTime datetime;

    @TableField("number")
    private String number;

    @TableField("traits")
    private String traits;

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