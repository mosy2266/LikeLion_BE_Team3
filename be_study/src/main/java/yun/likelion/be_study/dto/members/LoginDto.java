package yun.likelion.be_study.dto.members;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
