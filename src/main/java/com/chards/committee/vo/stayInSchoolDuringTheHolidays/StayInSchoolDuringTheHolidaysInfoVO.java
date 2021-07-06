package com.chards.committee.vo.stayInSchoolDuringTheHolidays;

import com.chards.committee.domain.StayInSchoolDuringTheHolidays;
import lombok.Data;


@Data
public class StayInSchoolDuringTheHolidaysInfoVO extends StayInSchoolDuringTheHolidays {
    private String name;
    private String department;
    private String classes;
    private String educationBackground;
    private String grade;
}
