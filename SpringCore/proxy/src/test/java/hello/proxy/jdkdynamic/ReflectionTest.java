package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA(); //호출하는 메서드가 다름
        log.info("result = {} ", result1);

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {} ", result2);

    }

    @Test
    void reflectionTest1() throws Exception {
        // 클래스 메타 정보 획득
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA 메서드 정보
        // 획득한 클래스 메타정보에서 문자로 메서드를 찾을 수 있다.
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}",result1);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}",result2);
    }

    @Test
    void ReflectionTest2() throws Exception{
        // 클래스 메타 정보 획득
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA,target);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB,target);
    }

    private void dynamicCall(Method method,Object target) throws Exception{
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}",result);
    }


    @Slf4j
    static class Hello{
       public String callA(){
           log.info("Call A");
           return "A";
       }

        public String callB(){
            log.info("Call B");
            return "B";
        }
    }
}
