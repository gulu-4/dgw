package com.chards.committee.controller;


import com.chards.committee.domain.FieldOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.service.FieldOrderService;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.FieldOrderGetParamVO;
import com.chards.committee.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('OWN_INFO_CRUD')")
    @PostMapping("/add")
    public R add(@RequestBody FieldOrder fieldOrder){
        // TODO 添加的时候需要判断该场地改时间段内是否已经被审核预约
        // TODO 判断时间区间是否正确，end > start， start.day - now().day, end-start <= 2小时

        String stuNumber = RequestUtil.getId();
        fieldOrder.setStuNumber(stuNumber);
        fieldOrder.setStatus(0);
        fieldOrder.setCreateTime(LocalDateTime.now());
        fieldOrder.setUpdateTime(LocalDateTime.now());
        return R.success(fieldOrderService.save(fieldOrder));
    }

    @ApiOperation("查看所有的场地预约记录，学生看自己的，管理员看所有的")
    @PreAuthorize("hasRole('STUDENT') or hasRole('FIELDMANAGER')")
    @GetMapping("/getList")
    public R getList(@RequestBody FieldOrderGetParamVO fieldOrderGetParamVO,Page<FieldOrder> page) {
        if (RequestUtil.getRoles().contains("STUDENT")){
            // 如果是学生就将学号放进去
            fieldOrderGetParamVO.setStuNumber(RequestUtil.getId());
        } else if (RequestUtil.getRoles().contains("FIELDMANAGER")){
            fieldOrderGetParamVO.setManager(RequestUtil.getId());
        }
        return R.success(fieldOrderService.getList(page,fieldOrderGetParamVO));
    }

    @ApiOperation("根据id查询某条场地预约记录")
    @PreAuthorize("hasRole('STUDENT') or hasRole('FIELDMANAGER')")
    @GetMapping("/getOrderById/{id}")
    public R getOrderById(@PathVariable Integer id) {
        FieldOrderGetParamVO fieldOrderGetParamVO = new FieldOrderGetParamVO();
        fieldOrderGetParamVO.setId(id);
        if (RequestUtil.getRoles().contains("STUDENT")){
            // 如果是学生就将学号放进去
            fieldOrderGetParamVO.setStuNumber(RequestUtil.getId());
        } else if (RequestUtil.getRoles().contains("FIELDMANAGER")){
            fieldOrderGetParamVO.setManager(RequestUtil.getId());
        }
        return R.success(fieldOrderService.getOrderById(fieldOrderGetParamVO));
    }

//    @ApiOperation("查询某场地某天可以进行预约的时间段")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "某个租借场地的id", required = true, dataType = "Integer"),
//            @ApiImplicitParam(name = "time", value = "需要查询的某天时间，前台传递字符串", required = true, dataType = "String")
//    })
//    @PreAuthorize("hasRole('STUDNET')")
//    @GetMapping("/getAlreadyOrderedTime")
//    public R getAlreadyOrderedTime(@RequestParam Integer id,@RequestParam String time){
//
//    }


}
