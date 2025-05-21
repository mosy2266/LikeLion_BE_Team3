package yun.likelion.be_study.dto.boards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.entity.comments.Comments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardsDetailResponseDto {
    private Long boardId;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private long likeCount;
    private long viewCount;
    private List<CommentsResponseDto> comments;

    //Boards 엔티티를 DTO로 변환해주는 정적 메서드
    public static BoardsDetailResponseDto from(BoardsSimpleResponseDto dto, List<Comments> comments) {
        return BoardsDetailResponseDto.builder()
                .boardId(dto.getBoardId())
                .nickname(dto.getNickname())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdDate(dto.getCreatedDate())
                .lastModifiedDate(dto.getLastModifiedDate())
                .likeCount(dto.getLikeCount())
                .viewCount(dto.getViewCount())
                .comments(comments.stream()
                        .map(CommentsResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
