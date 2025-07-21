package org.ClickOn.repository;

import org.ClickOn.dto.CartItemDto;
import org.ClickOn.entity.Cart;
import org.ClickOn.entity.Items;
import org.ClickOn.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndItem(Users user, Items item); // 유저 장바구니에 아이템 찾기
    List<Cart> findAllByUser(@Param("user") Users user); // 유저 찾기
    void deleteByUserAndItem(Users user, Items item); // 카트 아이템 삭제
    @Query("SELECT COUNT(DISTINCT c.item) FROM Cart c WHERE c.user = :user")
    long countDistinctItemByUser(@Param("user") Users user);
}
