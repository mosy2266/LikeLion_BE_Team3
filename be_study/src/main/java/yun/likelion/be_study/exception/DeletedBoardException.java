package yun.likelion.be_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class DeletedBoardException extends RuntimeException {
    public DeletedBoardException(String message) {
        super(message);
    }
}
