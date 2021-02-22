package com.chards.committee.vo;

import com.chards.committee.dto.UserDataScope;
import com.chards.committee.dto.UserInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserPermissionInfoVO extends UserInfo {
    private List<String> roles = new ArrayList<>();
    private List<UserDataScope> userDataScopeList = new ArrayList<>();
}
