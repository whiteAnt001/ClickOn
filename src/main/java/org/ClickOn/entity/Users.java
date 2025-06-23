package org.ClickOn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 유저 고유ID
    private String name; // 이름
    private String email; // 이메일
    private String password; // 패스워드
    private String phone; // 전화번호
    private String address; // 주소
    private LocalDateTime createDate; // 가입일
    private String birth; // 생년월일
    private String role; // 일반회원 / 관리자 / 판매자 구분
    private String oauthProvider; // 소셜 로그인 제공자(구글, 카카오, 네이버 등)
    private String oauthId; // 소셜 로그인 ID

    //가입일 포멧팅
    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }
}
