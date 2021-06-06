package com.chards.committee.domain;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiuSu
 * @since 2021-05-19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("difficulties_student_assessment")
public class DifficultiesStudentAssessment extends Model<DifficultiesStudentAssessment> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "申请表关联id")
    @NotNull(message = "申请表关联信息不能为空")
    private Long applyId;

    @ApiModelProperty(value = "其他相关描述")
    private String ps;

    @ApiModelProperty(value = "是否申请建档（0否，1是）")
    private Integer apply;

    @ApiModelProperty(value = "总分")
    private String score;

    @ApiModelProperty(value = "测谎分")
    private String fakeScore;

    private String field01;

    private String field02;

    private String field03;

    private String[] field04;

    private String field05;

    private String field06;

    private String field07;

    private String field08;

    private String field09;

    private String field10;

    private String field11;

    private String field12;

    private String field13;

    private String field14;

    private String field15;

    private String[] field16;

    private String field17;

    private String field18;

    private String field19;

    private String[] field20;

    private String field21;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
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
