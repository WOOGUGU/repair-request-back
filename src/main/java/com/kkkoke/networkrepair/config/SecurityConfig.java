package com.kkkoke.networkrepair.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.result.ResultCode;
import com.kkkoke.networkrepair.service.Impl.UserDetailsServiceImpl;
import com.kkkoke.networkrepair.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final PropertiesUtil propertiesUtil;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, PropertiesUtil propertiesUtil) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.propertiesUtil = propertiesUtil;
    }

    //配置密码加密算法
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 自定义 DaoAuthenticationProvider 用以区分登录错误时的密码错误或用户名不存在
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    // 自定义 AuthenticationManager  这个会覆盖原厂中的 AuthenticationManager
    // 推荐使用这个
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServiceImpl);
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    @Bean
    // 将工厂本地创建的 AuthenticationManager 暴露出来，在工厂以外的地方也能够注入
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 自定义 Filter 交给工厂管理
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/doLogin"); // 指定认证url
        loginFilter.setUsernameParameter("uname"); // 指定接收 json 用户名 key
        loginFilter.setPasswordParameter("passwd"); // 指定接收 json 密码 key
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            String result = new ObjectMapper().writeValueAsString(ApiResult.success(authentication.getPrincipal(), "登录成功", ApiResult.LOGIN_SUCCESS));
            response.getWriter().println(result);
        })); // 认证成功处理
        loginFilter.setAuthenticationFailureHandler(((request, response, exception) -> {
            log.info("{}.errMsg:{}", exception, exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            if (exception.getMessage().equals("username has not existed")) {
                String result = new ObjectMapper().writeValueAsString(ApiResult.fail(ResultCode.USERNAME_INVALID,null, "用户名不存在，请重试", ApiResult.USER_WRONG));
                response.getWriter().println(result);
            } else {
                String result = new ObjectMapper().writeValueAsString(ApiResult.fail(ResultCode.PASSWORD_WRONG,null, "密码错误，请重试", ApiResult.PASSWORD_WRONG));
                response.getWriter().println(result);
            }
        })); // 认证失败处理
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
                        "/swagger-resources", "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/webjars/**", "/login.html", "/css/**", "/font/**",
                        "/fonts/**", "/images/**", "/js/**", "/lib/**", "/error/**", "/v2/slide/selectAllSlide",
                        "/v2/article/selectAllArticle", "v2/notice/selectAllNotice").permitAll() // 开放资源
                .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(((request, response, authException) -> {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String result = new ObjectMapper().writeValueAsString(ApiResult.fail(ResultCode.UNAUTHENTICATED,null, "请登录之后再访问该资源", ApiResult.UNAUTHENTICATED));
                response.getWriter().println(result);
            }))
            .and().rememberMe().tokenValiditySeconds(Integer.parseInt(propertiesUtil.getTokenExpiredTime())) // 设置token过期时间为7天
            .and()
            .logout()
//                .logoutUrl("/logout")
            .logoutRequestMatcher(new OrRequestMatcher(
                    new AntPathRequestMatcher("/doLogout", HttpMethod.DELETE.name()),
                    new AntPathRequestMatcher("/doLogout", HttpMethod.GET.name())
            ))
            .logoutSuccessHandler(((request, response, authentication) -> {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.OK.value());
                String result = new ObjectMapper().writeValueAsString(ApiResult.success(authentication.getPrincipal(), "注销成功", ApiResult.LOGOUT_SUCCESS));
                response.getWriter().println(result);
            }))
            .and()
            .csrf().disable();

        // 设置访问同源头
        http.headers().frameOptions().sameOrigin();

        // at: 用某个 filter 来替换过滤器链中哪个 filter
        // before: 放在过滤器链中哪个 filter 之前
        // after: 放在过滤器链中哪个 filter 之后
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
