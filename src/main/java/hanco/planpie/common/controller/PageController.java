package hanco.planpie.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class PageController {

    @GetMapping("/movePage")
    public String movePage(@RequestParam("page") String page) {

        log.info("================movePage ===== > " + page);
        return page;
    }

    @GetMapping("/")
    public String goHome(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // 로그인된 사용자가 있을 경우 모델에 사용자 정보 추가
        if (userDetails != null) {
            log.info("============username: " + userDetails.getUsername());
            model.addAttribute("username", userDetails.getUsername());
        }
        model.addAttribute("isHome", true);

        return "home";
    }

    @GetMapping("/user/login")
    public String login() {
        return "user/login";  // 로그인 페이지 템플릿
    }

    @GetMapping("/user/register")
    public String register() {
        return "user/register";  // 로그인 페이지 템플릿
    }

    @GetMapping("/dashboard/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";  // 로그인 페이지 템플릿
    }

    @GetMapping("/dashboard/kanbanboard")
    public String kanbanboard() {
        return "dashboard/kanbanboard";  // 로그인 페이지 템플릿
    }
}
