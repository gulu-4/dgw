package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author LiuSu
 * @since 2021-03-19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("field_order")
//@ApiModel(value="FieldOrder对象", description="")
public class FieldOrder extends Model<FieldOrder> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "需要租借场地的id")
    private Long rentId;

    @ApiModelProperty(value = "预约人学号")
    private String stuNumber;

    @ApiModelProperty(value = "预约人手机号")
    private String phone;

    @ApiModelProperty(value = "预约人邮箱")
    private String email;

    @ApiModelProperty(value = "申请理由")
    private String reason;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "申请状态，0未审核，1审核通过，-1审核未通过")
    private Integer status;

    @ApiModelProperty(value = "审核人工号或者学号")
    private String checker;

    @ApiModelProperty(value = "审核情况说明")
    private String checkRemark;

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
