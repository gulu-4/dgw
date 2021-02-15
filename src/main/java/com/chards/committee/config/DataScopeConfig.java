package com.chards.committee.config;

import com.chards.committee.util.DataScopeInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;


@Configuration
//@EnableTransactionManagement
//@MapperScan("com.chards.committee.mapper")
public class DataScopeConfig implements InitializingBean {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Override
    public void afterPropertiesSet() throws Exception {
        DataScopeInterceptor dataScopeInterceptor = new DataScopeInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(dataScopeInterceptor);
        }
    }
}
