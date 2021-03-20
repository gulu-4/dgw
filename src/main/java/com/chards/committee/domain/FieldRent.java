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

import javax.validation.constraints.NotEmpty;
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
@TableName("field_rent")
//@ApiModel(value="FieldRent对象", description="")
public class FieldRent extends Model<FieldRent> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "负责人，学号或者工号，逗号分隔")
    private String manager;

    @ApiModelProperty(value = "可供租借场地名称")
    private String name;

    @ApiModelProperty(value = "图片的单个名称，上传时统一格式，返回时拼接")
    @NotEmpty(message = "图片不能为空")
    private String pictures;

    @ApiModelProperty(value = "容量")
    private String capacity;

    @ApiModelProperty(value = "地点")
    private String location;

    @ApiModelProperty(value = "功能")
    private String functions;

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
