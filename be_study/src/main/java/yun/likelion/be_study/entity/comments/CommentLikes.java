package yun.likelion.be_study.entity.comments;

import jakarta.persistence.*;
import lombok.*;
import yun.likelion.be_study.entity.members.Members;

@Entity
@Table(name = "comment_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "comment_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikesId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Comments comment;
}
