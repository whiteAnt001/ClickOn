package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.service.AdminItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminItemController {

    private AdminItemService adminItemService;

    // 아이템 등록 폼으로 이동
    @GetMapping("/items/new")
    public String newItem() {
        return "admin/newItem";
    }
}
