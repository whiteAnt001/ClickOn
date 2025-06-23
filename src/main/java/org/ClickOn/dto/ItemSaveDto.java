package org.ClickOn.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class ItemSaveDto {
    //@NotBlank(message = "상품명을 입력하세요.")
    private String name;
    private String description;
    //@NotNull(message = "가격을 입력하세요.")
    private Integer price;
    //@NotNull(message = "재고를 입력하세요.")
    //@Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;
    //@NotBlank(message = "카테고리를 선택하세요.")
    private String category;
    private String discount;
    private String soldOut;

    private MultipartFile imageFile;
}
