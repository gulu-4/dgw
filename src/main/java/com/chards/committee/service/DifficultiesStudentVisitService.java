package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.DifficultiesStudentVisit;
import com.chards.committee.mapper.DifficultiesStudentVisitMapper;
import com.chards.committee.vo.DifficultiesStudentVisitGetParamVO;
import com.chards.committee.vo.DifficultiesStudentVisitGetVO;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author LiuSu
 * @create 2021/7/6 18:48
 */
@Service("difficultiesStudentVisitService")
public class DifficultiesStudentVisitService extends ServiceImpl<DifficultiesStudentVisitMapper, DifficultiesStudentVisit> {
    public List<DifficultiesStudentVisitGetVO> getStudentsVisits(DifficultiesStudentVisitGetParamVO difficultiesStudentVisitGetParamVO) {
        return baseMapper.getStudentsVisits(difficultiesStudentVisitGetParamVO);
    }
}
