package com.chards.committee.domain;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@TableName("difficulties_student_apply")
public class DifficultiesStudentApply extends Model<DifficultiesStudentApply> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "期数，年份")
    private Integer stage;

    @ApiModelProperty(value = "学生学号")
    private String stuNum;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "学院")
    private String department;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "班级")
    private String classes;

    @ApiModelProperty(value = "姓名")
    private String stuName;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "民族")
    private String national;

    @ApiModelProperty(value = "政治面貌")
    private String politicalStatus;

    @ApiModelProperty(value = "出生年月")
    private String birthday;

    @ApiModelProperty(value = "籍贯")
    private String originLocation;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "家庭人口")
    private Integer familyNum;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "详细通讯地址")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

    @ApiModelProperty(value = "家长手机号码")
    private String parentPhone;

    @ApiModelProperty(value = "家庭成员情况，逗号分隔")
    private String familyMember;

    @ApiModelProperty(value = "是否建档立卡贫困家庭学生")
    private Boolean fileAndRegister;

    @ApiModelProperty(value = "是否最低生活保障家庭学生")
    private Boolean minimumLivingSecurity;

    @ApiModelProperty(value = "是否特困供养学生")
    private Boolean extremelyPoor;

    @ApiModelProperty(value = "是否孤残学生")
    private Boolean orphanAndDisabled;

    @ApiModelProperty(value = "是否烈士子女")
    private Boolean martyr;

    @ApiModelProperty(value = "是否家庭经济困难残疾学生及残疾人子女")
    private Boolean other;

    @ApiModelProperty(value = "影响家庭经济状况有关信息，将表中内容作为整个字符串传到后端")
    private String influenceInfo;

    @ApiModelProperty(value = "个人承诺内容")
    private String personalContent;

    @ApiModelProperty(value = "申请理由，150-255字符")
    private String applyReason;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "国家扶贫库导入信息")
    private String fromImport;

    @ApiModelProperty(value = "辅导员审核时间")
    private LocalDateTime firstReviewTime;

    @ApiModelProperty(value = "辅导员审核工号+“，”+姓名")
    private String firstReviewer;

    @ApiModelProperty(value = "0待审核，1，上报给学工处，-1未通过")
    private Integer firstCheck;

    @ApiModelProperty(value = "辅导员审核时间")
    private LocalDateTime secondReviewTime;

    @ApiModelProperty(value = "A档，B档，C档，无（默认为无）")
    private String level;

    @ApiModelProperty(value = "建档金额")
    private String money;

    @ApiModelProperty(value = "学工处审核工号+“，”+姓名")
    private String secondReviewer;

    @ApiModelProperty(value = "0待审核，1，通过，-1拒绝")
    private Integer secondCheck;

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
