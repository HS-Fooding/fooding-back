package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.repository.TokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
@Rollback(value = true)
class SearchServiceTest {
    @Autowired
    SearchService searchService;
    @Autowired
    TokenRepository tokenRepository;

    @Test
    void saveSearch() {
        //given
        String string = "뽤똫야ㅃ피 찌뜽아얼 1삐ㅑㄲ얔.";
        String string1 = "뽤똫야ㅃ피 찌뜽아얼";
        String string2 = "뽤똫야ㅃ피";

        //when
        searchService.saveSearch(string);
        searchService.saveSearch(string1);
        searchService.saveSearch(string2);

        //then
        Assertions.assertThat(tokenRepository.findAll().size() == 6).isTrue();
        Assertions.assertThat(tokenRepository.findByString(string2).getCount()).isEqualTo(3);
    }

    @Test
    void getRecommend(){
        //given
        String string = "뽤똫야ㅃ피 찌뜽아얼 1삐ㅑㄲ얔.";
        String string1 = "뽤똫야ㅃ피 찌뜽아얼";
        String string2 = "뽤똫야ㅃ피";

        //when
        searchService.saveSearch(string);
        searchService.saveSearch(string1);
        searchService.saveSearch(string2);

        //then
        System.out.println(searchService.getRecommend(string2));
        Assertions.assertThat(searchService.getRecommend(string2).size()).isEqualTo(3);
    }
}