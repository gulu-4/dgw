package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chards.committee.domain.DataScope;
import com.chards.committee.dto.UserDataScope;
import com.chards.committee.mapper.DataScopeMapper;
import org.springframework.stereotype.Service;

import java.util.List;



@Service("dataScopeService")
public class DataScopeService extends ServiceImpl<DataScopeMapper, DataScope> {
    public List<UserDataScope> getUserDataScope(String userId) {
        return baseMapper.getUserDataScopeById(userId);
    }
}
