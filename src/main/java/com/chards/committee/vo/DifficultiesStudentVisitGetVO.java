package com.chards.committee.vo;

import com.chards.committee.domain.DifficultiesStudentVisit;
import lombok.Data;
import java.util.List;

/**
 * @author LiuSu
 * @create 2021/7/6 19:03
 */
@Data
public class DifficultiesStudentVisitGetVO {
    //学号、姓名、性别、班级、走访记录1（走访时间、走访小结、材料情况、走访人员）
    private String stuNumber;   //学号
    private String name;
    private String gender;
    private String department;
    private List<DifficultiesStudentVisit> difficultiesStudentVisitList;
}
