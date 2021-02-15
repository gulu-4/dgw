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



/**
 * (DataScope)表实体类
 *
 * @author poplar
 * @since 2021-02-10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_data_scope")
public class DataScope extends Model<DataScope> {
    private static final long serialVersionUID = 1167854448128906677L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("department")
    private String department;

    @TableField("education_background")
    private String educationBackground;

    @TableField("grade")
    private String grade;

    @TableField("major")
    private String major;

    @TableField("classes")
    private String classes;

    @TableField("is_active")
    private String isActive;

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