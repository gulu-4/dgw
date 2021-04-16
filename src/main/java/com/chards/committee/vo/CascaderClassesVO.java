package com.chards.committee.vo;

import lombok.Data;
import java.util.List;

/**
 * @author LiuSu
 * @create 2021/4/16 11:49
 */
@Data
public class CascaderClassesVO {

    private String value;

    private List<CascaderClassesVO> children;
}
