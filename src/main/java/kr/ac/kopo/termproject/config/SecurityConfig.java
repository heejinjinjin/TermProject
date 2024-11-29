package kr.ac.kopo.termproject.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //패스워드 암호화하는 클래스
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        String userName = "user2";
//        String password = "1234";
//        UserDetails user = User.builder()
//                .username(userName)
//                .password(passwordEncoder().encode(password))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) -> {
            // 정적 리소스 경로 허용 설정
            auth.requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**").permitAll();
            // 댓글 권한
            auth.requestMatchers("/replies/board/{bno}").permitAll();
            auth.requestMatchers("/replies/**").hasRole("USER");
            // 게시판 권한
            auth.requestMatchers("/board/list").permitAll();
            auth.requestMatchers("/board/read").permitAll();
            auth.requestMatchers("/board/modify").hasRole("USER");
            auth.requestMatchers("/board/register").hasRole("USER");
            // 공지사항 권한
            auth.requestMatchers("/notice/list").permitAll();
            auth.requestMatchers("/notice/read").permitAll();
            auth.requestMatchers("/notice/modify").hasRole("MANAGER");
            auth.requestMatchers("/notice/register").hasRole("MANAGER");
            // 메인페이지 권한
            auth.requestMatchers("/homepage/zoo").permitAll();
            auth.requestMatchers("/homepage/africa").permitAll();
            auth.requestMatchers("/homepage/na").permitAll();
            auth.requestMatchers("/homepage/sa").permitAll();
            auth.requestMatchers("/homepage/australia").permitAll();

        });

        httpSecurity.formLogin();
        httpSecurity.csrf().disable();
        httpSecurity.logout();

        return httpSecurity.build();
    }
}
