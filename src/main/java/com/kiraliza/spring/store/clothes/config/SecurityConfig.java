package com.kiraliza.spring.store.clothes.config;

import com.kiraliza.spring.store.clothes.service.impl.MongoUserDetailsService;
import com.kiraliza.spring.store.clothes.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    MongoUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults()
    {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**", "/admin").hasAllRoles(Role.ADMIN.name(), Role.MANAGER.name())
                .requestMatchers("/**", "/login").permitAll()
                .anyRequest().authenticated()
            )
//            .formLogin(formLogin -> formLogin
//                .loginPage("/signin")
//                .permitAll()
//            )
            .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
    {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
        ;

        return authenticationManagerBuilder.build();
    }
}
