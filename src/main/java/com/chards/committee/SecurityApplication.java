package com.chards.committee;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("com.chards.committee.mapper")
public class SecurityApplication {


    /**
     * 修改加密方式
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        //默认加密方式
        return  new BCryptPasswordEncoder();
    }


     /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 开启 count 的 join 优化,只针对 left join !!!
        PaginationInterceptor page = new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
//        page.setDialectType("mysql");
        return page;
    }


    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
