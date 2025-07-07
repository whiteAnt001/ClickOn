package org.ClickOn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthDto {
    private String name;
    private String email;
    private String password;
    private String phone;
}
