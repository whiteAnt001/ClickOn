package org.ClickOn.service;

import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.AuthDto;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.UsersRepository;
import org.ClickOn.util.JwtUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JavaMailSender mailSender;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String jwtSecret = "gmlsroal1ghdlqslek";
    private final long jwtExpirationMs = 86400000;

    public boolean register(AuthDto authDto) {
        //아이디 중복 확인
        if(usersRepository.findByEmail(authDto.getEmail()) != null) {
            return false; //존재하는 닉네임
        }

        Users user = new Users();
        user.setName(authDto.getName());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));
        user.setEmail(authDto.getEmail());
        user.setPhone(authDto.getPhone());
        user.setRole("일반");
        user.setEnabled(false); //이메일 인증 전

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token); //이메일 인증 토큰
        
        usersRepository.save(user); //정보 저장

        sendVerificationEmail(user);
        return true;
    }

    //이메일 전송 메서드
    public void sendVerificationEmail(Users user) {
        String subject = "Click On 회원가입 이메일 인증";
        String verificationUrl = "http://localhost:8080/api/verify?token=" + user.getVerificationToken();

        String body = "Click On 이메일 인증입니다. \n\n"
                + "이메일 인증을 완료하려면 아래 링크를 클릭해주세요.: \n"
                + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public String login(AuthDto authDto) {
        Users user = usersRepository.findByEmail(authDto.getEmail());
        //존재하지 않는 사용자
        if(user == null) {
            return null;
        }
        //이메일 인증 안됨
        if(!user.isEnabled()) {
            return null;
        }
        //비밀번호 틀림
        if(!passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            return null;
        }
        return jwtUtil.generateAccessToken(user);
    }
}
