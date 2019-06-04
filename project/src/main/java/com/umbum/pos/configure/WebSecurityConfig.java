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
    private final AuthenticationSuccessHandler authSuccessHandler;
    private final AuthenticationFailureHandler authFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public WebSecurityConfig(AuthenticationFailureHandler authFailureHandler,
        AuthenticationSuccessHandler authSuccessHandler) {
        this.authFailureHandler = authFailureHandler;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/join").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN") // 내부적으로 접두어 "ROLE_"가 붙는다.
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
