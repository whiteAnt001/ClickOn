package org.ClickOn.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.UsersRepository;
import org.ClickOn.util.JwtUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    //JWT토큰에서 사용자 정보 추출
    public Users getUserInfoToken(String token) {
        Claims claims = jwtUtil.getClaims(token);
        String email = claims.getSubject();

        return usersRepository.findByEmail(email);
    }

    // userId로 사용자 정보 찾기
    public Users getUserInfo(Long userId) throws UsernameNotFoundException {
        return usersRepository.findById(userId).orElse(null);
    }
}
