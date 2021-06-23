package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.config.BusinessException;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.LoginIp;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.domain.User;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.dto.UserTokenDTO;
import com.chards.committee.mapper.UserMapper;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.UserLoginRespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    CoreAdminService coreAdminService;
    @Autowired
    StuInfoService stuInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    LoginIpService loginIpService;

    public UserInfo getUserById(String userId){
        UserInfo userInfo = new UserInfo();
        CoreAdmin coreAdmin = coreAdminService.getById(userId);
        StuInfo stuInfo = stuInfoService.getById(userId);
        if (coreAdmin!=null){
            BeanUtils.copyProperties(coreAdmin, userInfo);
            return userInfo;
        } else if (stuInfo != null){
            BeanUtils.copyProperties(stuInfo,userInfo);
            return userInfo;
        }else {
            return null;
        }

    }

    /**
     * 判断token是否过期，过期则返回重新登录，未过期则延长过期时间并且返回登录成功后相同的内容
     * @param token
     * @return
     */
    public UserLoginRespVO tokenIsExpired(String token, String ip) {
        // 查询token是否过期，即是否存在userTokenDTO
        UserTokenDTO userTokenDTO = redisService.getStringValue(token, UserTokenDTO.class);
        if (userTokenDTO != null) {
            // 未过期
            UserLoginRespVO resp = new UserLoginRespVO();
            resp.setToken(token);
            resp.setName(userTokenDTO.getUserInfo().getName());
            resp.setRoleList(userTokenDTO.getRoles());
            resp.setUserDataScopeList(userTokenDTO.getUserDataScopeList());
            LoginIp byLastIp = loginIpService.getByUserId(userTokenDTO.getUserInfo().getId());
            resp.setOldIp(byLastIp != null ? byLastIp.getIp() : "空");
            if (loginIpService.addLoginIp(userTokenDTO.getUserInfo().getId(), ip)) {
                resp.setNewIp(ip);
            }
            // 延长token过期时间 这里设置延长一天时间，即24小时内再次访问即不需要重新登录
            String newToken = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(newToken, userTokenDTO, 24, TimeUnit.HOURS);
            resp.setToken(newToken);
            return resp;
        }else{
            // 过期
            BusinessException.error(Code.USER_LOGGED_IN_EXPIRED);
            UserLoginRespVO resp = null;
            return resp;
        }
    }
}
