package hansung.ac.kr.fooding.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@RequiredArgsConstructor
public class Location {
    private String city;
    private String province;
    private String street;
    private Coordinate coordinate;
}
