package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.JobObtain;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.JobObtainGetInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author LiuSu
 * @create 2021/1/26 15:44
 */
public interface JobObtainMapper extends BaseMapper<JobObtain> {
    JobObtainGetInfoVO getInfoByStuNum(@Param("stuNum") String stuNum);

//    由于在就业模块中也出现了state字段，与拦截器的要求相冲突了，暂且放弃进行状态控制，2021.2.15 poplar
    @DataScope(studentState = "")
    Page<JobObtainGetInfoVO> getAdminManagementStudentJobObtain(@Param("page") Page<JobObtainGetInfoVO> page);
}
