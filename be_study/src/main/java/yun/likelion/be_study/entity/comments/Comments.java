package yun.likelion.be_study.entity.comments;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import yun.likelion.be_study.entity.members.Members;
import yun.likelion.be_study.entity.boards.Boards;

@Entity
@Getter
@Setter
@Table(name = "comments")
@BatchSize(size = 30) // Comments 엔티티를 프록시로 로딩할 때 최대 30개까지 한번에 조회(댓글 엔티티 여러 건을 개별 식별자로 로딩할 때)
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    //Comments(댓글) 엔티티와 Boards(게시글) 엔티티가 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY) //FetchType.LAZY -> 실제로 board 필드에 접근이 필요할 때까지 쿼리 X
    //Comments 테이블에 board_id라는 컬럼 생성, 해당 애너테이션이 붙은 속성의 기본키를 외래키로 지정(해당 댓글이 어느 게시글에 속하는지)
    @JoinColumn(name = "board_id", nullable = false)
    private Boards board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member;

    @NotBlank
    private String nickname;

    @NotBlank
    private String content;

    @Column(name = "like_count")
    private long likeCount = 0;
}
