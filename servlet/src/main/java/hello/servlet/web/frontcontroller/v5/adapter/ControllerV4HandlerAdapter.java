package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;

        Map<String,String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;

    }

    private Map<String,String> createParamMap(HttpServletRequest request){
        Map<String,String> paramMap = new HashMap<>();
        // 요청 들어온것에서 반복하여 key값으로 "이름"을 넣어주고 String 값을 배열로 넣어준다. request에 파라미터를 다 뽑아서 넣어준다고 생각
        // username = "lee", age = "20'
        request.getParameterNames().asIterator().forEachRemaining(paramName -> paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }

}
