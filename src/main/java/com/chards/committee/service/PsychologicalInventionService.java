package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalInvention;
import com.chards.committee.mapper.PsychologicalInventionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiuSu
 * @create 2020/12/11 17:22
 */
@Service
public class PsychologicalInventionService extends ServiceImpl<PsychologicalInventionMapper, PsychologicalInvention> {

    @Autowired
    private PsychologicalInventionMapper psychologicalInventionMapper;

    /**
     * 通过学生学号查询干预情况
     */
    public List<PsychologicalInvention> getInventionsByStuNum(String stuNum){
        return psychologicalInventionMapper.getInventionsByStuNum(stuNum);
    }
}
