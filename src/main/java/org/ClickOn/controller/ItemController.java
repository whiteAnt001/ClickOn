package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Items;
import org.ClickOn.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DecimalFormat;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 아이템 상세
    @GetMapping("/items/{id}")
    public String showItem(@PathVariable Long id, Model model) {
        Items item = itemService.findItemById(id);
        DecimalFormat df = new DecimalFormat("#,###"); //가격 포멧팅해서 넘기기
        String formatted = df.format(item.getPrice());
        model.addAttribute("formattedPrice", formatted);
        model.addAttribute("item", item);
        return "item/itemDetail";
    }

}
