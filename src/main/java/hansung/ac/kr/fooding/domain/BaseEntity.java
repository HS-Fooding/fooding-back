package hansung.ac.kr.fooding.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity{
    private String createdBy;
    private String lasModifiedBy;
}
