package org.ClickOn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ClickOn.entity.Cart;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {
    private Long CartId;
    private Long itemId;
    private String itemName;
    private int price;
    private int quantity;

    public CartItemDto(Cart cart) {
        this.CartId = cart.getIdx();
        this.itemName = cart.getItem().getName();
        this.price = cart.getItem().getPrice();
        this.quantity = cart.getQuantity();
    }

}
