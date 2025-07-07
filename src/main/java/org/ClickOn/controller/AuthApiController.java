package org.ClickOn.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.AuthDto;
import org.ClickOn.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto authDto) {
        boolean result = authService.register(authDto);
        if(result) {
            //성공했을 경우
            return ResponseEntity.ok().body(Map.of("message", "회원가입 성공"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "이미 존재하는 이메일 입니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        String token = authService.login(authDto);
        if(token == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "이메일 또는 비밀번호가 올바르지 않습니다."));
        }

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);

        response.addCookie(cookie);

        return ResponseEntity.ok().body(Map.of("message", "로그인 성공!"));
    }
}
