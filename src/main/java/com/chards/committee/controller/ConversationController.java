package com.chards.committee.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.Conversation;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.service.ConversationService;
import com.chards.committee.service.StuInfoService;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.ConversationInsertVO;
import com.chards.committee.vo.ConversationPageVO;
import com.chards.committee.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Conversation)表控制层
 * <p>
 * 谈话表
 * </p>
 *
 * @author chards
 * @since 2020-08-01 12:13:56
 */
@RestController
@RequestMapping("/conversations")
public class ConversationController {
    /**
     * 服务对象
     */
    @Autowired
    ConversationService conversationService;

    @Autowired
    StuInfoService stuInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping
    public R selectAllByStuid(Page<ConversationPageVO> page, @RequestParam("stuid") String stuId) {
        return stuInfoService.isContainsReturnIsWork(stuId) ? R.success(conversationService.getPageByStuid(page, stuId)) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/all")
    public R selectAll(Page<ConversationPageVO> page) {
        return R.success(conversationService.getPageAll(page, RequestUtil.getAdminWorkDTO()));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('student_select')")
    public R selectOne(@PathVariable Serializable id) {
        Conversation conversation = conversationService.getById(id);
        Assert.notNull(conversation, Code.RESULT_DATA_NONE);
        StuInfo stuInfo = stuInfoService.getById(conversation.getStuNum());
        return stuInfo == null || stuInfoService.isWork(stuInfo) ? R.success(conversation) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 新增数据
     *
     * @param conversationInsertVO 请求内容
     * @return 新增结果
     */
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping
    public R insert(@RequestBody @Valid ConversationInsertVO conversationInsertVO) {
        if (stuInfoService.isContainsReturnIsWork(conversationInsertVO.getStuNum())) {
            Conversation conversation = new Conversation();
            BeanUtils.copyProperties(conversationInsertVO, conversation);
            conversation.setDatetime(LocalDateTime.now());
            conversation.setNumber(RequestUtil.getLoginUser().getId());
            return R.success(conversationService.save(conversation));
        }
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }




    /**
     * 删除谈话信息
     *
     * @param conversation {@code Long}
     * @return {@link R}
     */
    @PreAuthorize("hasRole('ROOT')")
    @PostMapping("remove/{conversation}")
    public R remove(@PathVariable String conversation) {
        return R.success(conversationService.removeById(conversation));
    }

}