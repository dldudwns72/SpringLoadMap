package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * ModelView는 render할 jsp파일명과 request 값으로 만든 model을 가지고 있다.
 */
public class ModelView {
    private String viewName;
    private Map<String,Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
