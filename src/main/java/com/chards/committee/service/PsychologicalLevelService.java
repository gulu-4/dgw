package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.mapper.PsychologicalLevelMapper;
import com.chards.committee.vo.PsychologicalLevelUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/11 18:58
 */
@Service
public class PsychologicalLevelService extends ServiceImpl<PsychologicalLevelMapper, PsychologicalLevel> {

    @Autowired
    private PsychologicalLevelMapper psychologicalLevelMapper;

    /**
     * 更新审核状态
     * */
    public int checkStatus(Long id,Integer checkStatus,String reviewer,
                           String instruction){
        LocalDateTime checkTime = LocalDateTime.now();
        return psychologicalLevelMapper.checkStatus(id,checkStatus,reviewer,instruction,checkTime);
    }
    /**
     * 更新内容
     */
    public int updatePsyLevelById(PsychologicalLevelUpdateVO psychologicalLevelUpdateVO){
        return psychologicalLevelMapper.updatePsyLevelById(psychologicalLevelUpdateVO);
    }

    /**
     * 根据审核状态分页获取数据
     */
    public Page<PsychologicalLevel> getPsychologicalLevelPage(Page<PsychologicalLevel> page,Integer checkStatus,String recorder) {
        return baseMapper.getPsychologicalLevelPage(page,checkStatus,recorder);
    }


}
