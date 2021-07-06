package com.chards.committee.vo.stayInSchoolDuringTheHolidays;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class StayInSchoolDuringTheHolidaysInsertVO {
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startDate;
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endDate;
    @NotNull(message = "电话号码不能为空")
    private String tel;
    @NotNull(message = "紧急联系人不能为空")
    private String emergencyCaller;
    @NotNull(message = "紧急联系人电话不能为空")
    private String emergencyTel;
    @NotNull(message = "家庭住址不能为空")
    private String address;
    @NotNull(message = "原因不能为空")
    private String reason;
}
