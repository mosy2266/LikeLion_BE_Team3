package org.likelion.be_study.board.persistence;


import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"comments"})
    Page<Board> findByCategory(Category category, Pageable pageable);
}
