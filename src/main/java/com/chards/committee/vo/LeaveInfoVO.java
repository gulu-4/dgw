package com.chards.committee.vo;

import com.chards.committee.domain.Leave;
import lombok.Data;

/**
 * @Author: chards
 * @Date: 18:29 2020/8/26
 */
@Data
public class LeaveInfoVO extends Leave {
    private String name;
    private String department;
    private String classes;
}
