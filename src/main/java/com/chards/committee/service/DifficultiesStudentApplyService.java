package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.DifficultiesStudentApply;
import com.chards.committee.mapper.DifficultiesStudentApplyMapper;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.DifficultiesPassVO;
import com.chards.committee.vo.DifficultiesStudentGetParamVO;
import com.chards.committee.vo.DifficultiesStudentGetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author LiuSu
 * @create 2021/5/19 22:36
 */
@Service("difficultiesStudentApplyService")
public class DifficultiesStudentApplyService extends ServiceImpl<DifficultiesStudentApplyMapper, DifficultiesStudentApply> {

    @Autowired
    private DifficultiesStudentAssessmentService difficultiesStudentAssessmentService;

    /**
     * 申请表新增
     * @return
     */
    @Transactional
    public Long applyAdd(DifficultiesStudentApply difficultiesStudentApply){
        LocalDateTime now = LocalDateTime.now();
        Integer year = now.getYear();

        // 判断当前期该学生是否有提交过申请，如果有则返回失败
        DifficultiesStudentGetParamVO difficultiesStudentGetParamVO = new DifficultiesStudentGetParamVO();
        difficultiesStudentGetParamVO.setStuNum(RequestUtil.getId());
        difficultiesStudentGetParamVO.setStage(year);
        if (getListWithParam(difficultiesStudentGetParamVO).size() > 0) {
            return Long.valueOf(-1);
        }
        // 新增申请表
        difficultiesStudentApply.setStuNum(RequestUtil.getId());
        difficultiesStudentApply.setCreateTime(LocalDateTime.now());
        difficultiesStudentApply.setUpdateTime(LocalDateTime.now());
        difficultiesStudentApply.setLevel("无");
        difficultiesStudentApply.setStage(year);
        difficultiesStudentApply.setFirstCheck(0);
        difficultiesStudentApply.setSecondCheck(0);
        baseMapper.insert(difficultiesStudentApply);
        return difficultiesStudentApply.getId();
    }

    /**
     * 根据筛选条件获取申请信息
     */
    public List<DifficultiesStudentApply> getListWithParam(DifficultiesStudentGetParamVO difficultiesStudentGetParamVO){
        return baseMapper.getListWithParam(difficultiesStudentGetParamVO);
    }

    /**
     * 辅导员审核列表信息获取
     * @param difficultiesStudentGetParamVO
     * @param page
     * @return
     */
    public Page<DifficultiesStudentGetVO> getFirstList(DifficultiesStudentGetParamVO difficultiesStudentGetParamVO, Page<DifficultiesStudentGetVO> page) {
        Page<DifficultiesStudentGetVO> difficultiesStudentGetVOPage = baseMapper.getFirstList(difficultiesStudentGetParamVO,page);
        addLastInfoToList(difficultiesStudentGetVOPage);
        return difficultiesStudentGetVOPage;
    }

    public Page<DifficultiesStudentGetVO> getSecondList(DifficultiesStudentGetParamVO difficultiesStudentGetParamVO, Page<DifficultiesStudentGetVO> page) {
        Page<DifficultiesStudentGetVO> difficultiesStudentGetVOPage = baseMapper.getSecondList(difficultiesStudentGetParamVO,page);
        addLastInfoToList(difficultiesStudentGetVOPage);
        return difficultiesStudentGetVOPage;
    }

    private void addLastInfoToList(Page<DifficultiesStudentGetVO> difficultiesStudentGetVOPage) {
        List<DifficultiesStudentGetVO> difficultiesStudentGetVOList = difficultiesStudentGetVOPage.getRecords();
        for (DifficultiesStudentGetVO difficultiesStudentGetVO : difficultiesStudentGetVOList) {
            // 获取每人上期建档信息
            LocalDateTime now = LocalDateTime.now();
            Integer lastYear = now.getYear() - 1;
            DifficultiesStudentGetParamVO getLastParam = new DifficultiesStudentGetParamVO();
            getLastParam.setStuNum(difficultiesStudentGetVO.getStuNum());
            getLastParam.setStage(lastYear);
            List<DifficultiesStudentApply> difficultiesStudentApplyList = baseMapper.getListWithParam(getLastParam);
            if (difficultiesStudentApplyList.size() > 0){
                difficultiesStudentGetVO.setLastId(difficultiesStudentApplyList.get(0).getId());
                difficultiesStudentGetVO.setLastLevel(difficultiesStudentApplyList.get(0).getLevel());
            }else {
                difficultiesStudentGetVO.setLastId(null);
                difficultiesStudentGetVO.setLastLevel("无");
            }

        }
    }

    public Integer check(DifficultiesPassVO difficultiesPassVO) {
        return baseMapper.check(difficultiesPassVO);
    }

    @Transactional
    public Integer deleteById(Serializable id) {
        baseMapper.deleteById(id);
        return difficultiesStudentAssessmentService.deleteByApplyId(id);
    }
}
