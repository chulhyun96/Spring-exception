# ControllerAdvice를 이용하여 에러페이지 렌더링

`@ExceptionController`를 사용해서 예외를 깔끔하게 처리할 수 있게 되었지만, 정상 코드와 예외처리 코드가 하나의 컨트롤러에 섞여있있었다.

그리고 해당 클래스 내부에서 발생하는 예외발생 시에만 `@ExceptionController`가 발생하는것을 알 수 있었다.

`@ControllerAdvice` 또는 `@RestControllerAdvice`를 사용하여 둘을 분리할 수 있다.


```java
@Slf4j  
@RestControllerAdvice  
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
```

새로운 폴더를 만들고, 거기에다가 `ExHandlerAdvice`라는 새로운 클래스를 만들었다.

그리고 기존의 `ApiExceptionController`에서의 에러를 처리하는 로직을 가져오고, `ApiExceptionController`의 메서드는 없애줬다.

기존에는 해당 클래스에서난 오류가만 ExceptionHandler가 잡아내서 오류를 만들었는데, 이제는 전역적으로 오류를 잡아낼 수 있게 되었다.


**@ControllerAdvice**
- `@ControllerAdvice`는 대상으로 지정한 여러 컨트롤러에 `@ExceptionHandler, @InitBinder` 기능을 부여해주는 역할을 한다.
- `@ControllerAdvice`에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다(글로벌 적용)
- `@RestControllerAdvice`는 `@ControllerAdvice`와 같고 `@ResponseBody`가 추가된 것이다.

**전역적으로 지원하고 싶지 않을 때**
패키지 혹은 특정 애노테이션, 혹은 특정 클래스만 `@ControllerAdvice` 를 지정해주고 싶은 경우.

```java
Target all Controllers annotated with @RestController

 @ControllerAdvice(annotations = RestController.class)
 public class ExampleAdvice1 {}

  Target all Controllers within specific packages

 @ControllerAdvice("org.example.controllers")
 public class ExampleAdvice2 {}

  Target all Controllers assignable to specific classes

 @ControllerAdvice(assignableTypes = {ControllerInterface.class,
 AbstractController.class})

public class ExampleAdvice3 {}
```

스프링 공식 문서 예제에서 보는 것 처럼 특정 애노테이션이 있는 컨트롤러를 지정할 수 있고, 특정 패키지를 직접 지정할 수도 있다. 패키지 지정의 경우 해당 패키지와 그 하위에 있는 컨트롤러가 대상이 된다. 그리고 특정 클래스를 지정할수도 있다.

예를 들어 상품과 관련된 컨트롤러의 예외처리, 주문과 관련된 컨트롤러의 예외처리를 따로해주고 싶은 경우, 애노테이션을 따로 만든다거나 패키지 명을 적어주면 된다.

**대상 컨트롤러 지정을 생략하면 모든 컨트롤러에 적용된다.**


**패키지 레벨로 적용시키기**
```java
@Slf4j  
@RestControllerAdvice(basePackages = "hello.exception.api")  
public class ExHandlerAdvice {  
  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(IllegalArgumentException.class)  
    public ErrorResult illegalArgExHandler(IllegalArgumentException e) {  
        log.error("[IllegalArgumentException] ex", e);  
        return new ErrorResult("Bad", e.getMessage());  
    }  
    /*보통은 이런 방식으로 사용할거같다 */    @ExceptionHandler  
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
```

**애노테이션으로 적용시키기**
```java
@Slf4j  
@RestControllerAdvice(annotations = BadReqEx.class)  
public class ExHandlerAdvice {  
  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(IllegalArgumentException.class)  
    public ErrorResult illegalArgExHandler(IllegalArgumentException e) {  
        log.error("[IllegalArgumentException] ex", e);  
        return new ErrorResult("Bad", e.getMessage());  
    }  
    /*보통은 이런 방식으로 사용할거같다 */    @ExceptionHandler  
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
```


**ModelAndView를 이용하여 HTML 페이지 렌더링하기**
```java
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
```
`UserException` 에러가 발생할 경우 해당 에러를 잡아서 userHandlerModelEx 메서드를 실행시켜준다. 그리고 setViewName에 경로를 설정해주면 뷰 템플릿의 에러 html을 찾아서 렌더링 해준다.



**정리**
`@ExceptinHandler, @ControllerAdvice`를 조합하면 예외를 깔끔하게 해결할 수 있다.
