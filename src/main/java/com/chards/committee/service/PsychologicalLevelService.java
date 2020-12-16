package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.PsychologicalInvention;
import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.mapper.PsychologicalLevelMapper;
import com.chards.committee.vo.CoreAdminBasicVO;
import com.chards.committee.vo.PsychologicalLevelCheckStatusVO;
import com.chards.committee.vo.PsychologicalLevelGetByStuNumVO;
import com.chards.committee.vo.PsychologicalLevelUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/11 18:58
 */
@Service
public class PsychologicalLevelService extends ServiceImpl<PsychologicalLevelMapper, PsychologicalLevel> {

    @Autowired
    private PsychologicalLevelMapper psychologicalLevelMapper;

    @Autowired
    private PsychologicalInventionService psychologicalInventionService;

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private CoreAdminService coreAdminService;

    /**
     * 更新审核状态
     * */
    public int checkStatus(PsychologicalLevelCheckStatusVO psychologicalLevelCheckStatusVO,String reviewer){
        LocalDateTime checkTime = LocalDateTime.now();
        Long id = psychologicalLevelCheckStatusVO.getId();
        Integer checkStatus = psychologicalLevelCheckStatusVO.getCheckStatus();
        String instruction = psychologicalLevelCheckStatusVO.getInstruction();
        return psychologicalLevelMapper.checkStatus(id,checkStatus,reviewer,instruction,checkTime);
    }
    /**
     * 更新内容
     */
    public int updatePsyLevelById(PsychologicalLevelUpdateVO psychologicalLevelUpdateVO){
        return psychologicalLevelMapper.updatePsyLevelById(psychologicalLevelUpdateVO);
    }

    /**
     * 根据审核状态分页获取数据
     */
    public Page<PsychologicalLevel> getPsychologicalLevelPage(Page<PsychologicalLevel> page,Integer checkStatus,String recorder,String stuNum) {
        return baseMapper.getPsychologicalLevelPage(page,checkStatus,recorder,stuNum);
    }

    /**
     * 根据学生学号获取当前学生的定级信息
     */
    public PsychologicalLevelGetByStuNumVO getPsychologicalLevelGetByStuNumVO(String stuNum){
        PsychologicalLevelGetByStuNumVO psychologicalLevelGetByStuNumVO = new PsychologicalLevelGetByStuNumVO();
        // 获取psychologicalLevels
        List<PsychologicalLevel> psychologicalLevels = isCheckedTrue(psychologicalLevelMapper.getPsychologicalLevelByStuNum(stuNum));
        // 必须是已经审核通过了才能返回，否则不可以进行返回
        if (!psychologicalLevels.isEmpty()){
            PsychologicalLevel initPL = psychologicalLevels.get(0);
            PsychologicalLevel nowPL = psychologicalLevels.get(psychologicalLevels.size() - 1);
            BeanUtils.copyProperties(nowPL,psychologicalLevelGetByStuNumVO);
            // 通过初始recorder 和 reviewer 获取老师信息
            psychologicalLevelGetByStuNumVO.setInitRecorder(getCoreAdminBasic(initPL.getRecorder()));
            psychologicalLevelGetByStuNumVO.setInitReviewer(getCoreAdminBasic(initPL.getReviewer()));
            psychologicalLevelGetByStuNumVO.setInitLevel(initPL.getLevel());
            psychologicalLevelGetByStuNumVO.setInitTime(initPL.getUpdateTime());
            // 通过当前recorder 和 reviewer 获取老师信息
            psychologicalLevelGetByStuNumVO.setRecorder(getCoreAdminBasic(nowPL.getRecorder()));
            psychologicalLevelGetByStuNumVO.setReviewer(getCoreAdminBasic(nowPL.getReviewer()));
            // 获取学生信息
            StuInfo stuInfo = stuInfoService.getById(stuNum);
            psychologicalLevelGetByStuNumVO.setDepartment(stuInfo.getDepartment());
            psychologicalLevelGetByStuNumVO.setClasses(stuInfo.getClasses());
            psychologicalLevelGetByStuNumVO.setName(stuInfo.getName());
            psychologicalLevelGetByStuNumVO.setGender(stuInfo.getGender());
            // 获取学院干预情况
            List<PsychologicalInvention> psychologicalInventionList = psychologicalInventionService.getInventionsByStuNum(stuNum);
            psychologicalLevelGetByStuNumVO.setPsychologicalInventionList(psychologicalInventionList);
            return psychologicalLevelGetByStuNumVO;
        }else{
            return null;
        }
    }


    /**
     * 如果根据学号查询到的定级记录不是空
     * 就进行审核是否通过判断，返回List<PsychologicalLevel>
     */
    public List<PsychologicalLevel> isCheckedTrue(List<PsychologicalLevel> psychologicalLevels) {
        List<PsychologicalLevel> psychologicalLevelBack = new ArrayList<>();
        for (PsychologicalLevel p : psychologicalLevels) {
            if (p.getCheckStatus() == 1){
                psychologicalLevelBack.add(p);
            }
        }
        return psychologicalLevelBack;
    }

    /**
     * 根据admin工号返回工号和姓名
     */
    public CoreAdminBasicVO getCoreAdminBasic(String id){
        CoreAdmin coreAdmin = coreAdminService.getById(id);
        CoreAdminBasicVO coreAdminBasicVO = new CoreAdminBasicVO();
        BeanUtils.copyProperties(coreAdmin,coreAdminBasicVO);
        return coreAdminBasicVO;
    }


}
