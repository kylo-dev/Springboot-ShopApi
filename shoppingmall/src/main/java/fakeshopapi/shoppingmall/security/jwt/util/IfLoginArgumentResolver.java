package fakeshopapi.shoppingmall.security.jwt.util;


import fakeshopapi.shoppingmall.dto.LoginInfoDto;
import fakeshopapi.shoppingmall.dto.LoginUserDto;
import fakeshopapi.shoppingmall.security.jwt.token.JwtAuthenticationToken;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.Iterator;

// Controller에 들어가는 파라미터를 보고, 값을 조작해서 넣어줌 --> ArgumentResolver
public class IfLoginArgumentResolver implements HandlerMethodArgumentResolver {

    // Controller에 Parameter를 확인한후 변경이 필요하면 True 반환
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(IfLogin.class) != null
                && parameter.getParameterType() == LoginUserDto.class;
    }

    // supportsParameter() 함수에서 True가 반환되면 실행됨
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception ex) {
            return null;
        }
        if (authentication == null) {
            return null;
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)authentication;
        LoginUserDto loginUserDto = new LoginUserDto();

        Object principal = jwtAuthenticationToken.getPrincipal(); // LoginInfoDto
        if (principal == null)
            return null;

        LoginInfoDto loginInfoDto = (LoginInfoDto)principal;
        loginUserDto.setEmail(loginInfoDto.getEmail());
        loginUserDto.setMemberId(loginInfoDto.getMemberId());
        loginUserDto.setName(loginInfoDto.getName());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            String role = grantedAuthority.getAuthority();
//            System.out.println(role);
            loginUserDto.addRole(role);
        }

        return loginUserDto;
    }
}