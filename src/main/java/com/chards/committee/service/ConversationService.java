package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.Conversation;
import com.chards.committee.dto.AdminWorkDTO;
import com.chards.committee.mapper.ConversationMapper;
import com.chards.committee.vo.ConversationPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Conversation)表服务类
 *
 * @author chards
 * @since 2020-08-01 12:13:56
 */
@Service("conversationService")
public class ConversationService extends ServiceImpl<ConversationMapper, Conversation> {
    @Autowired
    UserService userService;
    public Page<ConversationPageVO> getPageByStuid(Page<ConversationPageVO> page, String stuid) {
        List <ConversationPageVO> conversationPageVOList = baseMapper.getPageByStuid(page, stuid).getRecords();
        for(ConversationPageVO conversationPageVO:conversationPageVOList){
            conversationPageVO.setAdminName(userService.getUserById(conversationPageVO.getNumber()).getName());
            conversationPageVO.setStuEducationBackground(conversationPageVO.getEducationBackground());
            conversationPageVO.setStuGrade(conversationPageVO.getGrade());
        }
        page.setRecords(conversationPageVOList);
        return page;
    }


    public Page<ConversationPageVO> getPageAll(Page<ConversationPageVO> page) {
        List <ConversationPageVO> conversationPageVOList = baseMapper.getPageAll(page).getRecords();
        for(ConversationPageVO conversationPageVO:conversationPageVOList){
            conversationPageVO.setAdminName(userService.getUserById(conversationPageVO.getNumber()).getName());
            conversationPageVO.setStuEducationBackground(conversationPageVO.getEducationBackground());
            conversationPageVO.setStuGrade(conversationPageVO.getGrade());
        }
        page.setRecords(conversationPageVOList);
        return page;
    }
}