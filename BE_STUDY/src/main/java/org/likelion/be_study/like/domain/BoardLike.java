package org.likelion.be_study.like.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.be_study.base.BaseTimeEntity;
import org.likelion.be_study.board.domain.Board;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Board board;

    private boolean is_liked;

    private BoardLike(Board board) {
        this.board = board;
        this.is_liked = true;
    }

    public static BoardLike of(Board board){
        return new BoardLike(board);
    }

    public void like(){
        this.is_liked = true;
    }

    public void unlike() {
        this.is_liked = false;
    }




}
