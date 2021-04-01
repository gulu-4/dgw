package com.chards.committee.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/3/31 9:47
 */
@Data
public class FieldOrderGetVO {
    private Long id;

    private Long rentId;

    private String rentName;  //预约场地名称
    private String rentLocation;  //预约场地地点

    private String stuNumber;
    private String name;  //预约人的名字

    private String phone;

    private String email;

    private String reason;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private String checker;

    private String checkRemark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
