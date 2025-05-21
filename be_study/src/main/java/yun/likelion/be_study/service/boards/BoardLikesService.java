package yun.likelion.be_study.service.boards;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.entity.boards.BoardLikes;
import yun.likelion.be_study.entity.boards.Boards;
import yun.likelion.be_study.entity.members.Members;
import yun.likelion.be_study.repository.boards.BoardLikesRepository;
import yun.likelion.be_study.repository.boards.BoardsRepository;
import yun.likelion.be_study.repository.members.MembersRepository;

@Service
public class BoardLikesService {
    private final BoardLikesRepository boardLikesRepository;
    private final MembersRepository membersRepository;
    private final BoardsRepository boardsRepository;

    public BoardLikesService(BoardLikesRepository boardLikesRepository, MembersRepository membersRepository,
                             BoardsRepository boardsRepository) {
        this.boardLikesRepository = boardLikesRepository;
        this.membersRepository = membersRepository;
        this.boardsRepository = boardsRepository;
    }

    //좋아요
    @Transactional
    public long like(Long boardId, Long memberId) {
        Members member = membersRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if (boardLikesRepository.existsByMemberAndBoard(member, board)) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다");
        }

        boardLikesRepository.save(BoardLikes.builder()
                .member(member)
                .board(board)
                .build());
        board.setLikeCount(board.getLikeCount() + 1);
        return board.getLikeCount();
    }

    //좋아요 취소
    @Transactional
    public long unlike(Long boardId, Long memberId) {
        Members member = membersRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        BoardLikes like = boardLikesRepository.findByMemberAndBoard(member, board)
                .orElseThrow(() -> new IllegalStateException("좋아요를 누르지 않았습니다."));

        boardLikesRepository.delete(like);
        board.setLikeCount(board.getLikeCount() - 1);
        return board.getLikeCount();
    }
}
