package yun.likelion.be_study.repository.boards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.boards.BoardLikes;
import yun.likelion.be_study.entity.boards.Boards;
import yun.likelion.be_study.entity.members.Members;

import java.util.Optional;

@Repository
public interface BoardLikesRepository extends JpaRepository<BoardLikes, Long> {
    boolean existsByMemberAndBoard(Members member, Boards board);
    Optional<BoardLikes> findByMemberAndBoard(Members member, Boards board);
}
