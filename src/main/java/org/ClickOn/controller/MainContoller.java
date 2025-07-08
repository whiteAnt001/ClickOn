package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Items;
import org.ClickOn.repository.ItemsRepository;
import org.ClickOn.service.ItemService;
import org.ClickOn.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainContoller {

    private final ItemService itemService;
    private final ItemsRepository itemsRepository;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    public String main(Model model) {
        try{
            List<Items> items = itemService.findAllItems();
            model.addAttribute("items", items);

            //쿠키에서 엑세스 토큰 가져오기
            String token = jwtUtil.generateAccessToken()
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 조회 중 오류 발생");
            return "error";
        }
    }

    // 키보드 카테고리
    @GetMapping("/items/category/keyboard")
    public String keyboard(Model model) {
        List<Items> items = itemsRepository.findByCategory("KEYBOARD");
        model.addAttribute("items", items);
        return "item/category";
    }
    // 마우스 카테고리
    @GetMapping("/items/category/mouse")
    public String mouse(Model model) {
        List<Items> items = itemsRepository.findByCategory("MOUSE");
        model.addAttribute("items", items);
        return "item/category";
    }
    // 커스텀 카테고리
    @GetMapping("/items/category/custom")
    public String custom(Model model) {
        List<Items> items = itemsRepository.findByCategory("CUSTOM");
        model.addAttribute("items", items);
        return "item/category";
    }

//    // 악세사리 카테고리
//    @GetMapping("/items/category/keyboard")
//    public String acce(Model model) {
//        List<Items> items = itemsRepository.findByCategory("keyboard");
//        return "item/category";
//    }
}
