package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chards.committee.domain.DataScope;
import com.chards.committee.dto.UserDataScope;
import com.chards.committee.mapper.DataScopeMapper;
import com.chards.committee.vo.DataScopeListAddOrUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;



@Service("dataScopeService")
public class DataScopeService extends ServiceImpl<DataScopeMapper, DataScope> {
    public List<UserDataScope> getUserDataScope(String userId) {
        return baseMapper.getUserDataScopeById(userId);
    }

    // 新增和更新
    public String addOrUpdateUserDataScopeList(DataScopeListAddOrUpdateVO dataScopeListAddOrUpdateVO) {
        String userId = dataScopeListAddOrUpdateVO.getUserId();
        String returnStr = "设置成功";
        List<UserDataScope> dataScopeList = dataScopeListAddOrUpdateVO.getDataScopeList();
        // 判断是否存在， 存在则删除
        List<UserDataScope> dataScopeList1 = baseMapper.getUserDataScopeById(userId);
        if (dataScopeList1.size() > 0) {
            returnStr = "更新成功";
            // 通过用户id删除
            deleteByUserId(userId);
        }

        // 新增
        for (UserDataScope userDataScope : dataScopeList) {
            DataScope dataScope = new DataScope();
            BeanUtils.copyProperties(userDataScope,dataScope);
            dataScope.setUserId(userId);
            save(dataScope);
        }
        return returnStr;
    }

    public int deleteByUserId(String userId) {
        return baseMapper.deleteByUserId(userId);
    }
}
