package com.chards.committee.vo;

import com.chards.committee.dto.UserDataScope;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @author LiuSu
 * @create 2021/2/21 2:18
 */
@Data
public class DataScopeListAddOrUpdateVO {
    @NotBlank(message = "用户工号或者学号不能为空")
    private String userId;

    private List<UserDataScope> dataScopeList;
}
