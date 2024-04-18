package ac.su.springmvcinclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ForwardRedirectController {
    @GetMapping("/ex-forward-from")
    // 1번째 엔드포인트로 수신한 요청 처리를 위해서 다른 Handler 가 필요한 경우
    public String forwardFrom() {
        return "forward:/ex-forward-to";  // 다른 Handler 로 보내줌!
    }

    @GetMapping("/ex-forward-to")
    @ResponseBody
    public String forwardTo() {
        return "forwarded to /ex-forward-to";
        // Client 의 시점에서는 1개의 요청에 대해 1개의 응답을 수신한 결과
    }

    @GetMapping("/ex-redirect-from")
    public String redirectFrom() {
        return "redirect:/ex-redirect-to";  // Spring 에서 리턴 구문을 해석 후, 알아서 상태 및 헤더 처리
        // redirect 의 경우, 클라이언트의 협조가 반드시 필요 (클라이언트에 대한 명령으로 작용하는 응답)
        // redirection 이 너무 많으면, 서비스 전달에 불리할 뿐만 아니라, 설계적으로 문제가 있음
        // -> Web 서버 또는 Browser 상에서 Redirection 횟수에 대한 제한 설정이 존재함 (오동작 or 해킹 우려)
    }

    @GetMapping("/ex-redirect-to")
    @ResponseBody
    public String redirectTo() {
        return "redirected to /ex-redirect-to";
        // 독립적으로 Client 요청을 받음
    }
}
