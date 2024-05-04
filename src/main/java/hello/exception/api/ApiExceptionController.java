package hello.exception.api;

import hello.exception.exception.BadReqEx;
import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@BadReqEx
@RestController
public class ApiExceptionController {
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String id;
        private String name;
    }

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable String id) throws NoHandlerFoundException {
        if (id.equals("ex"))
            throw new RuntimeException("잘못된 사용자");

        if (id.equals("bad"))
            throw new IllegalArgumentException("잘못된 요청이열~~");

        if (id.equals("user-ex"))
            throw new UserException("사용자 오류임니당~!!");
        if (id.equals("not")) {
            throw new NoHandlerFoundException("GetMapping", "not", HttpHeaders.EMPTY);
        }

        return new MemberDto(id, "hello" + id);
    }
    @GetMapping("/api/reason/bad")
    public String getReasonBad() {
        throw new BadRequestException();
    }

    /*
    String도 반환 가능
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String illeagalArgExHandler(IllegalArgumentException e) {
        log.error(" [IllegalArgumentException] ex", e);
        return  "error/500";
    }*/
}
