package com.advise_clothes.project_advise_clothes;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
//    public String handleError() {
//        return "index.html";
//    }
    public String handleError() {
        return "삐빅 실패입니다.";
    }

}
