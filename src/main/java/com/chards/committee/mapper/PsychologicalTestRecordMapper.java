package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.domain.PsychologicalTestRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiuSu
 * @create 2020/12/11 16:56
 */
public interface PsychologicalTestRecordMapper extends BaseMapper<PsychologicalTestRecord> {
    List<PsychologicalTestRecord> getAllByStuNum(@Param("stuNum") String stuNum);
}
