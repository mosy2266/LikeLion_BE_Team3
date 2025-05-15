package yun.likelion.be_study.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.dto.boards.BoardsCreateRequestDto;
import yun.likelion.be_study.dto.boards.BoardsDetailResponseDto;
import yun.likelion.be_study.dto.boards.BoardsSimpleResponseDto;
import yun.likelion.be_study.dto.boards.BoardsUpdateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.entity.Boards;
import yun.likelion.be_study.entity.Comments;
import yun.likelion.be_study.repository.BoardsRepository;
import yun.likelion.be_study.repository.CommentsRepository;

import java.util.List;

@Service
public class BoardsService {
    private final BoardsRepository boardsRepository;
    private final CommentsRepository commentsRepository;

    public BoardsService(BoardsRepository boardsRepository, CommentsRepository commentsRepository) {
        this.boardsRepository = boardsRepository;
        this.commentsRepository = commentsRepository;
    }

    //게시글 목록
    @Transactional(readOnly = true)
    //Pageable을 활용한 페이지네이션
    public Page<BoardsSimpleResponseDto> getAllBoards(Pageable pageable) {
        return boardsRepository.findAll(pageable)
                .map(BoardsSimpleResponseDto::from);
    }

    //게시글 작성
    public BoardsSimpleResponseDto createBoard(BoardsCreateRequestDto boardsCreateRequestDto) {
        Boards board = new Boards();

        board.setName(boardsCreateRequestDto.getName());
        board.setTitle(boardsCreateRequestDto.getTitle());
        board.setContent(boardsCreateRequestDto.getContent());
        boardsRepository.save(board);

        return BoardsSimpleResponseDto.from(board);
    }

    //게시글 열람
    @Transactional(readOnly = true)
    public BoardsDetailResponseDto getBoard(Long boardId) {
        //수정 및 조회 로직에서는 board 객체(엔티티)가 바로 필요하므로 .orElseThrow()
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));

        BoardsSimpleResponseDto dto = BoardsSimpleResponseDto.from(board);
        List<Comments> comments = commentsRepository.findAllByBoard_BoardId(boardId);

        return BoardsDetailResponseDto.from(dto, comments);
    }

    //게시글 수정
    @Transactional
    public BoardsSimpleResponseDto updateBoard(Long boardId, BoardsUpdateRequestDto boardsUpdateRequestDto) {
        //수정 및 조회 로직에서는 board 객체(엔티티)가 바로 필요하므로 findById().orElseThrow()
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("there is no board with id " + boardId));

        board.setTitle(boardsUpdateRequestDto.getTitle());
        board.setContent(boardsUpdateRequestDto.getContent());

        return BoardsSimpleResponseDto.from(board);
    }

    //게시글 삭제
    @Transactional
    public void deleteBoard(Long boardId) {
        //삭제 로직에서는 존재 여부만 체크하면 되므로 existsById()
        if (!boardsRepository.existsById(boardId)) {
            throw new EntityNotFoundException("there is no board with id " + boardId);
        }
        boardsRepository.deleteById(boardId);
    }
}
