package fakeshopapi.shoppingmall.config;

import fakeshopapi.shoppingmall.security.jwt.util.IfLoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// Spring MVC 에대한 설정파일
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS는 웹 브라우저에서 실행되는 JavaScript 코드가 도메인 간 요청을 보낼 때 발생하는 보안 문제를 관리하는 메커니즘입니다.
    // 프론트는 3000 port | 백엔드는 8080 port
    // http://localhost:3000 ---> 8080 api를 호출할 수 있도록 설정

    // 다른 도메인에서 웹 리소스에 대한 요청을 할 때 보안 문제가 발생하지 않도록 허용되는 도메인 및 HTTP 메서드를 지정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST","PATCH", "PUT", "OPTIONS", "DELETE");
//                .allowCredentials(true);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new IfLoginArgumentResolver());
    }
}
