package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0(){
        login1();
        login2();
    }

    private void login1(){
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}",resultTime);
    }

    private void login2(){
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}",resultTime);
    }

    /**
     * 템플릿 메서드 패턴 적용
     */
    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    /**
     * 익명 내부 클래스를 이용한 객체 선언 시 override
     */
    @Test
    void templateMethodV2(){
        AbstractTemplate template1 = new SubClassLogic1(){
            @Override
            protected void call(){
                log.info("로직 1");
            }
        };
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic1(){
            @Override
            protected void call(){
                log.info("로직 2");
            }
        };
        template2.execute();
    }


}
