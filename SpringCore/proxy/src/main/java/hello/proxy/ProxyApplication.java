package hello.proxy;

import hello.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV1Config.class) // AppV1Config.class 를 스프링 빈으로 등록한다.
//주의, app과 app 하위에서만 componentScan 을 진행하도록 설정 AppV1Config는 따로 빈으로 인식하기 위해
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
