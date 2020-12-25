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
 * (PsychologicalLevelMapper)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("psychological_level")
public class PsychologicalLevel extends Model<PsychologicalLevel> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("stu_num")
    private String stuNum;

    @TableField("learning_problem")
    private String learningProblem;

    @TableField("economic_problem")
    private String economicProblem;

    @TableField("abnormal_behavior_problem")
    private String abnormalBehaviorProblem;

    @TableField("life_event_problem")
    private String lifeEventProblem;

    @TableField("personality_problem")
    private String personalityProblem;

    @TableField("epidemic_psychological_problem")
    private String epidemicPsychologicalProblem;

    @TableField("level")
    private String level;

    @TableField("initLevel")
    private String initLevel;

    @TableField("initTime")
    private Date initTime;

    @TableField("basis")
    private String basis;

    @TableField("remark")
    private String remark;

    @TableField("recorder")
    private String recorder;

    @TableField("recorded_time")
    private Date recordedTime;

    @TableField("check_status")
    private Integer checkStatus;  // 0 -1 1

    @TableField("reviewer")
    private String reviewer;

    @TableField("instruction")
    private String instruction;

    @TableField("check_time")
    private LocalDateTime checkTime;
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
