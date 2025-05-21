package yun.likelion.be_study.dto.comments;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.entity.members.Members;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsCreateRequestDto {

    private Members member;

    @NotBlank
    private String content;

}
