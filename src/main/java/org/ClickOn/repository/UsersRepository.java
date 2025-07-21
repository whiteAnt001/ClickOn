package org.ClickOn.repository;

import org.ClickOn.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email); // 유저 이메일 찾기
    Users findByVerificationToken(String token); //인증토큰 찾기
    Optional<Users> findById(long id);
}
