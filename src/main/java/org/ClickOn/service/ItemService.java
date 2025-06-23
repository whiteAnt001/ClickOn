package org.ClickOn.service;

import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Items;
import org.ClickOn.repository.ItemsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemsRepository itemsRepository;

    // 전체 상품을 찾는 로직
    public List<Items> findAllItems() {
        return itemsRepository.findAll();
    }

    // 단일 상품 로직(상품상세)
    public Items findItemById(Long id) {
        return itemsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id =" + id));
    }
}
