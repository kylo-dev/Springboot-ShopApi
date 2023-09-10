package fakeshopapi.shoppingmall.dto;

import lombok.Data;

@Data
public class CartItemResponseDto {

    private Long cartId;
    private Long productId;
    private String productTitle;
    private Double productPrice;
    private int quantity;
}
