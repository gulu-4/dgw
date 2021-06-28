package com.chards.committee.vo.teachStaffExport;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


/**
 * @author LiuSu
 * @create 2021/6/3 16:31
 */
@HeadRowHeight(35)
@Data
public class TeachStaffBasicExportVO {
    // 基本信息
    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","序号"})
    private Integer index;  //序号

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","学院"})
    private String department;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","姓名"})
    private String name;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","工号"})
    private String staffId;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","性别"})
    private String gender;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","民族"})
    private String national;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","出生年月"})
    private String birthday;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","政治面貌"})
    private String politicCountenance;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","入党时间"})
    private String partyJoinTime;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","籍贯"})
    private String nativePlace;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","工作时间"})
    private String workTime;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","工作单位"})
    private String workDepartment;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","财务编号"})
    private String financialNumber;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","职级"})
    private String position;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","职级首聘时间"})
    private String positionTime;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","职称"})
    private String academicTitle;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","职称首聘时间"})
    private String academicTitleTime;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","编制情况"})
    private String establishment;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","办公电话"})
    private String tel;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","办公地点"})
    private String officeLocation;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","手机"})
    private String phone;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","QQ"})
    private String qq;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","微信"})
    private String wechat;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","邮箱"})
    private String email;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","学历/学位"})
    private String educationalBackground;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","现任职务"})
    @TableField("occupation")
    private String occupation;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","婚姻状况"})
    private String weddingStatus;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","宿舍"})
    private String dormitory;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","家庭住址"})
    private String address;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","特长"})
    private String hobbys;

    @ExcelProperty({"辅导员信息统计汇总表（基本信息）","担任社会职务"})
    private String socialsDutuies;

    @ExcelIgnore
    private String awards;   //奖惩情况

    @ExcelIgnore
    private String qualificationCertificate; //

    @ExcelIgnore
    private String researches;  // 科研情况 这里需要分成三个
}
