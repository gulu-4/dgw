package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.ProvideMoney;
import com.chards.committee.mapper.ProvideMoneyMapper;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.ProvideMoneyGetDetailVO;
import com.chards.committee.vo.ProvideMoneyGetParamVO;
import com.chards.committee.vo.ProvideMoneyPassVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author LiuSu
 * @create 2021/7/24 17:58
 */
@Service("provideMoneyService")
public class ProvideMoneyService extends ServiceImpl<ProvideMoneyMapper, ProvideMoney> {

    public Long applyAdd(ProvideMoney provideMoney){
        // 判断该学生改期该类型申请表是否申请过，如果有则返回失败'
        ProvideMoneyGetParamVO provideMoneyGetParamVO = new ProvideMoneyGetParamVO();
        provideMoneyGetParamVO.setStuNum(RequestUtil.getId());
        provideMoneyGetParamVO.setStage(provideMoney.getStage());
        provideMoneyGetParamVO.setType(provideMoney.getType());
        if (getListWithParam(provideMoneyGetParamVO).size() > 0){
            return Long.valueOf(-1);
        }
        // 新增申请表
        provideMoney.setStuNum(RequestUtil.getId());
        provideMoney.setCreateTime(LocalDateTime.now());
        provideMoney.setUpdateTime(LocalDateTime.now());
        provideMoney.setFirstCheck(0);
        provideMoney.setSecondCheck(0);
        baseMapper.insert(provideMoney);
        return provideMoney.getId();
    }

    /**
     * 辅导员审核列表信息获取
     * @param provideMoneyGetParamVO
     * @param page
     * @return
     */
    public Page<ProvideMoneyGetDetailVO> getFirstList(ProvideMoneyGetParamVO provideMoneyGetParamVO, Page<ProvideMoneyGetDetailVO> page) {
        return baseMapper.getFirstList(provideMoneyGetParamVO,page);
    }

    /**
     * 学工处审核列表信息获取
     * @param provideMoneyGetParamVO
     * @param page
     * @return
     */
    public Page<ProvideMoneyGetDetailVO> getSecondList(ProvideMoneyGetParamVO provideMoneyGetParamVO, Page<ProvideMoneyGetDetailVO> page) {
        return baseMapper.getSecondList(provideMoneyGetParamVO,page);
    }

    /**
     * 审核
     * @param provideMoneyPassVO
     * @return
     */
    public Integer check(ProvideMoneyPassVO provideMoneyPassVO) {
        return baseMapper.check(provideMoneyPassVO);
    }

    /**
     * 该学生该期该类型申请表是否申请过
     * @return
     */
    public Map<String, Long> getHadApplied(String type,String stage) {
        ProvideMoneyGetParamVO provideMoneyGetParamVO = new ProvideMoneyGetParamVO();
        provideMoneyGetParamVO.setStuNum(RequestUtil.getId());
        provideMoneyGetParamVO.setStage(stage);
        provideMoneyGetParamVO.setType(type);
        Map<String,Long> resultMap= new HashMap<String,Long>();
        List<ProvideMoney> provideMoneyList = getListWithParam(provideMoneyGetParamVO);
        if (provideMoneyList.size() > 0) {
            resultMap.put("id",provideMoneyList.get(0).getId());
        }else{
            resultMap.put("id",Long.valueOf(0));
        }
        return resultMap;
    }

    /**
     * 筛选
     * @param provideMoneyGetParamVO
     * @return
     */
    public List<ProvideMoney> getListWithParam(ProvideMoneyGetParamVO provideMoneyGetParamVO) {
        return baseMapper.getListWithParam(provideMoneyGetParamVO);
    }

}
