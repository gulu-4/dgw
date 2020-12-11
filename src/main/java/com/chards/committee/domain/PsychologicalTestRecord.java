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
 * (PsychologicalTestRecord)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("psychological_test_record")
public class PsychologicalTestRecord extends Model<PsychologicalTestRecord> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("stu_num")
    private String stuNum;

    @TableField("school_year")
    private String schoolYear;

    @TableField("result")
    private String result;

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
