package hansung.ac.kr.fooding.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StringHandlerTest {
    @Autowired
    StringHandler stringHandler;

    @Test
    void tokenize() {
        //given
        String str = "안녕하세요 저는 임경익입니다.";
        List<String> strs;

        //when
        strs = stringHandler.tokenize(str);

        //then
        System.out.println("#######" + strs);
        Assertions.assertThat(strs.size() == 6).isTrue();
    }
}