package hello.exception.exception.advice;

import hello.exception.exception.BadReqEx;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice(annotations = BadReqEx.class)
public class HtmlExHandlerAdvice {
    /*단순 경로만 넘기기*/
/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String userHandlerEx(UserException ex) {
        log.error("[ Html UserException Handler ]", ex);
        log.error("[ Html UserException Handler ] {}", ex.getMessage());
        return "/error/4xx";
    }
*/
    /*모델에 담아서 넘기기*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ModelAndView userHandlerModelEx(UserException ex) {
        log.error("[ Html Model UserException Handler ]", ex);
        log.error("[ Html Model UserException Handler ] {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        // model.addAttribute와 같은 역할을 수행함 error가 키, Bad Request가 밸류
        modelAndView.addObject("error", HttpStatus.BAD_REQUEST.value());
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("text", "잘좀 써라");
        modelAndView.setViewName("/error/4xx");
        return modelAndView;
    }
}
