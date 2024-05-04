package hello.exception.exception;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임시에 애노테이션 정보를 사용할 수 있다는 것을 의미합니다.
@Target(ElementType.TYPE)
public @interface BadReqEx {
}
