package hello.exception.exception.advice;

import hello.exception.exception.BadReqEx;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = BadReqEx.class)
public class ExHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgExHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResult("Bad", e.getMessage());
    }
    /*보통은 이런 방식으로 사용할거같다 */
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[UserException] ex", e);
        return new ResponseEntity<>(new ErrorResult("User오류", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public  ErrorResult badRequestExHandler(Exception e) {
        log.error("[부모 Exception] ex", e);
        return new ErrorResult("Exception", "서버 내부 오류입니다");
    }
}
