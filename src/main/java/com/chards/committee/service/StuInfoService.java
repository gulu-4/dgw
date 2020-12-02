package com.chards.committee.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.config.BusinessException;
import com.chards.committee.constant.Constant;
import com.chards.committee.domain.CoreAdmin;
import com.chards.committee.domain.LoginIp;
import com.chards.committee.domain.ParentsInfo;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.dto.StuInfoPageDTO;
import com.chards.committee.dto.StuInfoSeniorDTO;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.dto.UserTokenDTO;
import com.chards.committee.mapper.StuInfoMapper;
import com.chards.committee.util.Assert;
import com.chards.committee.util.RequestUtil;
import com.chards.committee.vo.Code;
import com.chards.committee.vo.StuInfoPageVO;
import com.chards.committee.vo.StuInfoSeniorVO;
import com.chards.committee.vo.UserLoginRespVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GY
 * @since 2020-07-22
 */
@Service
@Slf4j
public class StuInfoService extends ServiceImpl<StuInfoMapper, StuInfo> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LoginIpService loginIpService;

    @Autowired
    private ParentsInfoService parentsInfoService;

    @Autowired
    CoreAdminService coreAdminService;

    @Autowired
    private RedisTemplate redisTemplate;


    public boolean updateStuPwd(String id, String oldpwd, String newpwd) {
        StuInfo stuInfo = getById(id);
        if (stuInfo == null) {
            BusinessException.error(Code.USER_LOGIN_ERROR);
        }
        if (StringUtils.isBlank(stuInfo.getPassword())) {
            log.info("userID: {}, 原密码未做修改", stuInfo.getId());
            StringBuilder sb = new StringBuilder();
            for (int i = 6; i < 14; i++) {
                Character c = stuInfo.getIdCard().charAt(i);
                sb.append(c);
            }
            log.info("user:{} 的初始密码是：{}", stuInfo.getId(), sb);
            if (!sb.toString().equals(oldpwd)) {
                BusinessException.error(Code.USER_LOGIN_ERROR);
            } else {
                stuInfo.setPassword(bCryptPasswordEncoder.encode(newpwd));
                log.info("user:{} 密码更新了", stuInfo.getId());
            }
        } else {
            //已经更新后的密码判断和oldpwd错误
            if (!bCryptPasswordEncoder.matches(oldpwd, stuInfo.getPassword())) {
                BusinessException.error(Code.USER_LOGIN_ERROR);
            } else {
                stuInfo.setPassword(bCryptPasswordEncoder.encode(newpwd));
                log.info("user:{} 密码更新了", stuInfo.getId());
            }
        }
        return updateById(stuInfo);
    }

    public UserLoginRespVO getUserToken(String username, String password, String ip) {
        StuInfo stuInfo = getById(username);
//        System.out.println(stuInfo);
        Assert.notNull(stuInfo, Code.USER_LOGIN_ERROR);
        if (StringUtils.isBlank(stuInfo.getPassword())) {
            log.info("user: {}, 密码未做修改", username);
            StringBuilder sb = new StringBuilder();
            for (int i = 6; i < 14; i++) {
                Character c = stuInfo.getIdCard().charAt(i);
                sb.append(c);
            }
            log.info("user:{} 的初始密码是：{}", username, sb);
            if (!sb.toString().equals(password)) {
                BusinessException.error(Code.USER_LOGIN_ERROR);
            }
        } else if (!bCryptPasswordEncoder.matches(password, stuInfo.getPassword())) {
            BusinessException.error(Code.USER_LOGIN_ERROR);
        }
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(stuInfo, userInfo);
        userTokenDTO.setUserInfo(userInfo);
        userTokenDTO.setRoles(Arrays.asList(Constant.STUDENT));
        userTokenDTO.setPermissionsList(Arrays.asList("OWN_INFO_CRUD"));
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, userTokenDTO, 24, TimeUnit.HOURS);
        UserLoginRespVO resp = new UserLoginRespVO();
        resp.setToken(token);
        resp.setName(stuInfo.getName());
        LoginIp byLastIp = loginIpService.getByUserId(username);
        resp.setOldIp(byLastIp != null ? byLastIp.getIp() : "空");
        if (loginIpService.addLoginIp(username, ip)) {
            resp.setNewIp(ip);
        }
        return resp;
    }

    @Transactional
    public boolean updateStuInfoAndStuParentsInfo(StuInfo stuInfo, ParentsInfo parentsInfo) {
        parentsInfo.setStuNum(stuInfo.getId());
        CoreAdmin coreAdmin = coreAdminService.getById(stuInfo.getCounsellorNum());
        Assert.notNull(coreAdmin,"该辅导员工号不存在");
        if (!isWork(coreAdmin,stuInfo,true))BusinessException.error("辅导员没有该学院年级的权限");
        stuInfo.setCounsellorName(coreAdmin.getName());
        stuInfo.setCounsellorPhone(coreAdmin.getPhone());
        return updateById(stuInfo) && parentsInfoService.updateById(parentsInfo);
    }


    public ParentsInfo getParentsInfo(String id) {
        return parentsInfoService.getById(id);
    }


    public Page<StuInfoPageVO> getLike(Page<StuInfoPageVO> page, StuInfoPageDTO stuInfoPageDTO) {
        stuInfoPageDTO.setParam("%" + stuInfoPageDTO.getParam() + "%");
        return baseMapper.getLike(page, stuInfoPageDTO);
    }

    public List<StuInfoPageVO> getLikeList(StuInfoPageDTO stuInfoPageDTO) {
        stuInfoPageDTO.setParam("%" + stuInfoPageDTO.getParam() + "%");
        return baseMapper.getLikeList(stuInfoPageDTO);
    }


    public Page<StuInfoPageVO> getSeniorSearch(Page<StuInfoPageVO> page, StuInfoSeniorVO stuInfoSeniorVO) {
        StuInfoSeniorDTO stuInfoSeniorDTO = new StuInfoSeniorDTO();
        BeanUtils.copyProperties(stuInfoSeniorVO, stuInfoSeniorDTO);
        stuInfoSeniorDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());

        stuInfoSeniorDTO.setDormitory(stuInfoSeniorDTO.getDormitory() + "%");
        return baseMapper.getSeniorSearch(page, stuInfoSeniorDTO);
    }

    public List<StuInfoPageVO> getSeniorSearchList(StuInfoSeniorVO stuInfoSeniorVO) {
        StuInfoSeniorDTO stuInfoSeniorDTO = new StuInfoSeniorDTO();
        BeanUtils.copyProperties(stuInfoSeniorVO, stuInfoSeniorDTO);
        stuInfoSeniorDTO.setAdminWorkDTO(RequestUtil.getAdminWorkDTO());
        stuInfoSeniorDTO.setDormitory(stuInfoSeniorDTO.getDormitory() + "%");
        return baseMapper.getSeniorSearchList(stuInfoSeniorDTO);
    }


    //比较年级的map
    private Map<String, BiFunction<CoreAdmin, StuInfo, Boolean>> gradeMap = new HashMap<>();

    {
        gradeMap.put(Constant.GRADE_ALL, ((coreAdmin, stuInfo) -> true));

        gradeMap.put(Constant.EDUCATIONBACKGROUND_GRADUATE, ((coreAdmin, stuInfo) ->
                stuInfo.getEducationBackground().equals(Constant.EDUCATIONBACKGROUND_GRADUATE)
        ));

    }


    private Map<String, BiFunction<CoreAdmin, StuInfo, Boolean>> departmentMap = new HashMap<>();

    {

        departmentMap.put(Constant.DEPARTMENT_STUDENT_WORKER, ((coreAdmin, stuInfo) -> true));

    }

    /**
     * 返回登陆者是否拥有对学生的权限
     *
     * @param
     * @param stuInfo 学生 （不能为空，为空自己负责）
     * @return
     */
    public boolean isWork(StuInfo stuInfo) {
        //如果是超级管理员 直接放行
        if (RequestUtil.isRoot()) return true;
        // 获取登录的管理员
        UserInfo loginUser = RequestUtil.getLoginUser();
        return isWork(loginUser, stuInfo);
    }

    /**
     * 判断学生是否存在和登录员对该学生有没有权限 (登录的情况下)
     * 如果学生不存在 抛异常
     *
     * @param stuid 学号id
     * @return
     */

    public boolean isContainsReturnIsWork(String stuid) {
        StuInfo stuInfo = getById(stuid);
        Assert.notNull(stuInfo, "该学生不存在");
        return isWork(stuInfo);
    }

    /**
     * 判断某一个管理员是否对某一个学生具有 (学院+工作年纪)权限  (可以在未登录的情况下使用)
     *
     * @param coreAdmin   管理员
     * @param stuInfo     学生信息
     * @param checkIsRoot 是否判断coreAdmin的角色是root
     */
    public boolean isWork(@NonNull CoreAdmin coreAdmin, @NonNull StuInfo stuInfo, boolean checkIsRoot) {
        if (checkIsRoot && coreAdminService.isRoot(coreAdmin)) return true;
        BiFunction<CoreAdmin, StuInfo, Boolean> departmentMapOrDefault = departmentMap.getOrDefault(coreAdmin.getDepartment(),
                ((coreAdmin1, stuInfo1) -> coreAdmin1.getDepartment().equals(stuInfo1.getDepartment())));
        // 如果学院不符合 直接不放行
        if (!departmentMapOrDefault.apply(coreAdmin, stuInfo)) return false;
        // 获取所有的工作年级
        List<String> list = Arrays.asList(coreAdmin.getWork().split(","));
        // 逐个比较 如果有一个通过就放行
        for (String work : list) {
            if (gradeMap.getOrDefault(work, (coreAdmin1, stuInfo1) ->
                    work.equals(stuInfo1.getGrade())
                            && stuInfo1.getEducationBackground().equals(Constant.EDUCATIONBACKGROUND_UNDERGRADUATE)
            ).apply(coreAdmin, stuInfo))
                return true;
        }
        return false;
    }

    /**
     * userInfo 因为是从登录信息获取来的 请首先使用 RequestUtils.isRoot判断是否root角色， 该方法默认是false
     *
     * @param userInfo
     * @param stuInfo
     * @return
     */
    public boolean isWork(UserInfo userInfo, StuInfo stuInfo) {
        CoreAdmin coreAdmin = new CoreAdmin();
        BeanUtils.copyProperties(userInfo, coreAdmin);
        return isWork(coreAdmin, stuInfo, false);
    }

}
