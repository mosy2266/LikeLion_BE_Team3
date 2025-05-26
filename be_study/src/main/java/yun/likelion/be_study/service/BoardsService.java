package yun.likelion.be_study.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.dto.boards.BoardsCreateRequestDto;
import yun.likelion.be_study.dto.boards.BoardsDetailResponseDto;
import yun.likelion.be_study.dto.boards.BoardsSimpleResponseDto;
import yun.likelion.be_study.dto.boards.BoardsUpdateRequestDto;
import yun.likelion.be_study.entity.Boards;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.entity.QBoards;
import yun.likelion.be_study.exception.DeletedBoardException;
import yun.likelion.be_study.repository.BoardsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static yun.likelion.be_study.util.SecurityUtils.getLoginMember;
import static yun.likelion.be_study.util.SecurityUtils.validateMember;

@Service
public class BoardsService {
    private final BoardsRepository boardsRepository;
    private final JPAQueryFactory queryFactory;

    public BoardsService(BoardsRepository boardsRepository, JPAQueryFactory queryFactory) {
        this.boardsRepository = boardsRepository;
        this.queryFactory = queryFactory;
    }


    //Pageable을 활용한 페이지네이션
    /*
    public Page<BoardsSimpleResponseDto> getAllBoards(Pageable pageable) {
        return boardsRepository.findAll(pageable)
                .map(BoardsSimpleResponseDto::from);
    }
    */

    //게시글 목록
    @Transactional(readOnly = true)
    //QueryDSL 적용 -> 동적 쿼리를 통해 게시판 검색 기능 구현
    public Page<BoardsSimpleResponseDto> getAllBoards(String nickname, String keyword, Pageable pageable) {
        QBoards b =  QBoards.boards;

        //동적 검색 조건 빌더 : 이름과 키워드
        BooleanBuilder builder = new BooleanBuilder();
        if (nickname != null && !nickname.isBlank()) {
            builder.and(b.member.nickname.eq(nickname));
        }
        if (keyword != null && !keyword.isBlank()) {
            builder.and(
                    b.title.contains(keyword)
                            .or(b.content.contains(keyword))
            );
        }

        //정렬 조건 매핑
        OrderSpecifier<?>[] orders = pageable.getSort().stream()
                .map(order -> order.isAscending()
                        ? b.createdDate.asc() : b.createdDate.desc())
                .toArray(OrderSpecifier[]::new);

        //페이징된 리스트 조회
        List<Boards> content = queryFactory
                .selectFrom(b)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders)
                .fetch();

        //전체 건수 조회
        long total = queryFactory
                .select(b.count())
                .from(b)
                .where(builder)
                .fetchOne();

        //DTO 변환 및 PageImpl 반환
        List<BoardsSimpleResponseDto> dtoList = content.stream()
                .map(BoardsSimpleResponseDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }

    //게시글 작성
    public BoardsSimpleResponseDto createBoard(BoardsCreateRequestDto boardsCreateRequestDto) {
        Boards board = new Boards();
        Members loginMember = getLoginMember();

        board.setMember(loginMember);
        board.setTitle(boardsCreateRequestDto.getTitle());
        board.setContent(boardsCreateRequestDto.getContent());
        boardsRepository.save(board);

        return BoardsSimpleResponseDto.from(board);
    }

    //게시글 열람
    @Transactional(readOnly = true)
    public BoardsDetailResponseDto getBoard(Long boardId) {
        //수정 및 조회 로직에서는 board 객체(엔티티)가 바로 필요하므로 .orElseThrow()
        /*
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));

        BoardsSimpleResponseDto dto = BoardsSimpleResponseDto.from(board);
        List<Comments> comments = commentsRepository.findAllByBoard_BoardId(boardId);

        return BoardsDetailResponseDto.from(dto, comments);
        */

        //기존 2번의 조회(쿼리)를 1번으로 통합
        Boards board = boardsRepository.findByIdWithComments(boardId)
                .orElseThrow(() -> new DeletedBoardException("삭제된 글입니다."));

        //board.getComments() 호출 시 추가 쿼리 발생 X
        return BoardsDetailResponseDto.from(BoardsSimpleResponseDto.from(board), board.getComments());
    }

    //게시글 수정
    @Transactional
    public BoardsSimpleResponseDto updateBoard(Long boardId, BoardsUpdateRequestDto boardsUpdateRequestDto) {
        //수정 및 조회 로직에서는 board 객체(엔티티)가 바로 필요하므로 findById().orElseThrow()
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));

        Members loginMember = getLoginMember();
        validateMember(board.getMember(), loginMember);

        board.setTitle(boardsUpdateRequestDto.getTitle());
        board.setContent(boardsUpdateRequestDto.getContent());

        return BoardsSimpleResponseDto.from(board);
    }

    //게시글 삭제
    @Transactional
    public void deleteBoard(Long boardId) {
        Boards board = boardsRepository.findById(boardId)
                        .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));
        Members loginMember = getLoginMember();

        validateMember(board.getMember(), loginMember);
        boardsRepository.deleteById(boardId);
    }

    //게시글 좋아요
    @Transactional
    public long incrementLike(Long boardId) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));
        board.setLikeCount(board.getLikeCount() + 1);

        return board.getLikeCount();
    }

    //게시글 좋아요 취소
    @Transactional
    public long decrementLike(Long boardId) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));
        if (board.getLikeCount() > 0) {
            board.setLikeCount(board.getLikeCount() - 1);
        }

        return board.getLikeCount();
    }
}
