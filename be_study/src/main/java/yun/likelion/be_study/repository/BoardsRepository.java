package yun.likelion.be_study.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.Boards;

import java.util.Optional;

@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long> {

    //@Query로 작성된 JPQL/SQL문이 데이터 변경용일 때 이를 알리기 위해 사용 -> 없으면 예외를 던짐, 반드시 트랜잭션 내에서 사용
    @Modifying
    //기본 CRUD 외에 직접 작성한 JPQL/SQL문을 실행
    @Query("UPDATE Boards b SET b.viewCount = b.viewCount + :inc where b.boardId = :id")
    void incrementViewCount(@Param("id") Long id, @Param("inc") Long inc);

    //게시글 상세 조회용 메서드
    //type 속성이 LOAD로 설정되어 있으면 그래프에 명시된 속성만 EAGER로 처리, 나머지는 기존 설정(LAZY)을 유지
    @EntityGraph(value = "Boards.withComments", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b FROM Boards b WHERE b.boardId = :boardId")
    Optional<Boards> findByIdWithComments(@Param("boardId") Long boardId);
}
