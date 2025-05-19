package org.likelion.be_study.comment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.be_study.base.BaseTimeEntity;
import org.likelion.be_study.board.domain.Board;


@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void update(String content){
        this.content = content;
    }

    @Builder
    public Comment(
        String content,
        Board board
    ) {
        this.content = content;
        this.board = board;
    }

}
