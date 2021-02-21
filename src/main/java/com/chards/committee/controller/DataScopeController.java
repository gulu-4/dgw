package com.chards.committee.controller;

import com.chards.committee.domain.DataScope;
import com.chards.committee.service.DataScopeService;
import com.chards.committee.vo.DataScopeListAddOrUpdateVO;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author LiuSu
 * @create 2021/2/21 2:02
 */
@RestController
@RequestMapping("/dataScope")
@Api(tags = "管理员数据权限相关接口", value = "管理员数据权限接口")
public class DataScopeController {
    @Autowired
    private DataScopeService dataScopeService;

    /**
     * 通过用户id获取dataScopeList
     * @param userId
     * @return
     */
    @GetMapping("/getByUserId/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('student_select')")
    @ApiOperation(value = "通过用户id获取dataScopeList")
    public R getDataScopeByUserId(@PathVariable String userId) {
        return R.success(dataScopeService.getUserDataScope(userId));
    }


    /**
     * 根据用户id新增或者更新dataScopeList
     * @param dataScopeListAddOrUpdateVO
     * @return
     */
    @PostMapping("/")
    @PreAuthorize("hasRole('ROOT')")
    @ApiOperation(value = "根据用户id新增或者更新dataScopeList")
    public R addOrUpdateUserDataScopeList(@RequestBody DataScopeListAddOrUpdateVO dataScopeListAddOrUpdateVO) {
        return R.success(dataScopeService.addOrUpdateUserDataScopeList(dataScopeListAddOrUpdateVO));
    }
}
