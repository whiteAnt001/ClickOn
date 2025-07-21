package org.ClickOn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CartRequest {
    private Long itemId;
    private int quantity;
}
