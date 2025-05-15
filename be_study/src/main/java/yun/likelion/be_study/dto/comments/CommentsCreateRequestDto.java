package yun.likelion.be_study.dto.comments;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsCreateRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String content;

}
