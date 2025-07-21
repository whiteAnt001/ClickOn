package org.ClickOn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressDto {
    private Long id;
    private String recipient;
    private String phone;
    private String postalCode;
    private String address1;
    private String address2;
    private boolean isDefault;
}
