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
        // 에러가 발생한 경우 sendError로 상태코드를 변경 후 ModelAndView를 반환
        try {

            if (e instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException 발생");
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return new ModelAndView();
            }

        } catch (IOException ex) {
            log.error("resolveException", ex);
        }
        return null;
    }
}
