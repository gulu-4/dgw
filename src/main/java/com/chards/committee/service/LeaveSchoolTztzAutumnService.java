package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.LeaveSchoolTztzAutumn;
import com.chards.committee.dto.BackSchoolDateAreaDTO;
import com.chards.committee.dto.LeaveSchoolTztzAutumnAdminGetAndUpdateDTO;
import com.chards.committee.mapper.LeaveSchoolTztzAutumnMapper;
import com.chards.committee.vo.LeaveSchoolTztzAutumnGetALLVO;
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

    public Page<LeaveSchoolTztzAutumnGetALLVO> getAdminManagementStudentLeaveSchoolTztzAutumn(Page<LeaveSchoolTztzAutumnGetALLVO> page, LeaveSchoolTztzAutumnAdminGetAndUpdateDTO leaveSchoolTztzAutumnAdminGetAndUpdateDTO) {
        return baseMapper.getAdminManagementStudentLeaveSchoolTztzAutumn(page,leaveSchoolTztzAutumnAdminGetAndUpdateDTO);
    }

    public boolean updateAdminManagementStudentLeaveSchoolPass(LeaveSchoolTztzAutumnAdminGetAndUpdateDTO leaveSchoolTztzAutumnAdminGetAndUpdateDTO) {
        return baseMapper.updateAdminManagementStudentLeaveSchoolPass(leaveSchoolTztzAutumnAdminGetAndUpdateDTO);
    }

    public List<LeaveSchoolTztzAutumnGetALLVO> getAdminManagementStudentLeaveSchoolByDateArea(BackSchoolDateAreaDTO backSchoolDateAreaDTO) {
        return baseMapper.getAdminManagementStudentLeaveSchoolByDateArea(backSchoolDateAreaDTO);
    }
}
