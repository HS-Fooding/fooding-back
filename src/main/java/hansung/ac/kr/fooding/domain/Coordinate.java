package hansung.ac.kr.fooding.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@RequiredArgsConstructor
public class Coordinate {
    private int x;
    private int y;
}
