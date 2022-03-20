package hansung.ac.kr.fooding.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    // 테스트 해보고 지우시오!
    @GetMapping("/test")
    public String test() {
        return "test!!";
    }
}
