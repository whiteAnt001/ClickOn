package org.ClickOn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 리뷰 고유번호
    private Long item_id; // 상품번호
    private String user_id; // 유저 아이디(이메일)
    private Integer rating; // 별점
    private String comment; // 리뷰내용
    private LocalDateTime createdAt; // 리뷰 작성시간
    
    // 리뷰 작성시간 초기화
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
