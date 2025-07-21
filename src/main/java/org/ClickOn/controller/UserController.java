package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Address;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.AddressRepository;
import org.ClickOn.service.AddressService;
import org.ClickOn.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressRepository addressRepository;

    @GetMapping("/mypage")
    public String mypage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !(auth.getPrincipal() instanceof Users)) {
            return "redirect:/login";
        }
        Users userDetail = (Users) auth.getPrincipal();
        Users user = userService.getUserInfo(userDetail.getIdx());
        List<Address> address = addressRepository.findByUser_Idx(userDetail.getIdx());
        model.addAttribute("addresses", address);
        model.addAttribute("user", user);
        return "user/mypage/mypage";
    }

}
