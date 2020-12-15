package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiuSu
 * @create 2020/12/15 12:25
 */
@Data
public class PsychologicalLevelCheckStatusVO {

    private Long id;

    private Integer checkStatus;

    private String instruction;
}
