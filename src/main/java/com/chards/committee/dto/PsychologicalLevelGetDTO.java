package com.chards.committee.dto;

import com.chards.committee.vo.PsychologicalLevelQueryNewParamVO;
import lombok.Data;

/**
 * 在心理等级信息筛选接口接收参数用的PsychologicalLevelQueryNewParamVO基础上拓展一个AdminWorkDTO，以便于在SQL中针对不同的权限做不同的处理。
 */
@Data
public class PsychologicalLevelGetDTO extends PsychologicalLevelQueryNewParamVO {
    private AdminWorkDTO adminWorkDTO;
}
