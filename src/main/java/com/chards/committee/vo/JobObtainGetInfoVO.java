package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/1/26 12:49
 */
@Data
public class JobObtainGetInfoVO {
    private Long id;

    private String stuNum;

    // 返回学生基本信息
    private String name;

    private String gender;

    private String department;

    private String grade;

    private String classes;

    private String state;

    private String intention;

    private String progress;

    private String helpType;

    private String remark;

    //这里返回填写者名字
    private String recorder;

    private LocalDateTime recordedTime;
}
