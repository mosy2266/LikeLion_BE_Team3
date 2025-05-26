package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.entity.Boards;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardsSimpleResponseDto {
    private Long boardsId;
    private String name;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private long likeCount;
    private long viewCount;

    //Boards 엔티티를 DTO로 변환해주는 정적 메서드
    public static BoardsSimpleResponseDto from(Boards board) {
        return BoardsSimpleResponseDto.builder()
                .boardsId(board.getBoardId())
                .name(board.getMember().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .build();
    }
}
