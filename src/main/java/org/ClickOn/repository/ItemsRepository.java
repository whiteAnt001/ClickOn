package org.ClickOn.repository;

import org.ClickOn.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    // 카테고리별로 아이템 찾기
    List<Items> findByCategory(String category);
}
