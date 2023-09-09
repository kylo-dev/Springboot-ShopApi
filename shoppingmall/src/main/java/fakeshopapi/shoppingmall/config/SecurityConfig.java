package fakeshopapi.shoppingmall.config;

import fakeshopapi.shoppingmall.security.jwt.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerConfig authenticationManagerConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    // JWT 토큰 인증을 사용, HttpSession 사용 X
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() // 직접 id, password를 입력 받아서 --> JWT 토큰을 리턴
                .csrf().disable() // CSRF 공격을 막기 위한 방법.
                .cors() // CORS 다른 도메인에서의 요청을 허용
                .and()
                .apply(authenticationManagerConfig)
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Preflight 요청은 허용
                .mvcMatchers( "/members/signup", "/members/login", "/members/refreshToken").permitAll()
                .mvcMatchers(GET, "/categories/**", "/products/**").permitAll()
                .mvcMatchers(GET,"/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .mvcMatchers(POST,"/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .anyRequest().hasAnyRole("USER", "MANAGER", "ADMIN")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .build();
    }

    // 암호를 암호화하거나, 사용자가 입력한 암호가 기존 암호랑 일치하는지 검사할 때 이 Bean을 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
