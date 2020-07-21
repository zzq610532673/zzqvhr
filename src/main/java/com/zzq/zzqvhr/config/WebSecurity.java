package com.zzq.zzqvhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzq.zzqvhr.filter.VerifycodeFilter;
import com.zzq.zzqvhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
    @Autowired
    private VerifycodeFilter verifycodeFilter;
    @Bean
    public HrService hrService(){
        return new HrService();
    }
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService()).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**","/verifyCode");
    }

    /**
     * 角色继承（上级自动具有下级 的权限）
     * @param
     * @throws Exception
     */
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verifycodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/hr/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and() //回到authorizeRequests()
                .formLogin()
                .loginPage("/login.html")//配置表单登录的页面
                /*.loginProcessingUrl("/dologin")//配置登录接口，不配的时候默认是登录页面
                .successForwardUrl("/s")//服务端跳转
                .defaultSuccessUrl("/hello")//重定向
                .failureForwardUrl("/error")*/
                .successHandler((request,response,authentication)->{
                    Object principal = authentication.getPrincipal();
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((request,response,exception)->{
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(exception.getMessage()));
                    out.flush();
                    out.close();
                })
                .permitAll()//与登录相关的页面统统放行
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request,response,exception)->{
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("注销成功yes");
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable();
                /*.exceptionHandling()
                .authenticationEntryPoint((request,response,exception)->{
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("尚未登陆，请登录"));
                    out.flush();
                    out.close();
                });*/
    }
}
