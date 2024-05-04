package hello.exception.exception;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임시에 애노테이션 정보를 사용할 수 있다는 것을 의미합니다.
@Target(ElementType.METHOD) // 이 애노테이션이 어떤 요소에 적용될 수 있는지를 지정합니다. 여기서는 메서드에 적용하는 것 입니다.
public @interface BadReqEx {
}
