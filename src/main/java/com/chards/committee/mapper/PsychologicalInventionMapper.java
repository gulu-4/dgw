package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.domain.PsychologicalInvention;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiuSu
 * @create 2020/12/11 16:58
 */
public interface PsychologicalInventionMapper extends BaseMapper<PsychologicalInvention> {
    List<PsychologicalInvention> getInventionsByStuNum(@Param("stuNum") String stuNum);
}
