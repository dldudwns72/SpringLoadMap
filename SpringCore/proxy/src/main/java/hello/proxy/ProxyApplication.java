package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v1_proxy.ConcreteProxyConfig;
import hello.proxy.config.v1_proxy.InterfaceProxyConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import hello.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import hello.proxy.config.v5_autoproxy.AutoProxyConfig;
import hello.proxy.config.v6_aop.AopConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class,AppV2Config.class}) // 스프링 빈으로 등록한다.
//@Import(InterfaceProxyConfig.class) // 스프링 빈으로 등록한다.
//@Import(ConcreteProxyConfig.class) // 스프링 빈으로 등록한다.
//@Import(DynamicProxyBasicConfig.class) // 스프링 빈으로 등록한다.
//@Import(DynamicProxyFilterConfig.class) // 스프링 빈으로 등록한다.
//@Import(ProxyFactoryConfigV1.class) // 스프링 빈으로 등록한다.
//@Import(ProxyFactoryConfigV2.class) // 스프링 빈으로 등록한다.
//@Import(BeanPostProcessorConfig.class)
//@Import(AutoProxyConfig.class)
@Import(AopConfig.class)
//주의, app과 app 하위에서만 componentScan 을 진행하도록 설정 AppV1Config는 따로 빈으로 인식하기 위해
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace(){
		return new ThreadLocalLogTrace();
	}
}
