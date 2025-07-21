package org.ClickOn.controller;

import org.ClickOn.entity.Users;
import org.ClickOn.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() instanceof Users) {
            Users user = (Users)auth.getPrincipal();
            long count = cartService.getDistinctItemCountByUser(user);
            model.addAttribute("cartItemCount", count);
            model.addAttribute("loginUser", user);
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("cartItemCount", 0);
            model.addAttribute("isLoggedIn", false);
        }
    }
}
