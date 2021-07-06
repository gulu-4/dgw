package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.StayInSchoolDuringTheHolidays;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.LeaveInfoVO;
import com.chards.committee.vo.LeaveSchoolQueryParamVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysInfoVO;
import com.chards.committee.vo.stayInSchoolDuringTheHolidays.StayInSchoolDuringTheHolidaysQueryParamVO;


public interface StayInSchoolDuringTheHolidaysMapper extends BaseMapper<StayInSchoolDuringTheHolidays> {

    @DataScope()
    Page<StayInSchoolDuringTheHolidaysInfoVO> adminSelectStayInSchoolDuringTheHolidays(Page<StayInSchoolDuringTheHolidaysInfoVO> page, StayInSchoolDuringTheHolidaysQueryParamVO stayInSchoolDuringTheHolidaysQueryParamVO);
}
