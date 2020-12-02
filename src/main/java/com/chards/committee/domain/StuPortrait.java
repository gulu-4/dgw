package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (StuPortrait)表实体类
 *
 * @author chards
 * @since 2020-08-01 16:05:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("stu_portrait")
public class StuPortrait extends Model<StuPortrait> {
    private static final long serialVersionUID = -61352183236378643L;
    //主键id
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    //学号    
    @TableField("stu_num")
    private String stuNum;
    //性格分类    
    @TableField("kind")
    private Integer kind;
    //内容    
    @TableField("content")
    private String content;
    //创建时间    
    @TableField("create_time")
    private LocalDateTime createTime;
    //填写人工号    
    @TableField("number")
    private String number;

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