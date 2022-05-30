package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Token;
import hansung.ac.kr.fooding.handler.StringHandler;
import hansung.ac.kr.fooding.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final StringHandler stringHandler;
    private final TokenRepository tokenRepository;

    @Transactional
    public void saveSearch(String input){
        List<String> strings = stringHandler.tokenize(input);
        List<Token> findTokens = tokenRepository.getTokens(strings);

        for(Token token : findTokens){
            if(strings.contains(token.getString())) {
                token.count();
                strings.remove(token.getString());
            }
        }

        List<Token> newTokens = Token.from(strings);
        tokenRepository.saveAll(newTokens);
    }

    public List<String> getRecommend(String input){
        int num = 9;
        List<Token> findTokens = tokenRepository.findByStringContains(input);
        Collections.sort(findTokens);

        if (num > findTokens.size()){
            num = findTokens.size();
        }

        List<Token> slicedTokens = findTokens.subList(0, num);
        List<String> strings = new ArrayList<>();

        slicedTokens.forEach(token -> strings.add(token.getString()));
        return strings;
    }
}
