package org.likelion.be_study.comment.presentation;

import lombok.RequiredArgsConstructor;
import org.likelion.be_study.comment.application.CommentService;
import org.likelion.be_study.comment.presentation.dto.CreateCommentRequest;
import org.likelion.be_study.comment.presentation.dto.UpdateCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{boardId}")
    public ResponseEntity<Void> create(@PathVariable Long boardId, @RequestBody CreateCommentRequest commentRequest){
        commentService.createComment(boardId, commentRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{commentId}")
    public ResponseEntity<Void> update(@PathVariable Long commentId, @RequestBody UpdateCommentRequest commentRequest){
        commentService.updateComment(commentId, commentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}
