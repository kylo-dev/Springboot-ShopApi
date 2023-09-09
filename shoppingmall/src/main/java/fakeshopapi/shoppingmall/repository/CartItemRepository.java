package fakeshopapi.shoppingmall.repository;

import fakeshopapi.shoppingmall.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Cart를 memberId와 cartId와 productId로 조회하여 Cart안에 해당 맴버와 상품이 있는지 확인
    boolean existsByCart_memberIdAndCart_idAndProductId(Long memberId, Long cartId, Long productId);

    Optional<CartItem> findByCart_memberIdAndCart_idAndProductId(Long memberId, Long cartId, Long productId);

    // Cart를 memberId와 cartItemId로 조회하여 존재하는지 확인
    boolean existsByCart_memberIdAndId(Long memberId, Long cartItemId);

    // memberId와 cartItemId를 가지고 삭제
    void deleteByCart_memberIdAndId(Long memberId, Long cartItemId);

    List<CartItem> findByCart_memberIdAndCart_id(Long memberId, Long cartId);

    List<CartItem> findByCart_memberId(Long memberId);
}
