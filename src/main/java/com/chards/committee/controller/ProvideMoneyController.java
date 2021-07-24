package com.chards.committee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.ProvideMoney;
import com.chards.committee.service.ProvideMoneyService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/7/24 17:59
 */
@RestController
@RequestMapping("/provideMoney")
@Api(tags = "奖助学金模块", value = "奖助学经相关接口")
public class ProvideMoneyController {
    @Autowired
    private ProvideMoneyService provideMoneyService;

    @ApiOperation(value = "学生提交奖助学金申请表")
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/add")
    public R add(@RequestBody @Valid ProvideMoney provideMoney){
        // 先判断 该期该学生该类型的申请表是否填过
        Long result = provideMoneyService.applyAdd(provideMoney);
        if (result == -1){
            return R.failure(Code.DATA_ALREADY_EXISTED);
        }else{
            return R.success(result);
        }
    }

    @ApiOperation(value = "根据id获取申请表详细信息")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('student_select')")
    @GetMapping("/getApplyById/{id}")
    public R getApplyById(@PathVariable Serializable id) {
        return R.success(provideMoneyService.getById(id));
    }

    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('student_select')")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Serializable id) {
        return R.success(provideMoneyService.removeById(id));
    }

    @ApiOperation(value = "该学生该期该类型申请表是否申请过")
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getHadApplied")
    public R getHadApplied(@RequestParam("type") String type,@RequestParam("stage") String stage){
        return R.success(provideMoneyService.getHadApplied(type,stage));
    }


    @ApiOperation(value = "学生更新奖助学金申请表")
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/update")
    public R studentUpdate(@Valid @RequestBody ProvideMoney provideMoney) {
        // 如果辅导员已经审核通过，则不可以再进行修改
        ProvideMoney provideMoney1 = provideMoneyService.getById(provideMoney.getId());
        if (provideMoney1 == null) {
            return R.failure(Code.RESULT_DATA_NONE);
        }else{
            if (provideMoney1.getFirstCheck() == 1) {
                return R.failure("已审核通过，无法更改");
            }
            provideMoney.setUpdateTime(LocalDateTime.now());
            return R.success(provideMoneyService.updateById(provideMoney));
        }
    }

    @ApiOperation("辅导员列表获取")
    @PreAuthorize("hasRole('FUDAOYUAN')")
    @GetMapping("/getFirstList")
    public R getFirstList(ProvideMoneyGetParamVO provideMoneyGetParamVO, Page<ProvideMoneyGetDetailVO> page) {
        return R.success(provideMoneyService.getFirstList(provideMoneyGetParamVO,page));
    }

    @ApiOperation("辅导员审核")
    @PreAuthorize("hasRole('FUDAOYUAN')")
    @PutMapping("/firstCheck")
    public R firstCheck(@Valid @RequestBody ProvideMoneyPassVO provideMoneyPassVO){
        provideMoneyPassVO.setFirstReviewer(RequestUtil.getLoginUser().getId() + "," + RequestUtil.getLoginUser().getName());
        provideMoneyPassVO.setFirstCheckTime(LocalDateTime.now());
        return R.success(provideMoneyService.check(provideMoneyPassVO));
    }

    @ApiOperation("学工处列表获取")
    @PreAuthorize("hasRole('XUEGONG')")
    @GetMapping("/getSecondList")
    public R getSecondList(ProvideMoneyGetParamVO provideMoneyGetParamVO, Page<ProvideMoneyGetDetailVO> page) {
        provideMoneyGetParamVO.setFirstCheck(1);
        return R.success(provideMoneyService.getSecondList(provideMoneyGetParamVO,page));
    }

    @ApiOperation("学工处审核")
    @PreAuthorize("hasRole('XUEGONG')")
    @PutMapping("/secondCheck")
    public R secondCheck(@Valid @RequestBody ProvideMoneyPassVO provideMoneyPassVO){
        if (provideMoneyPassVO.getFirstCheck() == 0){
            provideMoneyPassVO.setFirstCheck(1);
        }
        provideMoneyPassVO.setSecondReviewer(RequestUtil.getLoginUser().getId() + "," + RequestUtil.getLoginUser().getName());
        provideMoneyPassVO.setSecondReviewTime(LocalDateTime.now());
        return R.success(provideMoneyService.check(provideMoneyPassVO));
    }


}
