package fakeshopapi.shoppingmall.controller;

import fakeshopapi.shoppingmall.domain.Cart;
import fakeshopapi.shoppingmall.domain.Member;
import fakeshopapi.shoppingmall.dto.LoginUserDto;
import fakeshopapi.shoppingmall.security.jwt.util.IfLogin;
import fakeshopapi.shoppingmall.service.CartService;
import fakeshopapi.shoppingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;
    private final MemberService memberService;

    @PostMapping
    public Cart addCart(@IfLogin LoginUserDto loginUserDto) {
        Optional<Member> member = memberService.getMember(loginUserDto.getEmail());
        if(member.isEmpty()) {
            throw new IllegalArgumentException("해당 사용자가 없습니다.");
        }

        LocalDate localDate = LocalDate.now();
        localDate.getYear();
        localDate.getDayOfMonth();
        localDate.getMonthValue();

        String date = String.valueOf(localDate.getYear()) + (localDate.getMonthValue() < 10 ? "0" :"") + String.valueOf(localDate.getMonthValue())
                                                    + (localDate.getDayOfMonth() < 10 ? "0" :"") +String.valueOf(localDate.getDayOfMonth());
        Cart cart = cartService.addCart(member.get().getMemberId(), date);
        return cart;
    }
}
