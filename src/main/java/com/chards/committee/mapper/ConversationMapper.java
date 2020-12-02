package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.Conversation;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.vo.ConversationPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * (Conversation)表数据库访问层
 *
 * @author chards
 * @since 2020-08-01 12:13:55
 */
public interface ConversationMapper extends BaseMapper<Conversation> {
    Page<ConversationPageVO> getPageByStuid(@Param("page") Page<ConversationPageVO> page, @Param("stuId") String stuId);

    Page<ConversationPageVO> getPageAll(@Param("page") Page<ConversationPageVO> page, @Param("param") AdminWorkDTO pageDTO);
}