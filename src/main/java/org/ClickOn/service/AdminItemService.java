package org.ClickOn.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.ItemSaveDto;
import org.ClickOn.entity.Items;
import org.ClickOn.repository.ItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AdminItemService {
    private final ItemsRepository itemsRepository;
    
    private final String uploadDir = "C:/Users/Gilink15/IdeaProjects/ClickOn/src/main/resources/static/images/"; // 이미지 저장 경로

    // 아이템 등록 로직
    @Transactional
    public void saveItem(ItemSaveDto dto) throws IOException {
        Items item = new Items();

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());
        item.setStock(dto.getStock());
        item.setSoldOut(dto.getSoldOut() != null ? dto.getSoldOut() : "N"); // 솔드아웃에 값이 따로 없으면 품절아님(N)
        item.setDiscount(dto.getDiscount());
        item.setView(0L);

        //이미지 저장
        MultipartFile file = dto.getImageFile();
        if(file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            item.setImageName(fileName);
        }
        itemsRepository.save(item);
    }
}
