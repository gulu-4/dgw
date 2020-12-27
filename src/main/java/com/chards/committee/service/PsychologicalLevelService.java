package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.PsychologicalInvention;
import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.mapper.PsychologicalLevelMapper;
import com.chards.committee.vo.*;
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
     * v2 添加字段判断学生是否是第一次进行关爱
     */
    public Page<PsychologicalLevelCheckSelectVO> getPsychologicalLevelPage(Page<PsychologicalLevelCheckSelectVO> page, Integer checkStatus, String recorder, String stuNum) {
        // 判断是否是第一次进行关爱
        Page<PsychologicalLevelCheckSelectVO> psychologicalLevelCheckSelectVOPage = baseMapper.getPsychologicalLevelPage(page,checkStatus,recorder,stuNum);
        List<PsychologicalLevelCheckSelectVO> pSList = psychologicalLevelCheckSelectVOPage.getRecords();
        for (PsychologicalLevelCheckSelectVO psychologicalLevelCheckSelectVO : pSList){
            List<PsychologicalLevel> levels = psychologicalLevelMapper.getPsychologicalLevelByStuNum(psychologicalLevelCheckSelectVO.getStuNum());
            if (levels.size() > 1) {
                // 大于1说明不是第一次进行关爱
                psychologicalLevelCheckSelectVO.setIsFirstCare(false);
            }else{
                psychologicalLevelCheckSelectVO.setIsFirstCare(true);
            }
        }
        return psychologicalLevelCheckSelectVOPage;
    }

    /**
     * 根据学生学号获取当前学生的定级信息
     */
    public PsychologicalLevelGetByStuNumVO getPsychologicalLevelGetByStuNumVO(String stuNum){
        PsychologicalLevelGetByStuNumVO psychologicalLevelGetByStuNumVO = new PsychologicalLevelGetByStuNumVO();
        // 获取psychologicalLevels
        // 必须是已经审核通过了才能返回，否则不可以进行返回
        List<PsychologicalLevel> psychologicalLevels = isCheckedTrue(psychologicalLevelMapper.getPsychologicalLevelByStuNum(stuNum));
        if (!psychologicalLevels.isEmpty()){
            PsychologicalLevel psychologicalLevel = psychologicalLevels.get(0);
            BeanUtils.copyProperties(psychologicalLevel,psychologicalLevelGetByStuNumVO);

            // 通过当前recorder 和 reviewer 获取老师信息
            psychologicalLevelGetByStuNumVO.setRecorders(getCoreAdminBasic(psychologicalLevel.getRecorder()));
            psychologicalLevelGetByStuNumVO.setReviewers(getCoreAdminBasic(psychologicalLevel.getReviewer()));
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
     * 根据筛选条件查询定级记录
     */
    public Page<PsychologicalLevelGetByStuNumVO> getPsychologicalLevelByParams(Page<PsychologicalLevelGetByStuNumVO> page,
                                                                               PsychologicalLevelQueryNewParamVO psychologicalLQNPVO){
        // 封装参数，对线索进行正则匹配
        if (psychologicalLQNPVO.getClues()!=null){
            for (int i = 0;i < psychologicalLQNPVO.getClues().size(); i++){
                String clue = psychologicalLQNPVO.getClues().get(i);
                psychologicalLQNPVO.getClues().set(i,"(^|[^0-9])"+ clue +"[^0-9]");
            }
        }

        // 需要对返回的数据进行记录者的处理
        Page<PsychologicalLevelGetByStuNumVO> psychologicalLevelGetByStuNumVOPage = baseMapper.getPsychologicalLevelByParams(page,psychologicalLQNPVO);
        List<PsychologicalLevelGetByStuNumVO> psychologicalLevelGetByStuNumVOList = psychologicalLevelGetByStuNumVOPage.getRecords();
        for (PsychologicalLevelGetByStuNumVO psychologicalLevelGetByStuNumVO : psychologicalLevelGetByStuNumVOList){
            // 通过当前recorder 和 reviewer 获取老师信息
            psychologicalLevelGetByStuNumVO.setRecorders(getCoreAdminBasic(psychologicalLevelGetByStuNumVO.getRecorder()));
            psychologicalLevelGetByStuNumVO.setReviewers(getCoreAdminBasic(psychologicalLevelGetByStuNumVO.getReviewer()));
        }
        return psychologicalLevelGetByStuNumVOPage;
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
