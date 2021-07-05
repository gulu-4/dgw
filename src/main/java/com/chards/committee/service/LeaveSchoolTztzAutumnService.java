package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.LeaveSchoolTztzAutumn;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.LeaveSchoolTztzAutumnAdminGetAndUpdateDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.mapper.LeaveSchoolTztzAutumnMapper;
import com.chards.committee.vo.CoreAdminBasicVO;
import com.chards.committee.vo.LeaveSchoolTztzAutumnGetALLVO;
import com.chards.committee.vo.LeaveSchoolTztzQueryParamVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (LeaveSchoolTztzAutumnMapper)表服务类
 *
 * @author LiuSu
 * @create 2021/1/4 17:14
 */
@Service("leaveSchoolTztzAutumnService")
public class LeaveSchoolTztzAutumnService extends ServiceImpl<LeaveSchoolTztzAutumnMapper, LeaveSchoolTztzAutumn> {
    @Autowired
    private CoreAdminService coreAdminService;

    @Autowired
    UserService userService;

    public Page<LeaveSchoolTztzAutumnGetALLVO> getAdminManagementStudentLeaveSchoolTztzAutumn(Page<LeaveSchoolTztzAutumnGetALLVO> page, LeaveSchoolTztzQueryParamVO leaveSchoolTztzQueryParamVO) {
        Page<LeaveSchoolTztzAutumnGetALLVO> leaveSchoolTztzAutumnGetALLVOPage = baseMapper.getAdminManagementStudentLeaveSchoolTztzAutumn(page,leaveSchoolTztzQueryParamVO);
        List<LeaveSchoolTztzAutumnGetALLVO> leaveSchoolTztzAutumnGetALLVOList = leaveSchoolTztzAutumnGetALLVOPage.getRecords();
        for (LeaveSchoolTztzAutumnGetALLVO leaveSchoolTztzAutumnGetALLVO : leaveSchoolTztzAutumnGetALLVOList){
            if ((leaveSchoolTztzAutumnGetALLVO.getReviewedBy()!=null) && (leaveSchoolTztzAutumnGetALLVO.getReviewedBy().length()>0)){
                leaveSchoolTztzAutumnGetALLVO.setReviewedByName(getCoreAdminName(leaveSchoolTztzAutumnGetALLVO.getReviewedBy()));
            }
        }
        return leaveSchoolTztzAutumnGetALLVOPage;
    }

    public boolean updateAdminManagementStudentLeaveSchoolPass(LeaveSchoolTztzAutumnAdminGetAndUpdateDTO leaveSchoolTztzAutumnAdminGetAndUpdateDTO) {
        return baseMapper.updateAdminManagementStudentLeaveSchoolPass(leaveSchoolTztzAutumnAdminGetAndUpdateDTO);
    }

    public List<LeaveSchoolTztzAutumnGetALLVO> getAdminManagementStudentLeaveSchoolByDateArea(BackSchoolDateAreaDTO backSchoolDateAreaDTO) {
        return baseMapper.getAdminManagementStudentLeaveSchoolByDateArea(backSchoolDateAreaDTO);
    }

    /**
     * 根据admin工号返回姓名
     */
    public String getCoreAdminName(String id){
        UserInfo userInfo = userService.getUserById(id);
        return userInfo.getName();
    }
}
