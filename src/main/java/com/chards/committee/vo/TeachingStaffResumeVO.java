package com.chards.committee.vo;

import com.chards.committee.domain.TeachingStaffResume;
import lombok.Data;

@Data
public class TeachingStaffResumeVO extends TeachingStaffResume {
    //    仅用于补充返回姓名信息
    private String name;
}
