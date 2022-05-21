package com.ongames.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class LocadoraWebSecurity extends WebSecurityConfigurerAdapter{
    
    @Autowired
    FuncionarioDetailsService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/apirest/**")
                .and()
                .authorizeRequests()                
                    .antMatchers("/apirest/**").hasRole("ADMIN")
                    .antMatchers("/funcionarios/**").hasRole("ADMIN")
                    .antMatchers("/funcionarios/meusDados/**").hasAnyRole("ADMIN", "FUNC")
                    .antMatchers("/pagamentos/**").hasRole("ADMIN")                
                    .anyRequest().authenticated()               
                .and()
                .httpBasic()
                .and()
                .formLogin()                    
                    .usernameParameter("email")                                    
                .and()
                .logout().permitAll();                 
    }      
}
