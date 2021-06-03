package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.dto.PsychologicalLevelGetDTO;
import com.chards.committee.dto.PsychologicalLevelRecordGetDTO;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.*;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

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

//    Page<PsychologicalLevelCheckSelectVO> getPsychologicalLevelPage(@Param("page") Page<PsychologicalLevelCheckSelectVO> page,
//                                                       @Param("checkStatus") Integer checkStatus,
//                                                       @Param("recorder") String recorder,
//                                                       @Param("stuNum") String stuNum);
    /**
     * 通过学号和审核状态对心理定级记录进行筛选查看
     * @param page
     * @param psychologicalLevelRecordGetDTO 构造的一个包含AdminWorkDTO的DTO，以便于在SQL中针对不同的权限做不同的处理。
     * @return
     */
    @DataScope()
    Page<PsychologicalLevelCheckSelectVO> getPsychologicalLevelPage(@Param("page") Page<PsychologicalLevelCheckSelectVO> page,
                                                                    @Param("param") PsychologicalLevelRecordGetDTO psychologicalLevelRecordGetDTO);

    List<PsychologicalLevel> getPsychologicalLevelByStuNum(@Param("stuNum") String stuNum);

    /**
     *
     * @param page
     * @param psychologicalLevelGetDTO 在心理等级信息筛选接口接收参数用的PsychologicalLevelQueryNewParamVO基础上拓展一个AdminWorkDTO，以便于在SQL中针对不同的权限做不同的处理。
     * @return
     */
    @DataScope()
    Page<PsychologicalLevelGetByStuNumVO> getPsychologicalLevelByParams(@Param("page") Page<PsychologicalLevelGetByStuNumVO> page,
                                                                        @Param("psychologicalLGD") PsychologicalLevelGetDTO psychologicalLevelGetDTO);
    /**
     *
     * @param psychologicalLevelGetDTO
     * @return
     */
    @DataScope()
    List<PsychologicalLevelGetByStuNumVO1> getPsychologicalLevelByParams1(@Param("psychologicalLGD") PsychologicalLevelGetDTO psychologicalLevelGetDTO);

}
