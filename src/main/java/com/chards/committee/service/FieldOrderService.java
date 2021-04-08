package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.FieldOrder;
import com.chards.committee.mapper.FieldOrderMapper;
import com.chards.committee.mapper.FieldRentMapper;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.FieldOrderGetParamVO;
import com.chards.committee.vo.FieldOrderGetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
@Service("fieldOrderService")
public class FieldOrderService extends ServiceImpl<FieldOrderMapper,FieldOrder> {

    @Autowired
    private FieldRentMapper fieldRentMapper;

    public Page<FieldOrderGetVO> getList(Page<FieldOrderGetVO> page, FieldOrderGetParamVO fieldOrderGetParamVO) {
        return baseMapper.getList(page,fieldOrderGetParamVO);
    }

    public FieldOrder getOrderById(FieldOrderGetParamVO fieldOrderGetParamVO) {
        return baseMapper.getOrderById(fieldOrderGetParamVO);
    }

    /**
     * 判断登录人是否是场地负责人
     * @param userId
     * @return
     */
    public Boolean getIsManager(String userId) {
        Integer result = fieldRentMapper.getIsManager(userId);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断登录人是否是某个场地负责人
     * @param userId
     * @return
     */
    public Boolean getIsRentManager(String userId,String rentId) {
        Integer result = fieldRentMapper.getIsRentManager(userId,rentId);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 登记预约信息
     * @param fieldOrder
     * @return
     */
    public Integer addFieldOrder(FieldOrder fieldOrder) {
        // 添加的时候需要判断该场地该时间段内是否已经被审核通过
        List<FieldOrder> fieldOrderList = getFieldOrderList(fieldOrder);
        int result = 0;
        if (fieldOrderList.size() > 0) {
            result = -1; // 该场地该时间段已经被人预约
        } else {
            // 判断时间区间是否正确，end > start， start.day > now().day, end-start <= 2小时
            if (fieldOrder.getEndTime().isBefore(fieldOrder.getStartTime())) {
                result = 0; // 开始时间应该小于结束时间
            } else{
                Long start = fieldOrder.getStartTime().toEpochSecond(ZoneOffset.of("+8"));
                Long end = fieldOrder.getEndTime().toEpochSecond(ZoneOffset.of("+8"));
                if (end - start > 7200) {
                    result = 0; //预约时间不能超过两个小时
                } else{
                    // fieldOrder.getStartTime().getDayOfMonth() <= LocalDateTime.now().getDayOfMonth()
                    if ( fieldOrder.getStartTime().isBefore(LocalDateTime.now()) ) {
                        result = 0; //预约时间应该是未来时间
                    } else{
                        String stuNumber = RequestUtil.getId();
                        fieldOrder.setStuNumber(stuNumber);
                        fieldOrder.setStatus(0);
                        fieldOrder.setCreateTime(LocalDateTime.now());
                        fieldOrder.setUpdateTime(LocalDateTime.now());
                        result = baseMapper.insert(fieldOrder);
                    }
                }
            }
        }
        return result;
    }

    private List<FieldOrder> getFieldOrderList(FieldOrder fieldOrder) {
        FieldOrderGetParamVO fieldOrderGetParamVO = new FieldOrderGetParamVO();
        fieldOrderGetParamVO.setRentId(fieldOrder.getRentId());
        fieldOrderGetParamVO.setStatus(1);
        fieldOrderGetParamVO.setStartTime(fieldOrder.getStartTime());
        fieldOrderGetParamVO.setEndTime(fieldOrder.getEndTime());
        List<FieldOrder> fieldOrderList =  baseMapper.getAlreadyPassedList(fieldOrderGetParamVO);
        return fieldOrderList;
    }

    /**
     * 查询某天的已经被预约的时间段
     * @param id
     * @param time
     * @return
     */
    public List<Map<String, Object>> getAlreadyOrderedTime(String id, String time) {
        List<Map<String, Object>> sectionList =  new ArrayList<Map<String,Object>>();

        DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        LocalDateTime startTime = LocalDateTime.parse(time, DATEFORMATTER);
        LocalDateTime endTime = startTime.plusDays(1);
        List<FieldOrder> fieldOrderList = baseMapper.getAlreadyOrderedTime(id,startTime,endTime);
//        if (fieldOrderList.size() > 0) {
//            startTime = fieldOrderList.get(0).getStartTime();
//            endTime = fieldOrderList.get(fieldOrderList.size() - 1).getEndTime();
//            section.put("startTime",startTime);
//            section.put("endTime",endTime);
//        }else {
//            section.put("startTime",0);
//            section.put("endTime",0);
//        }
        for(FieldOrder fieldOrder : fieldOrderList) {
            Map<String, Object> section = new HashMap<>();
            section.put("startTime",fieldOrder.getStartTime());
            section.put("endTime",fieldOrder.getEndTime());
            sectionList.add(section);
        }
        return sectionList;
    }

    public Integer checkOrder(FieldOrder fieldOrder) {
        if (fieldOrder.getStatus() == 1) {
            List<FieldOrder> fieldOrderList = getFieldOrderList(fieldOrder);
            if (fieldOrderList.size() > 0) {
                return -1;   // 说明该场地改时间段已经有被审核通过的预约记录
            }
        }
        return baseMapper.updateById(fieldOrder);
    }
}
