package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiuSu
 * @since 2021-07-24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("provide_money")
@ApiModel(value="ProvideMoney对象", description="")
public class ProvideMoney implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "学生学号")
    private String stuNum;

    @ApiModelProperty(value = "申请表类型")
    @NotBlank(message = "申请表类型不能为空")
    private String type;

    @ApiModelProperty(value = "期数（2020-2021）")
    @NotBlank(message = "期数不能为空")
    private String stage;

    @ApiModelProperty(value = "出生年月（2021年7月）")
    private String birthMonth;

    @ApiModelProperty(value = "入学时间（2020年9月）")
    private String enterSchoolTime;

    @ApiModelProperty(value = "学制（三年、四年...）")
    private String schoolSystem;

    @ApiModelProperty(value = "学习情况")
    private String learningSituation;

    @ApiModelProperty(value = "获奖情况")
    private String awards;

    @ApiModelProperty(value = "推荐理由")
    private String recommendReason;

    @ApiModelProperty(value = "家庭经济情况")
    private String familyEconomics;

    @ApiModelProperty(value = "家庭成员情况")
    private String familyMember;

    @ApiModelProperty(value = "申请理由")
    private String applyReason;

    @ApiModelProperty(value = "院系意见")
    private String departmentOpinion;

    @ApiModelProperty(value = "学校意见")
    private String schoolOpinion;

    @ApiModelProperty(value = "0待审核，1，上报给学工处，-1未通过")
    private int firstCheck;

    @ApiModelProperty(value = "审核人（工号+姓名）")
    private String firstReviewer;

    @ApiModelProperty(value = "辅导员审核时间")
    private LocalDateTime firstCheckTime;

    @ApiModelProperty(value = "0待审核，1通过，-1拒绝")
    private int secondCheck;

    @ApiModelProperty(value = "学工审核人（工号+姓名）")
    private String secondReviewer;

    @ApiModelProperty(value = "学工审核时间")
    private LocalDateTime secondReviewTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
