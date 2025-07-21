package org.ClickOn.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.AuthDto;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.UsersRepository;
import org.ClickOn.service.AuthService;
import org.ClickOn.util.JwtUtil;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto authDto) {
        boolean result = authService.register(authDto);
        if(result) {
            //성공했을 경우
            return ResponseEntity.ok().body(Map.of("message", "회원가입 성공"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "이미 존재하는 이메일 입니다."));
    }
    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        Users user = usersRepository.findByEmail(authDto.getEmail()); // 사용자 조회
        
        // 사용자가 존재하지 않거나 비밀번호가 존재하지 않는 경우
        if(user == null || !passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "계정 또는 비밀번호가 올바르지 않습니다."));
        }

        // 이메일 인증을 하지 않은 경우
        if(!user.isEnabled()) {
            return ResponseEntity.badRequest().body(Map.of("error", "이메일 인증을 완료해주세요."));
        }
        // 쿠키에 토큰을 저장
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        jwtUtil.addJwtToCookie(response, accessToken, "accessToken");
        jwtUtil.addJwtToCookie(response, refreshToken, "refreshToken");

        return ResponseEntity.ok().body(Map.of("message", "로그인 성공!"));
    }

    // 로그아웃(쿠키삭제)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 완료");
    }
}
