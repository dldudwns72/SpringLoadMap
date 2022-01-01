package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
        log.info("targetClass = {} ", target.getClass());
        log.info("proxyClass = {} ", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 해당 객체가 proxy인지 여부 확인
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue(); // 해당 객체가 dynamicproxy 인지 여부 확인
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse(); // 해당 객체가 cglibproxy 인지 여부 확인
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy(){
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService)proxyFactory.getProxy();
        log.info("targetClass = {} ", target.getClass());
        log.info("proxyClass = {} ", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 해당 객체가 proxy인지 여부 확인
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse(); // 해당 객체가 dynamicproxy (인터페이스 기반 Proxy) 인지 여부 확인
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue(); // 해당 객체가 cglibproxy (구체클래스 기반 Proxy) 인지 여부 확인
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        // 인터페이스 기반이지만 실제로 ServiceImpl 구체클래스를 상속받아 CGLIB를 이용한 Proxy 설정
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
        log.info("targetClass = {} ", target.getClass());
        log.info("proxyClass = {} ", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}
