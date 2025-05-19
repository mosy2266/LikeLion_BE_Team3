package org.likelion.be_study.board.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.be_study.base.BaseTimeEntity;
import org.likelion.be_study.board.presentation.dto.UpdateBoardRequest;
import org.likelion.be_study.comment.domain.Comment;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @Builder
    public Board(
        String title,
        String content,
        Category category
    ) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void update(UpdateBoardRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.category = request.category();
    }
}
