package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 여기서는 ExceptionResolver에서 모든것을 한번에 처리한 경우.
        // 원래는 WAS에 sendError에 대한 정보를 담아서 다시 요청을 수행했었음

        // 에러 발생 시 Response에 헤더 정보를 담아서 JSON으로 변환후 보내는 것
        try {
            if (e instanceof UserException) {
                // 두가지의 경우가 있음 JSON으로 처리하는 경우, Html로 처리하는 경우
                log.info("UserException to 400");
                // Response Header를 만들어줌.
                 httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                 //헤더에 대한 정보를 끌고옴
                if ("application/json".equals(httpServletRequest.getHeader("accept"))) {
                    Map<String, Object> errorResult = new HashMap<>();
                    //에러 클래스를 담아둠.
                    errorResult.put("ex", e.getClass());
                    //에러 메시지를 담아둠
                    errorResult.put("message", e.getMessage());
                    //JSON으로 들어온 정보를 담아둔 Map을 String으로 변경
                    String result = objectMapper.writeValueAsString(errorResult);

                    // 결과 반환 시 헤더의 정보를 다시 씌워줌
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");

                    // String으로 변환 된 Map을 다시 Json 형태의 String으로 바디에 덮어씌워줌
                    httpServletResponse.getWriter().write(result);
                    return new ModelAndView();
                } else {
                    // text/html 뷰 템플릿의 뷰를 렌더링 해
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException ex) {
            log.error("resolve exception", ex);
        }
        return null;
    }
}
