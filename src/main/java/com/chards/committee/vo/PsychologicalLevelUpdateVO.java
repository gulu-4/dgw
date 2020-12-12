package com.chards.committee.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/11 20:33
 */
@Data
public class PsychologicalLevelUpdateVO {

    private Long id;
    private String learningProblem;

    private String economicProblem;

    private String abnormalBehaviorProblem;

    private String lifeEventProblem;

    private String personalityProblem;

    private String epidemicPsychologicalProblem;

    private String level;

    private String basis;

    private String remark;

    private LocalDateTime recordedTime;

}
