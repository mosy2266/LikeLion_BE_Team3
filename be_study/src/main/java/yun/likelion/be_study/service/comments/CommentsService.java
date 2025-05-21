package yun.likelion.be_study.service.comments;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.dto.comments.CommentsCreateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.dto.comments.CommentsUpdateRequestDto;
import yun.likelion.be_study.entity.boards.Boards;
import yun.likelion.be_study.entity.comments.Comments;
import yun.likelion.be_study.entity.members.Members;
import yun.likelion.be_study.repository.boards.BoardsRepository;
import yun.likelion.be_study.repository.comments.CommentsRepository;
import yun.likelion.be_study.repository.members.MembersRepository;

@Service
public class CommentsService {
    private final BoardsRepository boardsRepository;
    private final CommentsRepository commentsRepository;
    private final MembersRepository membersRepository;

    public CommentsService(BoardsRepository boardsRepository, CommentsRepository commentsRepository,
                           MembersRepository membersRepository) {
        this.boardsRepository = boardsRepository;
        this.commentsRepository = commentsRepository;
        this.membersRepository = membersRepository;
    }

    //댓글 작성
    @Transactional
    public CommentsResponseDto createComment(Long boardId, CommentsCreateRequestDto dto, Long memberId) {
        //게시글 존재 여부 확인
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다. 게시글 id : " + boardId));

        Members member = membersRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Comments comment = new Comments();
        comment.setBoard(board);
        comment.setMember(member);
        comment.setNickname(member.getNickname());
        comment.setContent(dto.getContent());

        commentsRepository.save(comment);

        return CommentsResponseDto.from(comment);
    }

    //댓글 수정
    @Transactional
    public CommentsResponseDto updateComment(Long boardId, Long commentId, CommentsUpdateRequestDto dto,
                                             Long memberId) {
        //게시글-댓글 관계 검증
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        //비밀번호 검증
        if (!comment.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        //댓글 내용 업데이트
        comment.setContent(dto.getContent());

        return CommentsResponseDto.from(comment);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long boardId, Long commentId, Long memberId) {
        //게시글-댓글 관계 검증
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        //비밀번호 검증
        if (!comment.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        commentsRepository.delete(comment);
    }
}
