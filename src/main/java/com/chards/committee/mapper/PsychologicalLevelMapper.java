package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.vo.PsychologicalLevelUpdateVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/11 16:56
 */
public interface PsychologicalLevelMapper extends BaseMapper<PsychologicalLevel> {
    int checkStatus(@Param("id") Long id,
                    @Param("checkStatus") Integer checkStatus,
                    @Param("reviewer") String reviewer,
                    @Param("instruction") String instruction,
                    @Param("checkTime") LocalDateTime checkTime);

    int updatePsyLevelById(@Param("psychologicalLevelUpdateVO")PsychologicalLevelUpdateVO psychologicalLevelUpdateVO);

    Page<PsychologicalLevel> getPsychologicalLevelPage(@Param("page") Page<PsychologicalLevel> page,
                                                       @Param("checkStatus") Integer checkStatus,
                                                       @Param("recorder") String recorder);
}
