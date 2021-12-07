package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    // urlPattenrs에 설정한 url로 해당 클래스가 생성이 되며 URL과 일치하는 구현체 설정
    public FrontControllerServletV3() {
        controllerV3Map.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 요청 URL을 통한 Controller 구현체 선택
        ControllerV3 controller = controllerV3Map.get(requestURI);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String,String> paramMap = createParamMap(request); // username : "lee", age = "20"

        ModelView modelView = controller.process(paramMap);

        String viewName = modelView.getViewName();// 논리 이름 가져온다.

        // /WEB-INF/view/new-form.jsp"
        MyView myView = viewResolver(viewName);

        myView.render(modelView.getModel(), request,response);

    }

    // 요청이 들어온 값에 대해 Map 반환형으로 파라미터명,파라미터값 설정
    private Map<String,String> createParamMap(HttpServletRequest request){
        Map<String,String> paramMap = new HashMap<>();
        // 요청 들어온것에서 반복하여 key값으로 "이름"을 넣어주고 String 값을 배열로 넣어준다. request에 파라미터를 다 뽑아서 넣어준다고 생각
        // username = "lee", age = "20'
        request.getParameterNames().asIterator().forEachRemaining(paramName -> paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/view/" + viewName + ".jsp");
    }


}
