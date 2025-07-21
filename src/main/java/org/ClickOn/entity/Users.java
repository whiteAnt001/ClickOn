package org.ClickOn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 유저 고유ID
    private String name; // 이름
    private String email; // 이메일
    private String password; // 패스워드
    private String phone; // 전화번호
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }

    private LocalDateTime createDate; // 가입일
    private String birth; // 생년월일
    private String role; // 일반회원 / 관리자 / 판매자 구분
    private String oauthProvider; // 소셜 로그인 제공자(구글, 카카오, 네이버 등)
    private String oauthId; // 소셜 로그인 ID

    private String verificationToken;  // 이메일 인증용 토큰
    private boolean enabled;  // 인증 완료 여부 (기본 false, 인증 완료시 true)

    //가입일 포멧팅
    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 필요에 따라 수정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
