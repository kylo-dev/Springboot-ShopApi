package fakeshopapi.shoppingmall.config;

import fakeshopapi.shoppingmall.security.jwt.filter.JwtAuthenticationFilter;
import fakeshopapi.shoppingmall.security.jwt.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig extends AbstractHttpConfigurer<AuthenticationManagerConfig, HttpSecurity> {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // Spring Security 구성을 설정하는 역할
    @Override
    public void configure(HttpSecurity builder) throws Exception {

        // Spring Security에서 사용자 인증을 관리하는 중요한 컴포넌트 가져오기
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        // JWT 인증 필터를 HTTP 요청을 처리하는 필터 체인의 일부로 등록
        builder.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(jwtAuthenticationProvider);
    }
}
