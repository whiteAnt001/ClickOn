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

    public List<Items> findAllItems() {
        return itemsRepository.findAll();
    }
}
