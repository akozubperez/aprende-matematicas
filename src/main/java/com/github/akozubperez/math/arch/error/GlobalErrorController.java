package com.github.akozubperez.math.arch.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public String index() {
        return "/#/error";
    }

    @Override
    public String getErrorPath() {
        return "/#/error";
    }
}