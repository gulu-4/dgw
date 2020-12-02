package com.chards.committee.dto;

import com.chards.committee.vo.StuInfoSeniorVO;
import lombok.Data;

/**
 * @Author: chards
 * @Date: 2:09 2020/8/20
 */
@Data
public class StuInfoSeniorDTO extends StuInfoSeniorVO {
    private AdminWorkDTO adminWorkDTO;
}
