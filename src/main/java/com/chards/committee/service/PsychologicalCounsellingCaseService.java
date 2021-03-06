package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.mapper.PsychologicalCounsellingCaseMapper;
import com.chards.committee.mapper.PsychologicalTestRecordMapper;
import com.chards.committee.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PsychologicalCounsellingCaseService extends ServiceImpl<PsychologicalCounsellingCaseMapper, PsychologicalCounsellingCase> {

    @Autowired
    private PsychologicalCounsellingCaseMapper psychologicalCounsellingCaseMapper;

    @Autowired
    private PsychologicalTestRecordMapper psychologicalTestRecordMapper;

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private CoreAdminService coreAdminService;

    @Autowired
    private UserService userService;

    /**
     * 筛选心理咨询记录，可通过学号、起止时间筛选
     * 传入返回数据VO的page，以及请求参数VO的实例
     * 2021.1.1 加 poplar
     */
    public Page<PsychologicalCounselingCaseDetailVO> getAllCounselingCaseByParams(Page<PsychologicalCounselingCaseDetailVO> page, PsychologicalCounsellingCaseSelectVO psychologicalCounsellingCaseSelectVO){

        String startTime = timeTransfer(psychologicalCounsellingCaseSelectVO.getStartTime());
        String endTime = timeTransfer(psychologicalCounsellingCaseSelectVO.getEndTime());
        //        需要返回的咨询记录详情Page VO
        Page<PsychologicalCounselingCaseDetailVO> psychologicalCounselingCaseDetailVOPage =
                baseMapper.getAllCounselingCaseByParams(page,psychologicalCounsellingCaseSelectVO.getStuNum(),psychologicalCounsellingCaseSelectVO.getCounselor(),startTime,endTime);
        List<PsychologicalCounselingCaseDetailVO> PsychologicalCounselingCaseDetailVOList = psychologicalCounselingCaseDetailVOPage.getRecords();

        for (PsychologicalCounselingCaseDetailVO psychologicalCounselingCaseDetailVO:PsychologicalCounselingCaseDetailVOList){
            String stuNum = psychologicalCounselingCaseDetailVO.getStuNum();
            if (userService.getUserById(stuNum) == null) {
                continue;
            }
            StuInfo stuInfo = stuInfoService.getById(stuNum);

            psychologicalCounselingCaseDetailVO.setClasses(stuInfo.getClasses());
            psychologicalCounselingCaseDetailVO.setDepartment(stuInfo.getDepartment());
            psychologicalCounselingCaseDetailVO.setGrade(stuInfo.getGrade());
            psychologicalCounselingCaseDetailVO.setGender(stuInfo.getGender());
            psychologicalCounselingCaseDetailVO.setName(stuInfo.getName());
            psychologicalCounselingCaseDetailVO.setPhone(stuInfo.getPhone());
            psychologicalCounselingCaseDetailVO.setRecorders(getCoreAdminBasic(psychologicalCounselingCaseDetailVO.getRecorder()));
            List<PsychologicalTestRecord> recordList = psychologicalTestRecordMapper.getAllByStuNum(stuNum);
            psychologicalCounselingCaseDetailVO.setPsychologicalTestRecordList(recordList);
        }

        psychologicalCounselingCaseDetailVOPage.setRecords(PsychologicalCounselingCaseDetailVOList);
        return psychologicalCounselingCaseDetailVOPage;
    }

    /**
     * 筛选心理咨询记录，可通过学号、起止时间筛选
     * 用于excel表格导出
     */
    public List<PsychologicalCounselingCaseExportVO> getAllCounselingCaseByParams(PsychologicalCounsellingCaseSelectVO psychologicalCounsellingCaseSelectVO){

        String startTime = timeTransfer(psychologicalCounsellingCaseSelectVO.getStartTime());
        String endTime = timeTransfer(psychologicalCounsellingCaseSelectVO.getEndTime());
        //        需要返回的咨询记录详情Page VO
        List<PsychologicalCounselingCaseDetailVO> psychologicalCounselingCaseDetailVOS =
                baseMapper.getAllCounselingCaseByParams1(psychologicalCounsellingCaseSelectVO.getStuNum(),psychologicalCounsellingCaseSelectVO.getCounselor(),startTime,endTime);
        List<PsychologicalCounselingCaseExportVO> psychologicalCounselingCaseExportVOS = new ArrayList<>();

        for (PsychologicalCounselingCaseDetailVO psychologicalCounselingCaseDetailVO:psychologicalCounselingCaseDetailVOS){
//            System.out.println(psychologicalCounselingCaseDetailVO);
            PsychologicalCounselingCaseExportVO psychologicalCounselingCaseExportVO = new PsychologicalCounselingCaseExportVO();
            BeanUtils.copyProperties(psychologicalCounselingCaseDetailVO,psychologicalCounselingCaseExportVO);
            String stuNum = psychologicalCounselingCaseDetailVO.getStuNum();
            if (userService.getUserById(stuNum) == null) {
                continue;
            }
            StuInfo stuInfo = stuInfoService.getById(stuNum);

            ZoneId zone = ZoneId.systemDefault();
            Instant instant = psychologicalCounselingCaseDetailVO.getRecordedTime().atZone(zone).toInstant();
            psychologicalCounselingCaseExportVO.setRecordedTime(Date.from(instant));
            psychologicalCounselingCaseExportVO.setClasses(stuInfo.getClasses());
            psychologicalCounselingCaseExportVO.setDepartment(stuInfo.getDepartment());
            psychologicalCounselingCaseExportVO.setGrade(stuInfo.getGrade());
            psychologicalCounselingCaseExportVO.setGender(stuInfo.getGender());
            psychologicalCounselingCaseExportVO.setName(stuInfo.getName());
            psychologicalCounselingCaseExportVO.setPhone(stuInfo.getPhone());
            psychologicalCounselingCaseExportVO.setRecorder(getCoreAdminBasic(psychologicalCounselingCaseExportVO.getRecorder()).getName());
            // 将boolean转换为是否
            if (psychologicalCounselingCaseDetailVO.getIsFinished()) {
                psychologicalCounselingCaseExportVO.setIsFinished("是");
            }else {
                psychologicalCounselingCaseExportVO.setIsFinished("否");
            }
            if (psychologicalCounselingCaseDetailVO.getHasDiagnosis()) {
                psychologicalCounselingCaseExportVO.setHasDiagnosis("是");
            }else {
                psychologicalCounselingCaseExportVO.setHasDiagnosis("否");
            }
            if (psychologicalCounselingCaseDetailVO.getIsFirstTime()) {
                psychologicalCounselingCaseExportVO.setIsFirstTime("是");
            }else {
                psychologicalCounselingCaseExportVO.setIsFirstTime("否");
            }
            psychologicalCounselingCaseExportVOS.add(psychologicalCounselingCaseExportVO);
        }
//        System.out.println(PsychologicalCounselingCaseDetailVOList);
        return psychologicalCounselingCaseExportVOS;
    }

    /**
     * 获取某位同学所有咨询记录和测试记录  通过学生学号
     */
    public PsychologicalCounsellingCaseAndTestRecordGetAllVO getAll(String stuNum) {
        PsychologicalCounsellingCaseAndTestRecordGetAllVO psychologicalAllVO = new PsychologicalCounsellingCaseAndTestRecordGetAllVO();

        // 获取学生相关信息
        StuInfo stuInfo = stuInfoService.getById(stuNum);
        BeanUtils.copyProperties(stuInfo,psychologicalAllVO);

        // 通过学号获取咨询记录
        List<PsychologicalCounsellingCase> caseList = psychologicalCounsellingCaseMapper.getAllByStuNum(stuNum);

        // 通过学号获取测试记录
        List<PsychologicalTestRecord> recordList = psychologicalTestRecordMapper.getAllByStuNum(stuNum);

        psychologicalAllVO.setPsychologicalCounsellingCaseList(caseList);
        psychologicalAllVO.setPsychologicalTestRecordList(recordList);
        return psychologicalAllVO;
    }

    // 时间转换
    public String timeTransfer(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (d != null){
            return formatter.format(d);
        }
        return null;
    }

    /**
     * 根据admin工号返回工号和姓名
     */
    public CoreAdminBasicVO getCoreAdminBasic(String id){
//        CoreAdmin coreAdmin = coreAdminService.getById(id);
        UserInfo userInfo = userService.getUserById(id);
        CoreAdminBasicVO coreAdminBasicVO = new CoreAdminBasicVO();
        BeanUtils.copyProperties(userInfo,coreAdminBasicVO);
        return coreAdminBasicVO;
    }


}
