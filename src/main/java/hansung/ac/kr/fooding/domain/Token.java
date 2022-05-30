package hansung.ac.kr.fooding.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Token implements Comparable<Token>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String string;
    private int count = 1;

    public Token(String str){
        this.string = str;
    }

    public void count(){
        count++;
    }

    public static List<Token> from(List<String> input){
        List<Token> tokens = new ArrayList<>();
        input.forEach(i -> tokens.add(new Token(i)));
        return tokens;
    }

    @Override
    public int compareTo(Token o) {
        String oString = o.string;
        if (string.length() < oString.length()){
            return -1;
        } else if (string.length() > oString.length()){
            return 1;
        } else if (count > o.count) {
            return -1;
        } else if (count < o.count) {
            return 1;
        }
        return 0;
    }
}
