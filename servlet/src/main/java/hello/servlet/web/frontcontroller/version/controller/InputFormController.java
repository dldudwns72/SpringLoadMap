package hello.servlet.web.frontcontroller.version.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.version.Controller;

import java.util.Map;

public class InputFormController implements Controller {

    @Override
    public ModelView process(Map<String,String> paramMap) {
        return new ModelView("new-form");
    }

}
