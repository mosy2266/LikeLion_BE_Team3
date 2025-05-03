package yun.likelion.be_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.Boards;

@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long> {
}
