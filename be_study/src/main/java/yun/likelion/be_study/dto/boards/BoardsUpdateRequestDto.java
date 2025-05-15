package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsUpdateRequestDto {
    private String title;
    private String content;
}
