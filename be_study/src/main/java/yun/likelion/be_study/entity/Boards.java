package yun.likelion.be_study.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
//@NamedEntityGraph로 그래프를 정의해두고 @EntityGraph로 참조해서 사용
@NamedEntityGraph(name = "Boards.withComments", attributeNodes = @NamedAttributeNode(("comments")))
@SQLDelete(sql = "UPDATE boards SET deleted = true WHERE board_id = ?") //논리적 삭제 구현
@Where(clause = "deleted = false")
public class Boards extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotBlank
    private String name;

    @NotBlank(message = "제목은 반드시 입력해야 합니다.")
    private String title;

    @NotBlank(message = "내용은 반드시 입력해야 합니다.")
    private String content;

    @Column(name = "like_count")
    private long likeCount = 0;

    @Column(name = "view_count")
    private long viewCount = 0;

    //논리적 삭제를 위한 필드
    @Column(nullable = false)
    private boolean deleted = false;

    //양방향 매핑 추가
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 20) // Boards.comments 컬렉션을 로딩할 때 최대 20개씩 조회(여러 게시글의 댓글 목록을 반복 조회할 때)
    private List<Comments> comments = new ArrayList<>();
}
