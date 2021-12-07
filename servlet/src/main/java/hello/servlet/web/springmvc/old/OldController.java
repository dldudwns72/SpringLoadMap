package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/springmvc/old-controller") // spring bean을 등록하여 Handler Mapping이 해당 빈을 찾아서 호출한다.
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Old Controller ");
        // springboot에서 application.properties에서 prefix,suffix 설정을 해주면 자동으로 viewResolver 등록됨.
        // springboot에서 지원해주는걸 사용하지 않을 시 InternalResourceViewResolver를 통하여 prefix,suffix를 등록하여 사용하면 된다.
        return new ModelAndView("new-form");
    }

}
