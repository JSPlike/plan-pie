package hanco.planpie.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("/movePage")
    public String movePage(@RequestParam("page") String page) {
        return page;
    }
}
