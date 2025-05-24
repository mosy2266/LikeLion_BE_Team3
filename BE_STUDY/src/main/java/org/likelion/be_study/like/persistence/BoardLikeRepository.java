package org.likelion.be_study.like.persistence;


import java.util.Optional;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.like.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardId(Long boardId);
    boolean existsByBoardId(Long boardId);
}
