package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 에러 발생 시 HandlerExceptionResolver가 호출이 되는데
        // IllegalArgumentException 발생 시 sendError 상태코드를 변경 후 ModelAndView 를 반환
        try {

            if (e instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException 발생");
                // WAS에 error 정보를 sendError로 재정의 후 ModelAndView를 WAS에 전달하는 것.
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return new ModelAndView();
            }

        } catch (IOException ex) {
            log.error("resolveException", ex);
        }
        return null;
    }
}
