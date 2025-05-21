package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.entity.members.Members;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsCreateRequestDto {
    private Members member;
    private String title;
    private String content;
}
