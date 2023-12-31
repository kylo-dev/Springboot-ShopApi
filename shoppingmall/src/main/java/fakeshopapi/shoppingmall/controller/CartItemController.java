package fakeshopapi.shoppingmall.controller;

import fakeshopapi.shoppingmall.domain.CartItem;
import fakeshopapi.shoppingmall.dto.AddCartItemDto;
import fakeshopapi.shoppingmall.dto.CartItemResponseDto;
import fakeshopapi.shoppingmall.dto.LoginUserDto;
import fakeshopapi.shoppingmall.security.jwt.util.IfLogin;
import fakeshopapi.shoppingmall.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cartItems")
@RestController
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public CartItemResponseDto addCartItem(@IfLogin LoginUserDto loginUserDto, @RequestBody AddCartItemDto addCartItemDto){

        // 같은 cart에 같은 product가 있으면 quantity를 더해줘야함
        if(cartItemService.isCartItemExist(loginUserDto.getMemberId(), addCartItemDto.getCartId(), addCartItemDto.getProductId())){
            CartItem cartItem = cartItemService.getCartItem(loginUserDto.getMemberId(), addCartItemDto.getCartId(), addCartItemDto.getProductId());

            cartItem.setQuantity(cartItem.getQuantity() + addCartItemDto.getQuantity());
            return cartItemService.updateCartItem(cartItem);
        }
        return cartItemService.addCartItem(addCartItemDto);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity deleteCartItem(@IfLogin LoginUserDto loginUserDto, @PathVariable Long cartItemId){

        if(!cartItemService.isCartItemExist(loginUserDto.getMemberId(), cartItemId)){
            return ResponseEntity.badRequest().build();
        }
        cartItemService.deleteCartItem(loginUserDto.getMemberId(), cartItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<CartItemResponseDto> getCartItems(@IfLogin LoginUserDto loginUserDto,
                                       @RequestParam(required = false) Long cartId) {
        if(cartId == null)
            return cartItemService.getCartItems(loginUserDto.getMemberId());
        return cartItemService.getCartItems(loginUserDto.getMemberId(), cartId);
    }
}
