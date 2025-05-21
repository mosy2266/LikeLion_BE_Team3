package yun.likelion.be_study.entity.boards;

import jakarta.persistence.*;
import lombok.*;
import yun.likelion.be_study.entity.members.Members;

@Entity
@Table(name = "board_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "board_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLikesId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Boards board;
}
