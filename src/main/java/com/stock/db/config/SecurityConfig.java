package com.stock.db.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
    @brief      : 스프링 시큐리티 관련 configuration class
    @details    : 페이지 별 권한 검사, 로그인 처리, passwordEncoder Bean 등록
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    /*
        @brief      : passwordEncoder 를 bean 으로 등록
        @details    : 비밀번호를 encoding 할 경우,
                      항상 동일한 encoder 를 사용해야 하므로 bean 으로 등록하여 사용
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
        @brief : 로그인 관련 처리 및 페이지 별 접근 권한 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .failureUrl("/members/login/error")
                .usernameParameter("id")
                .passwordParameter("pwd")
        ;


        http.authorizeRequests()
                .mvcMatchers("/members/mypage").authenticated()
                .mvcMatchers("/", "/members/**").permitAll()
                .anyRequest().authenticated()
        ;

        return http.build();
    }
}
