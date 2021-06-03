package com.chards.committee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.TeachingStaffResume;
import com.chards.committee.mapper.TeachingStaffResumeMapper;
import com.chards.committee.vo.PsychologicalCounselingCaseDetailVO;
import com.chards.committee.vo.TeachingStaffResumeVO;
import com.chards.committee.vo.teachStaffExport.TeachStaffBasicExportVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuSu
 * @create 2021/2/21 0:42
 */
@Service("teachingStaffResumeService")
public class TeachingStaffResumeService extends ServiceImpl<TeachingStaffResumeMapper, TeachingStaffResume> {

    @Autowired
    UserService userService;

    @Autowired
    CoreAdminService coreAdminService;

    public TeachingStaffResume getByStaffId(String staffId){
        return baseMapper.getByStaffId(staffId);
    }

    public Page<TeachingStaffResumeVO> getPage(Page<TeachingStaffResumeVO> page, Integer checkStatus){

        Page<TeachingStaffResumeVO> teachingStaffResumeVOPage = baseMapper.getPage(page, checkStatus);
        List<TeachingStaffResumeVO> teachingStaffResumeVOList = teachingStaffResumeVOPage.getRecords();
        for (TeachingStaffResumeVO teachingStaffResumeVO: teachingStaffResumeVOList){
            teachingStaffResumeVO.setName(userService.getUserById(teachingStaffResumeVO.getStaffId()).getName());
        }
        teachingStaffResumeVOPage.setRecords(teachingStaffResumeVOList);
        return teachingStaffResumeVOPage;
    }

    /**
     * 导表
     */
    public List<TeachStaffBasicExportVO> getList() {
        QueryWrapper<TeachingStaffResume> queryWrapper = new QueryWrapper<>();
        List<TeachingStaffResume> teachingStaffResumeList = baseMapper.selectList(queryWrapper);
        List<TeachStaffBasicExportVO> teachStaffBasicExportVOList = new ArrayList<>();
        for (int i = 0; i < teachingStaffResumeList.size(); i++) {
            TeachStaffBasicExportVO teachStaffBasicExportVO = new TeachStaffBasicExportVO();
            BeanUtils.copyProperties(teachingStaffResumeList.get(i),teachStaffBasicExportVO);
            CoreAdmin coreAdmin = coreAdminService.getById(teachingStaffResumeList.get(i).getStaffId());
            teachStaffBasicExportVO.setIndex(i+1);
            teachStaffBasicExportVO.setName(coreAdmin.getName());
            teachStaffBasicExportVO.setGender(coreAdmin.getGender());
            teachStaffBasicExportVO.setDepartment(coreAdmin.getDepartment());
            teachStaffBasicExportVOList.add(teachStaffBasicExportVO);
        }
        return teachStaffBasicExportVOList;
    }
}
