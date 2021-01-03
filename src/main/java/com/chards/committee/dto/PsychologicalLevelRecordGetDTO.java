package com.chards.committee.dto;

import lombok.Data;

@Data
public class PsychologicalLevelRecordGetDTO {
    private Integer checkStatus;

    private String stuNum;

    private AdminWorkDTO adminWorkDTO;
}
