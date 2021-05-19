package com.chards.committee.controller;


import com.chards.committee.domain.FieldOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.FieldRent;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.FieldOrderService;
import com.chards.committee.service.FieldRentService;
import com.chards.committee.util.HuaweiSmsUtil;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.FieldOrderGetParamVO;
import com.chards.committee.vo.FieldOrderGetVO;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
@RestController
@RequestMapping("/field-order")
@Api(tags = "场地预约",value = "场地预约接口")
public class FieldOrderController {
    @Autowired
    private FieldOrderService fieldOrderService;
    @Autowired
    private FieldRentService fieldRentService;
    @Autowired
    private HuaweiSmsUtil huaweiSmsUtil;

    @ApiOperation("学生添加场地预约")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @PostMapping("/add")
    public R add(@RequestBody FieldOrder fieldOrder){
        Integer result = fieldOrderService.addFieldOrder(fieldOrder);
        if (result == -1) {
            return R.failure("该场地该时间段已经被人预约");
        } else if (result == 0) {
            return R.failure(Code.PARAM_IS_INVALID);
        }
        return R.success("预约信息登记成功");
    }

    @ApiOperation("查看所有的场地预约记录，管理员看所管所有的")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @PostMapping("/getList")
    public R getList(@RequestBody FieldOrderGetParamVO fieldOrderGetParamVO,Page<FieldOrderGetVO> page) {
        fieldOrderGetParamVO.setManager(RequestUtil.getId());
        if (RequestUtil.getRoles().contains("ROOT")) {
            fieldOrderGetParamVO.setManager(null);
        }
        return R.success(fieldOrderService.getList(page,fieldOrderGetParamVO));
    }

    @ApiOperation("调用接口，查看某个登录人是否是场地负责人，不是的话返回false，是的话返回true")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @GetMapping("/getIsManager")
    public R getIsManager() {
        String userId = RequestUtil.getId();
        if (RequestUtil.getRoles().contains("ROOT")) {
            return R.success(true);
        }
        Boolean flag = fieldOrderService.getIsManager(userId);
        if (flag) {
            return R.success(true);
        }else {
            return R.failure("抱歉，您不是该场地负责人，没有权限!");
        }
    }

    //    下个版本废弃，改用 getOrderById/{id} （10.6.12）
    @ApiOperation("查看所有的场地预约记录，学生看自己的")
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/getStudentList")
    public R getStudentList(@RequestBody FieldOrderGetParamVO fieldOrderGetParamVO,Page<FieldOrderGetVO> page) {
        fieldOrderGetParamVO.setStuNumber(RequestUtil.getId());
        return R.success(fieldOrderService.getList(page,fieldOrderGetParamVO));
    }


    @ApiOperation("查看所有的场地预约记录，教师和学生都可用")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @PostMapping("/getRecordList")
    public R getRecordList(@RequestBody FieldOrderGetParamVO fieldOrderGetParamVO,Page<FieldOrderGetVO> page) {
        fieldOrderGetParamVO.setStuNumber(RequestUtil.getId());
        return R.success(fieldOrderService.getList(page,fieldOrderGetParamVO));
    }


    @ApiOperation("根据id查询某条场地预约记录")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @GetMapping("/getOrderById/{id}")
    public R getOrderById(@PathVariable String id) {
        FieldOrderGetParamVO fieldOrderGetParamVO = new FieldOrderGetParamVO();
        fieldOrderGetParamVO.setId(Long.valueOf(id).longValue());
        if (RequestUtil.getRoles().contains("STUDENT")){
            // 如果是学生就将学号放进去
            fieldOrderGetParamVO.setStuNumber(RequestUtil.getId());
        } else if (RequestUtil.getRoles().contains("FIELDMANAGER")){
            fieldOrderGetParamVO.setManager(RequestUtil.getId());
        }
        return R.success(fieldOrderService.getOrderById(fieldOrderGetParamVO));
    }

    @ApiOperation("查询某场地某天可以进行预约的时间段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "某个租借场地的id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "time", value = "需要查询的某天时间,具体到某天即可", required = true, dataType = "String")
    })
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @GetMapping("/getAlreadyOrderedTime")
    public R getAlreadyOrderedTime(@RequestParam String id,
                                   @RequestParam String time){
        return R.success(fieldOrderService.getAlreadyOrderedTime(id,time));
    }

    @ApiOperation(value = "管理员审核自己所管场地的预约信息")
    @PreAuthorize("hasRole('STUDENT') or hasAuthority('teacher_own')")
    @PutMapping("/update")
    public R update(@RequestBody FieldOrder fieldOrder) {
        // 判断审核人是否是manager中的或者是场地管理员
        Boolean flag = fieldOrderService.getIsRentManager(RequestUtil.getId(), String.valueOf(fieldOrder.getRentId()));
        if (RequestUtil.getRoles().contains("FIELDMANAGER")) {
            flag = true;
        }
        if (!flag) {
            return R.failure(Code.PERMISSION_NO_ACCESS);
        }
        if (fieldOrder.getStatus() == null || fieldOrder.getStatus() == 0) {
            fieldOrder.setStatus(1);
        }
        if (fieldOrder.getStatus() == -1 && fieldOrder.getCheckRemark() == null) {
            fieldOrder.setCheckRemark("抱歉，预约未通过，请尝试联系管理员或预约其他场地或时间段！");
        }
        fieldOrder.setUpdateTime(LocalDateTime.now());
        fieldOrder.setChecker(RequestUtil.getId());
        Integer result = fieldOrderService.checkOrder(fieldOrder);
        if (result == -1) {
            return R.failure("该场地该时间段已经有被审核通过的预约记录");
        } else {
            // 手机号码 phone 预约地点 localtion 预约时间 time
            FieldOrder fieldOrder1 = fieldOrderService.getById(fieldOrder.getId());
            String phone = fieldOrder1.getPhone();
            FieldRent fieldRent = fieldRentService.getById(fieldOrder1.getRentId());
            String location = fieldRent.getName();
            LocalDateTime startTime = fieldOrder1.getStartTime();
            String time = huaweiSmsUtil.transformLocalTime(startTime);
            try {
                huaweiSmsUtil.sendSms(phone,location,time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return R.success("审核完成");
        }
    }


}
