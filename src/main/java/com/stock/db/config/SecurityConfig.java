package com.stock.db.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")        // 성공 시 redirect 할 URL
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
