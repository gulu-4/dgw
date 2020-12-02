package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.Conversation;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.mapper.ConversationMapper;
import com.chards.committee.vo.ConversationPageVO;
import org.springframework.stereotype.Service;

/**
 * (Conversation)表服务类
 *
 * @author chards
 * @since 2020-08-01 12:13:56
 */
@Service("conversationService")
public class ConversationService extends ServiceImpl<ConversationMapper, Conversation> {
    public Page<ConversationPageVO> getPageByStuid(Page<ConversationPageVO> page, String stuid) {
        return baseMapper.getPageByStuid(page, stuid);
    }

    public Page<ConversationPageVO> getPageAll(Page<ConversationPageVO> page, AdminWorkDTO pageDTO) {
        return baseMapper.getPageAll(page, pageDTO);
    }
}