package org.ClickOn.controller;

import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.UsersRepository;
import org.ClickOn.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EmailVerificationController {

    private final UsersRepository usersRepository;
    private final AuthService authService;
    // 이메일 재전송 폼
    @GetMapping("/verify")
    public String verifyForm() {
        return "user/verify";
    }

    // 이메일 인증 API
    @GetMapping("/api/verify")
    public String verify(@RequestParam("token") String token, Model model) {
        Users user = usersRepository.findByVerificationToken(token);
        if(user == null) {
           model.addAttribute("message", "유효하지 않은 인증 링크입니다.");
        } else {
            user.setEnabled(true); // 인증완료
            user.setVerificationToken(null); //토큰 제거
            usersRepository.save(user);
            model.addAttribute("message", "이메일 인증이 완료되었습니다.");
        }
        return "user/verified"; // 이메일 인증 완료 폼
    }

    // 이메일 재전송 API
    @PostMapping("/api/resend-verification")
    public ResponseEntity<?> resendGVerification(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Users user = usersRepository.findByEmail(email);
        if(user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "등록되지 않은 이메일입니다."));
        }

        if(user.isEnabled()) {
            return ResponseEntity.badRequest().body(Map.of("error", "이미 이메일 인증이 완료된 계정입니다."));
        }

        // 새 인증토큰 생성
        String newToken = UUID.randomUUID().toString();
        user.setVerificationToken(newToken);
        usersRepository.save(user);

        // 메일 재전송
        authService.sendVerificationEmail(user);

        return ResponseEntity.ok().body(Map.of("message", "인증 메일이 재전송 되었습니다."));
    }
}
