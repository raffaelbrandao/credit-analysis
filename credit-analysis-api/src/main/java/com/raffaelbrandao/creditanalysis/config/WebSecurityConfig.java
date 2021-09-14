package com.raffaelbrandao.creditanalysis.config;

import com.raffaelbrandao.creditanalysis.service.security.CustomUserDetailsService;
import com.raffaelbrandao.creditanalysis.service.security.JwtService;
import com.raffaelbrandao.creditanalysis.service.security.filter.CustomAuthenticationFilter;
import com.raffaelbrandao.creditanalysis.service.security.filter.CustomUsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/api/proposals/*").hasAnyAuthority("ROLE_USER")
                .antMatchers("/api/proposals/*/status/*").hasAnyAuthority("ROLE_ANALYST")
                .anyRequest().authenticated().and()
                .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManagerBean(), jwtService))
                .addFilterBefore(new CustomAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class).formLogin().disable();
    }
}
