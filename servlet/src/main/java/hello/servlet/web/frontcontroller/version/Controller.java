package hello.servlet.web.frontcontroller.version;

import hello.servlet.web.frontcontroller.ModelView;
import java.util.Map;

public interface Controller {

    ModelView process(Map<String,String> paramMap);

}
