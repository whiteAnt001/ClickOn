package org.ClickOn.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.CartItemDto;
import org.ClickOn.dto.CartRequest;
import org.ClickOn.entity.Cart;
import org.ClickOn.entity.Items;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.CartRepository;
import org.ClickOn.repository.ItemsRepository;
import org.ClickOn.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemsRepository itemsRepository;
    private final UsersRepository usersRepository;

    // 카드에 상품을 저장
    @Transactional
    public void addToCart(Long userId, Long itemId, int quantity) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
        Items item = itemsRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));

        Cart cart = cartRepository.findByUserAndItem(user, item)
                .map(c -> {
                    c.setQuantity(c.getQuantity() + quantity);
                    return c;
                })
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItem(item);
                    newCart.setQuantity(quantity);
                    return newCart;
                });


        cartRepository.save(cart);
    }

    // 카드에 아이템 넣기
    @Transactional
    public List<CartItemDto> getCartItems(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        return cartRepository.findAllByUser(user).stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
    }
    
    // 카드에서 아이템 삭제하기
    @Transactional
    public void removeItem(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 카트 항목입니다."));
        cartRepository.delete(cart);
    }

    // 카트 총액 
    public int calculateTotalPrice(List<CartItemDto> cartItems) {
        return cartItems.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    // 금액 포멧팅
    public String formatPrice(int price) {
        return String.format("%,d원", price);
    }

    // 갯수 변경
    public void updateQuantity(Long userId, Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카트 ID입니다."));

        if(!cart.getUser().getIdx().equals(userId)) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    // 카트에 담긴 아이템 갯수
    public long getDistinctItemCountByUser(Users user) {
        return cartRepository.countDistinctItemByUser(user);
    }

}
