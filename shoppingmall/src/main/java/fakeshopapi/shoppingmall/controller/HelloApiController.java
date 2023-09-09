package fakeshopapi.shoppingmall.controller;

import fakeshopapi.shoppingmall.security.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloApiController {

    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/hello")
    public String hello(@RequestHeader("Authorization") String token) {
        Long userIdFromToken = jwtTokenizer.getMemberIdFromToken(token);
        return "hello " + userIdFromToken;
    }
}
