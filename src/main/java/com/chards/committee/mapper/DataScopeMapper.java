package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.domain.DataScope;
import com.chards.committee.dto.UserDataScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DataScope)表数据库访问层
 *
 * @author poplar
 * @since 2021-02-10
 */
public interface DataScopeMapper extends BaseMapper<DataScope> {
    List<UserDataScope> getUserDataScopeById(@Param("userId") String userId);
    int deleteByUserId(@Param("userId") String userId);
//    Page<ConversationPageVO> getPageByStuid(@Param("page") Page<ConversationPageVO> page, @Param("stuId") String stuId);
//
//    Page<ConversationPageVO> getPageAll(@Param("page") Page<ConversationPageVO> page, @Param("param") AdminWorkDTO pageDTO);
}