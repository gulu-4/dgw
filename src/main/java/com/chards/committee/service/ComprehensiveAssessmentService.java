package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.ComprehensiveAssessment;
import com.chards.committee.mapper.ComprehensiveAssessmentMapper;
import com.chards.committee.vo.ComprehensiveAssessmentSeniorVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "ComprehensiveAssessmentService")
public class ComprehensiveAssessmentService extends ServiceImpl<ComprehensiveAssessmentMapper, ComprehensiveAssessment> {

    /**
     * 因为可能会同一年的文件多次导入，
     * 所以导入文件时，先检测数据库中是否有正在导入的该年的元组；
     * 即便是不同年份的id重复可以，应为在不同年，所导出时对应的序号仍为对应年份当时导入的序号
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int importComprehensiveAssessmentInfo(List<ComprehensiveAssessment> list){
        QueryWrapper<ComprehensiveAssessment> queryWrapper = new QueryWrapper<>();
        if(list != null && list.size() != 0){
            queryWrapper.eq("create_time",list.get(0).getCreateTime());
            List<ComprehensiveAssessment> queryList = baseMapper.selectList(queryWrapper);
            //当有该年的数据时，先将其全部删除再重新导入
            QueryWrapper<ComprehensiveAssessment> deleteWrapper = new QueryWrapper<>();
            if (queryList != null){
                deleteWrapper.eq("create_time",list.get(0).getCreateTime());
                int flag = baseMapper.delete(deleteWrapper);
            }
            return baseMapper.importBatch(list);
        }

        return 0;

    }

    public Page<ComprehensiveAssessment> getComprehensiveAssessmentInfo(Page<ComprehensiveAssessment> page, ComprehensiveAssessmentSeniorVO comprehensiveAssessmentSeniorVO){
        return baseMapper.getInfoSeniorSearch(page,comprehensiveAssessmentSeniorVO.getDepartment(),comprehensiveAssessmentSeniorVO.getCreate_time());
    }


    public List<ComprehensiveAssessment> exportComprehensiveAssessmentInfo(String year){
        return baseMapper.exportAllInfo(year);
    }

}
