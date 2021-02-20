package com.chards.committee.util;


import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.chards.committee.dto.UserDataScope;
import com.chards.committee.dto.UserTokenDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;


@Intercepts(
        {@Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )})
public class DataScopeInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(DataScopeInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        log.info("进入 PrepareInterceptor 拦截器 ");
        // 1.获取执行的RoutingStatementHandler
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());

        // 2.获取MetaObject
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 3.获取MappedStatement, 用于判定方法上的注解
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 4.MappedStatement的ID即为Mapper方法的全路径名
        String methodId = ms.getId();
//        log.info("mapper method: {}", methodId);

        // 5.获取Mapper的Class名称
        String clazzName = methodId.substring(0, methodId.lastIndexOf("."));
        // 6.获取拦截的方法名
        String methodName = methodId.substring(methodId.lastIndexOf(".") + 1);
//        log.info("clazzName: {}, methodName: {}", clazzName, methodName);
        // 7.反射获取方法上的注解内容
        Method[] methods = Class.forName(clazzName).getDeclaredMethods();
        DataScope dataScope = null;
        for (Method md : methods) {
            if (methodName.equalsIgnoreCase(md.getName())) {
                dataScope = md.getAnnotation(DataScope.class);
            }
        }
        if (dataScope == null) {
            return invocation.proceed();
        }
        String studentState = dataScope.studentState();
        String isDataAccessControlled = dataScope.isDataAccessControlled();
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object paramObject = boundSql.getParameterObject();
        System.out.println(paramObject.toString());
        String sql = boundSql.getSql();
//        sql = sql.replaceAll("\\n", "").replaceAll("\\t", "");


        if ((!StringUtils.isEmpty(studentState)) || isDataAccessControlled.equals("是")){
            sql = "SELECT * FROM (" + sql + ") origin WHERE ";
        }

        if (isDataAccessControlled.equals("是")){
            UserTokenDTO loginUserTokenDTO = RequestUtil.getLoginUserTokenDTO();
            List<UserDataScope> userDataScopeList = loginUserTokenDTO.getUserDataScopeList();

            if (sql.indexOf("department") == -1 && sql.indexOf("stu_info.*") == -1){
                log.error("【错误！】在使用DataScope进行数据权限控制时，必须将stu_info表的department，或stu_info.* 放到select中！");
                throw new Exception("在使用DataScope进行数据权限控制时，必须将stu_info表的department，或stu_info.* 放到select中！");
            }
            if (sql.indexOf("grade") == -1 && sql.indexOf("stu_info.*") == -1){
                log.error("【错误！】在使用DataScope进行数据权限控制时，必须将stu_info表的grade，或stu_info.* 放到select中！");
                throw new Exception("在使用DataScope进行数据权限控制时，必须将stu_info表的grade，或stu_info.* 放到select中！");
            }
            if (sql.indexOf("education_background") == -1 && sql.indexOf("stu_info.*") == -1){
                log.error("【错误！】在使用DataScope进行数据权限控制时，必须将stu_info表的education_background，或stu_info.* 放到select中！");
                throw new Exception("在使用DataScope进行数据权限控制时，必须将stu_info表的education_background，或stu_info.* 放到select中！");
            }


            for (int i = 0; i< userDataScopeList.size(); i++){
                UserDataScope userDataScope = userDataScopeList.get(i);
                String statement = "(origin.department like '%"+ (userDataScope.getDepartment()!=null?userDataScope.getDepartment():"") +"%' and origin.education_background like '%"+ (userDataScope.getEducationBackground()!=null?userDataScope.getEducationBackground():"") +"%' and origin.grade like '%" + (userDataScope.getGrade()!=null?userDataScope.getGrade():"") + "%')";
                if (i == 0){
                    sql += " ( ";
                }
                if (userDataScopeList.size() > 1){
                    sql += statement;
                    if (i < userDataScopeList.size()-1){
                        sql += " or ";
                    }
                }
                else {
                    sql += statement;
                }
                if (i == userDataScopeList.size()-1){
                    sql += " ) ";
                }
            }

        }
//        若要进行学生状态的控制
        if (!StringUtils.isEmpty(studentState)){
            if (sql.indexOf("state") == -1 && sql.indexOf("stu_info.*") == -1){
                log.error("【错误！】在使用DataScope进行学生状态控制时，必须将stu_info表的state，或stu_info.* 放到select中！");
                throw new Exception("在使用DataScope进行学生状态控制时，必须将stu_info表的state，或stu_info.* 放到select中！");
            }
//            若前边已经进行了数据权限控制
            if (isDataAccessControlled.equals("是")){
                sql += " and origin.state = '" + studentState + "'";
            }else {
                sql += "origin.state = '" + studentState + "'";
            }
        }

        System.out.println(sql);
        metaObject.setValue("delegate.boundSql.sql", sql);


        return invocation.proceed();
    }

}