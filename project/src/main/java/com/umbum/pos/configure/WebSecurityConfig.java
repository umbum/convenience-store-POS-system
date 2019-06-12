package com.umbum.pos.configure;

import java.security.AuthProvider;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/join").permitAll()
            .antMatchers("/admin/**", "/order-page", "/employee-manaement-page", "/income-statment-page").hasRole("ADMIN")
            .anyRequest().authenticated();

        http.csrf().disable();

        http.formLogin()
            .loginPage("/login")
            .permitAll()
            .usernameParameter("username")
            .passwordParameter("password")
            .failureUrl("/login?error")
            .defaultSuccessUrl("/");

        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .permitAll();

    }

    @Override
    public void configure(WebSecurity web) {
        // 인증 없이 무조건 통과시킬 경로
        web
            .ignoring()
            .antMatchers("/assets/**", "/custom/**");
    }
}
