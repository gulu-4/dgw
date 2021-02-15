package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.User;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.mapper.UserMapper;
import com.chards.committee.vo.Code;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    CoreAdminService coreAdminService;
    @Autowired
    StuInfoService stuInfoService;

    public UserInfo getUserById(String userId){
        UserInfo userInfo = new UserInfo();
        CoreAdmin coreAdmin = coreAdminService.getById(userId);
        StuInfo stuInfo = stuInfoService.getById(userId);
        if (coreAdmin!=null){
            BeanUtils.copyProperties(coreAdmin, userInfo);
            return userInfo;
        } else if (stuInfo != null){
            BeanUtils.copyProperties(stuInfo,userInfo);
            return userInfo;
        }else {
            BusinessException.error(Code.USER_NOT_EXIST);
            return null;
        }

    }
}
