package fakeshopapi.shoppingmall.service;

import fakeshopapi.shoppingmall.domain.Cart;
import fakeshopapi.shoppingmall.domain.CartItem;
import fakeshopapi.shoppingmall.dto.AddCartItemDto;
import fakeshopapi.shoppingmall.repository.CartItemRepository;
import fakeshopapi.shoppingmall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CartItem addCartItem(AddCartItemDto addCartItemDto) {

        Cart cart = cartRepository.findById(addCartItemDto.getCartId()).orElseThrow();
        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setQuantity(addCartItemDto.getQuantity());
        cartItem.setProductId(addCartItemDto.getProductId());
        cartItem.setProductPrice(addCartItemDto.getProductPrice());
        cartItem.setProductTitle(addCartItemDto.getProductTitle());
        cartItem.setProductDescription(addCartItemDto.getProductDescription());

        return cartItemRepository.save(cartItem);
    }

    public boolean isCartItemExist(Long memberId, Long cartId, Long productId) {
        boolean check = cartItemRepository.existsByCart_memberIdAndCart_idAndProductId(memberId, cartId, productId);
        return check;
    }

    public CartItem getCartItem(Long memberId, Long cartId, Long productId) {
        return cartItemRepository.findByCart_memberIdAndCart_idAndProductId(memberId, cartId, productId).orElseThrow();
    }

    // CartItem 수량 변경
    @Transactional
    public CartItem updateCartItem(CartItem cartItem) {
        CartItem findCartItem = cartItemRepository.findById(cartItem.getId()).orElseThrow();

        findCartItem.setQuantity(cartItem.getQuantity());
        return findCartItem;
    }


    public boolean isCartItemExist(Long memberId, Long cartItemId) {
        return cartItemRepository.existsByCart_memberIdAndId(memberId, cartItemId);
    }

    // Cart에서 CartItem 삭제
    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {
        cartItemRepository.deleteByCart_memberIdAndId(memberId, cartItemId);
    }

    // 회원이 장바구니에 담은 모든 CartItem 가져오기
    public List<CartItem> getCartItems(Long memberId, Long cartId) {
        return cartItemRepository.findByCart_memberIdAndCart_id(memberId, cartId);
    }


    public List<CartItem> getCartItems(Long memberId) {
        return cartItemRepository.findByCart_memberId(memberId);
    }
}
