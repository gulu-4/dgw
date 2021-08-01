package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.StuInfoPageDTO;
import com.chards.committee.dto.StuInfoSeniorDTO;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.StuInfoBasicVO;
import com.chards.committee.vo.StuInfoPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
public interface StuInfoMapper extends BaseMapper<StuInfo> {
    @DataScope()
    Page<StuInfoPageVO> getLike(@Param("page") Page<StuInfoPageVO> page, @Param("param") StuInfoPageDTO param);

    Page<StuInfoPageVO> getLikeForPsychologicalCounselor(@Param("page") Page<StuInfoPageVO> page, @Param("param") StuInfoPageDTO param);

//    导出用，不分页的
    @DataScope()
    List<StuInfoPageVO> getLikeList(@Param("param") StuInfoPageDTO param);

    @DataScope()
    Page<StuInfoPageVO> getSeniorSearch(@Param("page") Page<StuInfoPageVO> page, @Param("param") StuInfoSeniorDTO stuInfoSeniorDTO);

    @DataScope()
    List<StuInfoPageVO> getSeniorSearchList(@Param("param") StuInfoSeniorDTO stuInfoSeniorDTO);


    @DataScope()
    List<StuInfoPageVO> getStudentsByDorStuNumber(@Param("stuNumber") String id);

    @DataScope()
    List<StuInfoBasicVO> getBasicStuInfoList(@Param("param") StuInfoSeniorDTO stuInfoSeniorDTO);
}
