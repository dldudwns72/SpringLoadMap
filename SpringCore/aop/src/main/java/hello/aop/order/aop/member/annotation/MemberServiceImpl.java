package hello.aop.order.aop.member.annotation;

import hello.aop.order.aop.member.ClassAop;
import hello.aop.order.aop.member.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService{

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "OK";
    }

    public String internal(String param){
        return "ok";
    }
}
