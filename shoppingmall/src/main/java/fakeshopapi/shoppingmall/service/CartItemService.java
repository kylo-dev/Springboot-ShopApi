package fakeshopapi.shoppingmall.service;

import fakeshopapi.shoppingmall.domain.Cart;
import fakeshopapi.shoppingmall.domain.CartItem;
import fakeshopapi.shoppingmall.dto.AddCartItemDto;
import fakeshopapi.shoppingmall.dto.CartItemResponseDto;
import fakeshopapi.shoppingmall.repository.CartItemRepository;
import fakeshopapi.shoppingmall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CartItemResponseDto addCartItem(AddCartItemDto addCartItemDto) {

        Cart cart = cartRepository.findById(addCartItemDto.getCartId()).orElseThrow();
        CartItem cartItem = new CartItem();
        setCartItem(addCartItemDto, cartItem, cart);
        cartItemRepository.save(cartItem);

        CartItemResponseDto cartItemResponseDto = getCartItemResponseDto(addCartItemDto);

        return cartItemResponseDto;
    }

    private static void setCartItem(AddCartItemDto addCartItemDto, CartItem cartItem, Cart cart) {
        cartItem.setCart(cart);
        cartItem.setQuantity(addCartItemDto.getQuantity());
        cartItem.setProductId(addCartItemDto.getProductId());
        cartItem.setProductPrice(addCartItemDto.getProductPrice());
        cartItem.setProductTitle(addCartItemDto.getProductTitle());
        cartItem.setProductDescription(addCartItemDto.getProductDescription());
    }

    private static CartItemResponseDto getCartItemResponseDto(AddCartItemDto addCartItemDto) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setCartId(addCartItemDto.getCartId());
        cartItemResponseDto.setProductId(addCartItemDto.getProductId());
        cartItemResponseDto.setProductTitle(addCartItemDto.getProductTitle());
        cartItemResponseDto.setProductPrice(addCartItemDto.getProductPrice());
        cartItemResponseDto.setQuantity(addCartItemDto.getQuantity());
        return cartItemResponseDto;
    }

    // 한 Member의 Cart에 같은 Product가 있는지 확인
    public boolean isCartItemExist(Long memberId, Long cartId, Long productId) {
        boolean check = cartItemRepository.existsByCart_memberIdAndCart_idAndProductId(memberId, cartId, productId);
        return check;
    }

    public CartItem getCartItem(Long memberId, Long cartId, Long productId) {
        return cartItemRepository.findByCart_memberIdAndCart_idAndProductId(memberId, cartId, productId).orElseThrow();
    }

    // CartItem 수량 변경
    @Transactional
    public CartItemResponseDto updateCartItem(CartItem cartItem) {
        CartItem findCartItem = cartItemRepository.findById(cartItem.getId()).orElseThrow();
        findCartItem.setQuantity(cartItem.getQuantity());

        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setCartId(findCartItem.getCart().getId());
        cartItemResponseDto.setProductId(findCartItem.getProductId());
        cartItemResponseDto.setProductTitle(findCartItem.getProductTitle());
        cartItemResponseDto.setProductPrice(findCartItem.getProductPrice());
        cartItemResponseDto.setQuantity(findCartItem.getQuantity());

        return cartItemResponseDto;
    }


    public boolean isCartItemExist(Long memberId, Long cartItemId) {
        return cartItemRepository.existsByCart_memberIdAndId(memberId, cartItemId);
    }

    // Cart에서 CartItem 삭제
    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {
        cartItemRepository.deleteByCart_memberIdAndId(memberId, cartItemId);
    }

    // 회원의 특정 장바구니에 담은 모든 CartItem 가져오기
    public List<CartItemResponseDto> getCartItems(Long memberId, Long cartId) {
        List<CartItem> findCartItems = cartItemRepository.findByCart_memberIdAndCart_id(memberId, cartId);
        List<CartItemResponseDto> cartItemResponseDtos = getCartItemResponseDtos(findCartItems);
        return cartItemResponseDtos;
    }

    // 한 회원에 모든 CartItem 가져오기
    public List<CartItemResponseDto> getCartItems(Long memberId) {
        List<CartItem> findCartItems = cartItemRepository.findByCart_memberId(memberId);
        List<CartItemResponseDto> cartItemResponseDtos = getCartItemResponseDtos(findCartItems);
        return cartItemResponseDtos;
    }

    private static List<CartItemResponseDto> getCartItemResponseDtos(List<CartItem> findCartItems) {
        List<CartItemResponseDto> cartItemResponseDtos = new ArrayList<>();

        for (CartItem findCartItem : findCartItems) {
            CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();

            cartItemResponseDto.setCartId(findCartItem.getCart().getId());
            cartItemResponseDto.setProductId(findCartItem.getProductId());
            cartItemResponseDto.setProductTitle(findCartItem.getProductTitle());
            cartItemResponseDto.setProductPrice(findCartItem.getProductPrice());
            cartItemResponseDto.setQuantity(findCartItem.getQuantity());
            cartItemResponseDtos.add(cartItemResponseDto);
        }
        return cartItemResponseDtos;
    }
}
