package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.entity.Boards;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardsSimpleResponseDto {
    private Long boardsId;
    private String name;
    private String title;
    private String content;
    private long likeCount;

    //Boards 엔티티를 DTO로 변환해주는 정적 메서드
    public static BoardsSimpleResponseDto from(Boards board) {
        return BoardsSimpleResponseDto.builder()
                .boardsId(board.getBoardId())
                .name(board.getName())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(board.getLikeCount())
                .build();
    }
}
