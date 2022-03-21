package hansung.ac.kr.fooding.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
