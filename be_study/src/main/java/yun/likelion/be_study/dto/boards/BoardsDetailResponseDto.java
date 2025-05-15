package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.entity.Comments;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardsDetailResponseDto {
    private Long boardId;
    private String name;
    private String title;
    private String content;
    private List<CommentsResponseDto> comments;

    public static BoardsDetailResponseDto from(BoardsSimpleResponseDto dto, List<Comments> comments) {
        return BoardsDetailResponseDto.builder()
                .boardId(dto.getBoardsId())
                .name(dto.getName())
                .title(dto.getTitle())
                .content(dto.getContent())
                .comments(comments.stream()
                        .map(CommentsResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
