package hansung.ac.kr.fooding.handler;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class StringHandler {
    private StringTokenizer tokens;

    public List<String> tokenize(String input){
        List<String> tokenized = new ArrayList<>();
        tokens = new StringTokenizer(input, " .,-=", false);
        int count = tokens.countTokens();

        while(tokens.hasMoreTokens()){
            tokenized.add(tokens.nextToken());
        }

        for(int wordNum = 2; wordNum <= count; wordNum++){
            for(int i = 0; i <= count - wordNum; i++){
                StringBuilder sb = new StringBuilder();
                for(int j = i; j < wordNum + i; j++){
                    if(j!=i) sb.append(" ");
                    sb.append(tokenized.get(j));
                }
                tokenized.add(sb.toString());
            }
        }
        return tokenized;
    }
}