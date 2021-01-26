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

/**
 * @author LiuSu
 * @create 2021/1/26 12:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("job_obtain")
public class JobObtain extends Model<JobObtain> {
    private static final long serialVersionUID = 1L;
    //唯一
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    //学号
    @TableField(value = "stu_num")
    private String stuNum;

    //就业状况
    @TableField(value = "state")
    private String state;

    //就业意向
    @TableField(value = "intention")
    private String intention;

    //就业进度
    @TableField(value = "progress")
    private String progress;

    //就业帮扶
    @TableField(value = "help_type")
    private String helpType;

    //备注
    @TableField(value = "remark")
    private String remark;

    //记录人
    @TableField(value = "recorder")
    private String recorder;

    //记录时间
    @TableField(value = "recorded_time")
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
