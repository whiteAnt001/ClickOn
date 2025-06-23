package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Items;
import org.ClickOn.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainContoller {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(Model model) {
        List<Items> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/keyboard")
    public String keyboard() {
        return "category";
    }
}
