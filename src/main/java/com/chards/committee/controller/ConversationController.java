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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "谈话信息管理")
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
    @ApiOperation(value = "【管】查询某位同学的所有数据")
    public R selectAllByStuid(Page<ConversationPageVO> page, @RequestParam("stuid") String stuId) {
        return stuInfoService.isWithinDataScope(stuId) ? R.success(conversationService.getPageByStuid(page, stuId)) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    @PreAuthorize("hasAuthority('student_select')")
    @GetMapping("/all")
    @ApiOperation(value = "查询管辖范围内的所有数据")
    public R selectAll(Page<ConversationPageVO> page) {
        return R.success(conversationService.getPageAll(page));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('student_select')")
    @ApiOperation(value = "通过主键查询单条数据")
    public R selectOne(@PathVariable Serializable id) {
        Conversation conversation = conversationService.getById(id);
        Assert.notNull(conversation, Code.RESULT_DATA_NONE);
        StuInfo stuInfo = stuInfoService.getById(conversation.getStuNum());
        return stuInfo == null || stuInfoService.isWithinDataScope(stuInfo) ? R.success(conversation) : R.failure(Code.PERMISSION_NO_ACCESS);
    }

    /**
     * 新增数据
     *
     * @param conversationInsertVO 请求内容
     * @return 新增结果
     */
    @PreAuthorize("hasAuthority('student_insert')")
    @PostMapping
    @ApiOperation(value = "新增谈话记录")
    public R insert(@RequestBody @Valid ConversationInsertVO conversationInsertVO) {
        if (stuInfoService.isWithinDataScope(conversationInsertVO.getStuNum())) {
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
    @ApiOperation(value = "【超】删除谈话记录")
    public R remove(@PathVariable String conversation) {
        return R.success(conversationService.removeById(conversation));
    }

}