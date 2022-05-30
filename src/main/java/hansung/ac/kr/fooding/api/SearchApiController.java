package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {SwaggerConfig.API_SEARCH})
@RestController
@RequiredArgsConstructor
public class SearchApiController {
    private final SearchService searchService;

    @ApiOperation(value = "검색어 추천")
    @RequestMapping(path="/search", method = RequestMethod.POST)
    public List<String> getRecommend(String query){
        List<String> recommends = searchService.getRecommend(query);
        return recommends;
    }
}
