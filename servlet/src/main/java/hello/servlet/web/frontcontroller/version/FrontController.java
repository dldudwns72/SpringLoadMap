package hello.servlet.web.frontcontroller.version;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.version.controller.InputFormController;
import hello.servlet.web.frontcontroller.version.controller.SavePageController;
import hello.servlet.web.frontcontroller.version.controller.ViewPageController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontController",urlPatterns = "/front-controller/version/*")
public class FrontController extends HttpServlet {

    private Map<String,Controller> controllerMap = new HashMap<>();

    public FrontController(){
        controllerMap.put("/front-controller/version/members/new-form", new InputFormController());
        controllerMap.put("/front-controller/version/members/save", new SavePageController());
        controllerMap.put("/front-controller/version/members", new ViewPageController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewName = request.getRequestURI();

        Controller controller = controllerMap.get(viewName);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String,String> paramMap = createParamMap(request);

        ModelView modelView = controller.process(paramMap);

        MyView myView = viewResolver(modelView.getViewName());

        myView.render(modelView.getModel(),request,response);

    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName -> paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName){
        String viewPage =  "/WEB-INF/views/" + viewName +".jsp";
        return new MyView(viewPage);
    }

}
