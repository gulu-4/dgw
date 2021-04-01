package com.chards.committee.controller;


import com.chards.committee.domain.FieldOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.FieldOrderService;
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

    @ApiOperation("学生添加场地预约")
    @PreAuthorize("hasAuthority('OWN_INFO_CRUD')")
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
    @PreAuthorize("hasRole('FIELDMANAGER')")
    @PostMapping("/getList")
    public R getList(@RequestBody FieldOrderGetParamVO fieldOrderGetParamVO,Page<FieldOrderGetVO> page) {
        fieldOrderGetParamVO.setManager(RequestUtil.getId());
        if (RequestUtil.getRoles().contains("ROOT")) {
            fieldOrderGetParamVO.setManager(null);
        }
        return R.success(fieldOrderService.getList(page,fieldOrderGetParamVO));
    }

    @ApiOperation("查看所有的场地预约记录，学生看自己的")
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/getStudentList")
    public R getStudentList(@RequestBody FieldOrderGetParamVO fieldOrderGetParamVO,Page<FieldOrderGetVO> page) {
        fieldOrderGetParamVO.setStuNumber(RequestUtil.getId());
        return R.success(fieldOrderService.getList(page,fieldOrderGetParamVO));
    }

    @ApiOperation("根据id查询某条场地预约记录")
    @PreAuthorize("hasRole('STUDENT') or hasRole('FIELDMANAGER')")
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
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getAlreadyOrderedTime")
    public R getAlreadyOrderedTime(@RequestParam String id,
                                   @RequestParam String time){
        return R.success(fieldOrderService.getAlreadyOrderedTime(id,time));
    }

    @ApiOperation(value = "管理员审核自己所管场地的预约信息")
    @PreAuthorize("hasRole('FIELDMANAGER')")
    @PutMapping("/update")
    public R update(@RequestBody FieldOrder fieldOrder) {
        // 这里没有进行审核人与manager的匹配，因为默认前面获取列表的时候做过
        if (fieldOrder.getStatus() == null) {
            fieldOrder.setStatus(1);
        }
        fieldOrder.setUpdateTime(LocalDateTime.now());
        fieldOrder.setChecker(RequestUtil.getId());
        Integer result = fieldOrderService.checkOrder(fieldOrder);
        if (result == -1) {
            return R.failure("该场地该时间段已经有被审核通过的预约记录");
        } else{
            return R.success("审核完成");
        }
    }


}
