package hello.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestController
public class ApiExceptionController {
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String id;
        private String name;
    }

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        return new MemberDto(id, "hello" + id);
    }
}
