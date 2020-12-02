package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


import java.util.Date;

@Data
@TableName("user")
public class User extends Model<User> {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private String type;
    private String account;
    private String email;
    private String phone;
    private String password;
    private String imgurl;
    private Date createtime;
    private Date updatetime;
}
