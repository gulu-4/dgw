package com.chards.committee.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * @author LiuSu
 * @create 2021/1/4 16:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("leave_school_tztz_autumn")
public class LeaveSchoolTztzAutumn extends Model<LeaveSchoolTztzAutumn> {
    private static final long serialVersionUID = 374506489809870668L;
    //学号
    @TableId(value = "stu_num")
    @ExcelProperty("学号")
    private String stuNum;
    // 申请事项：寒假离校
    @TableField("remark")
    @ExcelProperty("其他说明")
    private String remark;
    //离校时间
    @TableField("date")
    @ExcelProperty("离校时间")
    private String date;
    //个人行程
    @TableField("note")
    @ExcelProperty("个人行程")
    private String note;
    //居住地
    @TableField("loc")
    @ExcelProperty("出发地")
    private String loc;
    //是否批准  （0就是没批 1批准 2拒绝）
    @TableField("pass")
    @ExcelProperty("是否批准")
    private Integer pass;
    //呼吸道感染
    @TableField("q1")
    @ExcelProperty("呼吸道感染")
    private Integer q1;
    //境外旅居史
    @TableField("q2")
    @ExcelProperty("境外旅居史")
    private Integer q2;
    //接触境外归国人员
    @TableField("q3")
    @ExcelProperty("接触境外归国人员")
    private Integer q3;
    //发热、干咳、乏力等症状
    @TableField("q4")
    @ExcelProperty("发热、干咳、乏力等症状")
    private Integer q4;
    //审核人
    @TableField("reviewed_by")
    @ExcelProperty("审核人")
    private String reviewedBy;
    //离徐地点
    @TableField("pickup_loc")
    @ExcelProperty("离徐地点")
    private String pickupLoc;
    //家长监护人姓名
    @TableField("guardian")
    @ExcelProperty("家长监护人姓名")
    private String guardian;
    //离徐时间
    @TableField("hour")
    @ExcelProperty("离徐时间")
    private Integer hour;
    //紧急联系人
    @TableField("emergency_callee")
    @ExcelProperty("紧急联系人")
    private String emergencyCallee;
    //紧急联系电话
    @TableField("emergency_phone")
    @ExcelProperty("紧急联系电话")
    private String emergencyPhone;
    //居住地
    @TableField("address")
    @ExcelProperty("居住地")
    private String address;
    //由（）站
    @TableField("departure")
    @ExcelProperty("由（）站")
    private String departure;
    //到（）站
    @TableField("destination")
    @ExcelProperty("到（）站")
    private String destination;
    //交通工具
    @TableField("transport")
    @ExcelProperty("交通工具")
    private String transport;
    //座位号
    @TableField("seat")
    @ExcelProperty("座位号")
    private String seat;
    //陪同人员
    @TableField("company")
    @ExcelProperty("陪同人员")
    private String company;

    //车牌号/车次
    @TableField("car")
    @ExcelProperty("车牌号/车次")
    private String car;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.stuNum;
    }
}
