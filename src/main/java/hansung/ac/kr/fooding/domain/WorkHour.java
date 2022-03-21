package hansung.ac.kr.fooding.domain;

import javax.persistence.Embeddable;

@Embeddable
public class WorkHour {
    private String open;
    private String close;
}
