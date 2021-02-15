package com.chards.committee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.*;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HttpSecurityFilter httpSecurityFilter;
    @Autowired
    ApplicationContext applicationContext;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Map<HttpMethod, List<String>> permitAll = getPermitAll(applicationContext);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry =
                http
                        .cors(Customizer.withDefaults())
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
                        authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
        permitAll.forEach((httpMethod, strings) -> {
            expressionInterceptUrlRegistry.antMatchers(httpMethod, strings.toArray(new String[strings.size()])).permitAll();
        });
        expressionInterceptUrlRegistry.anyRequest().authenticated()
                .and()
                .exceptionHandling()
                // 权限不足处理   (如果全局异常处理有处理该异常AccessDeniedException的方法，会先走处理器)
//                .accessDeniedHandler(accessDeniedHandler())
                // 认证异常处理
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(httpSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new SecurityAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new SecurityAccessDeniedHandler();
    }


    public Map<HttpMethod, List<String>> getPermitAll(ApplicationContext applicationContext) {
        Map<HttpMethod, List<String>> httpMethodListMap = new HashMap<>();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            if (!handlerMethod.hasMethodAnnotation(PreAuthorize.class)) {
                Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
                methods.forEach(requestMethod -> {
                    HttpMethod httpMethod = HttpMethod.valueOf(requestMethod.name());
                    List<String> list = httpMethodListMap.getOrDefault(httpMethod, new ArrayList<>());
                    Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
                    patterns.forEach(url ->
                            {
                                url = url.replaceAll("\\{.*\\}", "*");
                                list.add(url);
                            }
                    );
                    if (list.size() == patterns.size()) httpMethodListMap.put(httpMethod, list);
                });
            }
        });
        return httpMethodListMap;
    }

    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**");
    }

}
