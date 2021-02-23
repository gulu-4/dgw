package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.chards.committee.domain.DataScope;
import com.chards.committee.dto.UserDataScope;
import lombok.Data;

import java.util.*;

/**
 * @author LiuSu
 * @create 2021/2/21 17:41
 */
@Data
public class PartTimeStaffVO {

    // 学号、姓名、性别、所管学生、手机号、角色（从tb_admin_role里直接查出来的那个）
    @ExcelProperty("工号")
    private String id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelIgnore
    private List<UserDataScope> userDataScopeList;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("角色")
    private String role;

}
