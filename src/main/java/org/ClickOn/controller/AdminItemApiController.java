package org.ClickOn.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.ItemSaveDto;
import org.ClickOn.service.AdminItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminItemApiController {

    private final AdminItemService adminItemService;

    //아이템 등록
    @PostMapping("/items/new")
    public ResponseEntity<String> itemSave(@ModelAttribute ItemSaveDto dto,
                                           @RequestParam("imageFile") MultipartFile file) throws IOException {
        try{
            dto.setImageFile(file);
            adminItemService.saveItem(dto);
            return ResponseEntity.ok("등록 성공");
        } catch (Exception e) {
            System.out.println("name: " + dto.getName());
            System.out.println("price: " + dto.getPrice());
            System.out.println("file name: " + file.getOriginalFilename());
            System.out.println("date : " + dto.getCategory());
            System.out.println("할인률 : " + dto.getDiscount());
            System.out.println("상세 : " + dto.getDescription());
            System.out.println("재고 : " + dto.getStock());
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }
}
