package org.ClickOn.service;

import lombok.RequiredArgsConstructor;
import org.ClickOn.dto.AuthDto;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.UsersRepository;
import org.ClickOn.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
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
        user.setEnabled(false); //이메일 인증 전

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token); //이메일 인증 토큰
        
        usersRepository.save(user); //정보 저장
        return true;
    }

    public String login(AuthDto authDto) {
        Users user = usersRepository.findByEmail(authDto.getEmail());
        //존재하지 않는 사용자
        if(user == null) {
            return null;
        }
        //이메일 인증 안됨
//        if(!user.isEnabled()) {
//            return null;
//        }
        //비밀번호 틀림
        if(!passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            return null;
        }
        return jwtUtil.generateToken(user.getEmail());
    }
}
