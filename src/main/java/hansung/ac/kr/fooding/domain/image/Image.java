package hansung.ac.kr.fooding.domain.image;

import hansung.ac.kr.fooding.domain.BaseEntity;

import javax.persistence.*;

//@MappedSuperclass
@Entity
public class Image extends BaseEntity {
    @Id @GeneratedValue
    private long id;
    private String path;
}
