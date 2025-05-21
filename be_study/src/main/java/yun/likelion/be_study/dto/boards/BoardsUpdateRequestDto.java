package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.entity.Members;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsUpdateRequestDto {
    private Members member;
    private String title;
    private String content;
}
