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
 * (PsychologicalInventionMapper)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("psychological_invention")
public class PsychologicalInvention extends Model<PsychologicalInvention> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("stu_num")
    private String stuNum;

    @TableField("invention")
    private String invention;

    @TableField("invention_time")
    private Date inventionTime;

    @TableField("recorder")
    private String recorder;

    @TableField("recorded_time")
    private LocalDateTime recordedTime;

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
