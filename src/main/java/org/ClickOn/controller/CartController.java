package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.CartItemDto;
import org.ClickOn.dto.CartRequest;
import org.ClickOn.entity.Cart;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.CartRepository;
import org.ClickOn.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepository;

    // 카드에 담기
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !(auth.getPrincipal() instanceof Users)) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        Users user = (Users) auth.getPrincipal();
        Long userId = user.getIdx();

        cartService.addToCart(userId, request.getItemId(), request.getQuantity());
        return ResponseEntity.ok("장바구니에 담겼습니다.");
    }

    // 카트 폼
    @GetMapping("/cart")
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !(auth.getPrincipal() instanceof Users)) {
            return "redirect:/login";
        }
        Users user = (Users) auth.getPrincipal();
        List<CartItemDto> items = cartService.getCartItems(user.getIdx());
        // 총액 계산/포멧팅
        int totalPrice = cartService.calculateTotalPrice(items);
        String formattedTotalPrice = cartService.formatPrice(totalPrice);

        model.addAttribute("cartItems", items);
        model.addAttribute("totalPrice", formattedTotalPrice);

        return "user/cart";
    }
    // 아이템 삭제
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<?> removeItem(@PathVariable Long cartId) {
        cartService.removeItem(cartId);
        return ResponseEntity.ok("장바구니에서 삭제되었습니다.");
    }
    
    // 수량 변경
    @PatchMapping("/cart/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody CartItemDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) auth.getPrincipal();
        int newQuantity = dto.getQuantity();

        cartService.updateQuantity(user.getIdx(), id ,newQuantity);
        return ResponseEntity.ok("수량이 변경되었습니다.");
    }
    
    
}
