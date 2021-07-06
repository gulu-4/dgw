package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.mapper.StayInSchoolDuringTheHolidaysMapper;
import com.chards.committee.domain.StayInSchoolDuringTheHolidays;
import com.chards.committee.vo.LeaveInfoVO;
import com.chards.committee.vo.LeaveSchoolQueryParamVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysInfoVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysQueryParamVO;
import org.springframework.stereotype.Service;

@Service
public class StayInSchoolDuringTheHolidaysService extends ServiceImpl<StayInSchoolDuringTheHolidaysMapper, StayInSchoolDuringTheHolidays> {
    public Page<StayInSchoolDuringTheHolidaysInfoVO> adminSelectStayInSchoolDuringTheHolidays(Page<StayInSchoolDuringTheHolidaysInfoVO> page, StayInSchoolDuringTheHolidaysQueryParamVO stayInSchoolDuringTheHolidaysQueryParamVO) {
        return baseMapper.adminSelectStayInSchoolDuringTheHolidays(page, stayInSchoolDuringTheHolidaysQueryParamVO);
    }
    
}
