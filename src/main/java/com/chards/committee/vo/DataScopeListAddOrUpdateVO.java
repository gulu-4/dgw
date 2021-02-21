package com.chards.committee.vo;

import com.chards.committee.dto.UserDataScope;
import lombok.Data;

import java.util.*;

/**
 * @author LiuSu
 * @create 2021/2/21 2:18
 */
@Data
public class DataScopeListAddOrUpdateVO {
    private String userId;

    private List<UserDataScope> dataScopeList;
}
