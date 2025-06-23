package org.ClickOn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@Setter
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 상품 고유ID
    private String name; // 상품 이름
    private String description; // 상세섦명
    private Integer price; // 가격
    private Integer stock; // 재고
    private String category; // 카테고리
    private String imageName; // 이미지
    private LocalDateTime createAt; // 등록일
    private String soldOut; // 품절여부
    private List<String> tags; // 태그
    private String discount; // 할인율
    private Long view; // 조회수

    // 등록일 포멧팅
    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
