package yun.likelion.be_study.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yun.likelion.be_study.entity.Comments;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsResponseDto {
    private Long commentId;
    private String nickname;
    private String content;

    public static CommentsResponseDto from(Comments comments) {
        return CommentsResponseDto.builder()
                .commentId(comments.getCommentId())
                .nickname(comments.getNickname())
                .content(comments.getContent())
                .build();
    }
}
