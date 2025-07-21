package org.ClickOn.controller;

import org.ClickOn.dto.AddressDto;
import org.ClickOn.entity.Users;
import org.ClickOn.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller

public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    // 주소 추가 폼
    @GetMapping("/mypage/address/add")
    public String addAddress(Model model) {
        model.addAttribute("address", new AddressDto());
        return "user/mypage/addAddress";
    }

    // 주소 추가
    @PostMapping("/mypage/address/add")
    public String saveAddress(@ModelAttribute AddressDto address) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) auth.getPrincipal();
        addressService.addAddress(user.getIdx(), address);
        return "redirect:/mypage";
    }

    // 기본 주소 변경
    @PostMapping("/mypage/address/default")
    public ResponseEntity<?> defaultAddress(@RequestBody Map<String, Long> req) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users user = (Users) auth.getPrincipal();

            Long addressId = req.get("id");
            Long userId = user.getIdx();

            addressService.setDefaultAddress(userId, addressId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
