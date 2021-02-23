package com.chards.committee.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * @author LiuSu
 * @create 2021/2/21 0:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teaching_staff_resume")
public class TeachingStaffResume extends Model<TeachingStaffResume> {
    private static final long serialVersionUID = 319803036181383175L;

    @ExcelIgnore
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ExcelProperty("工号")
    @TableField("staff_id")
    private String staffId;

    @ExcelIgnore
    @TableField("avatar")
    private String avatar;

    @ExcelProperty("民族")
    @TableField("national")
    private String national;

    @ExcelProperty("籍贯")
    @TableField("native_place")
    private String nativePlace;

    @ExcelProperty("出生年月")
    @TableField("birthday")
    private String birthday;

    @ExcelProperty("政治面貌")
    @TableField("politic_countenance")
    private String politicCountenance;

    @ExcelProperty("学历/学位")
    @TableField("educational_background")
    private String educationalBackground;

    @ExcelProperty("入党时间")
    @TableField("party_join_time")
    private String partyJoinTime;

    @ExcelProperty("工作时间")
    @TableField("work_time")
    private String workTime;

    @ExcelProperty("工作单位")
    @TableField("work_department")
    private String workDepartment;

    @ExcelProperty("现任职务")
    @TableField("occupation")
    private String occupation;

    @ExcelProperty("职级")
    @TableField("position")
    private String position;

    @ExcelProperty("职级首聘时间")
    @TableField("position_time")
    private String positionTime;

    @ExcelProperty("职称")
    @TableField("academic_title")
    private String academicTitle;

    @ExcelProperty("职称首聘时间")
    @TableField("academic_title_time")
    private String academicTitleTime;

    @ExcelProperty("办公电话")
    @TableField("tel")
    private String tel;

    @ExcelProperty("手机")
    @TableField("phone")
    private String phone;

    @ExcelProperty("QQ")
    @TableField("qq")
    private String qq;

    @ExcelProperty("微信")
    @TableField("wechat")
    private String wechat;

    @ExcelProperty("邮箱")
    @TableField("email")
    private String email;

    @ExcelProperty("婚姻状况")
    @TableField("wedding_status")
    private String weddingStatus;

    @ExcelProperty("宿舍")
    @TableField("dormitory")
    private String dormitory;

    @ExcelProperty("家庭住址")
    @TableField("address")
    private String address;

    @ExcelProperty("学习和工作简历")
    @TableField("study_and_work")
    private String studyAndWork;

    @ExcelProperty("学历学位演变信息")
    @TableField("education_progress")
    private String educationProgress;

    @ExcelProperty("奖惩情况")
    @TableField("awards")
    private String awards;

    @ExcelProperty("职业资格证书")
    @TableField("qualification_certificate")
    private String qualificationCertificate;

    @ExcelProperty("工作理念")
    @TableField("working_philosophy")
    private String workingPhilosophy;

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