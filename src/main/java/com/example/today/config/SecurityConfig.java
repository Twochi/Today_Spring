package com.example.today.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrfConfig) -> //
//                            csrfConfig.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //  CSRF 토큰을 쿠키에 저장하고, HTTP 요청에서 JavaScript에서 접근 가능하도록 설정합니다.
                            csrfConfig.disable()  // 이거는 CSRF 보호를 비활성화하는 메서드

                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                    frameOptionsConfig.disable() // X-Frame-Options 를 비활성화 하는 설저이지만 보안적인 이슈 발생할수 있음 지금은 이해안되는데 나중에 설정해봄
                        )
                )
                .authorizeHttpRequests((authorizeRequests) -> // HTTP 요청에 대한 권한 부여를 설정하는 메서드
                        authorizeRequests
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/" , "/auth/**").permitAll() // 경로에 대한 HTTP 요청을 허용하는 설정 login페이지와 루트 페이지에 대한 요청은 모든 사용자에게 허용
//                                .anyRequest().authenticated() // 나머지 모든 요청에 대해 사용자가 인증되었을 때에만 접근을 허용하는 설정
                                .anyRequest().permitAll() // 위에서 명시적으로 설정되지 않은 모든 요청에 대해 모든 사용자에게 허용
                );

        return http.build();
    }



}
