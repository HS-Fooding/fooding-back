package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.structure.Table;

import javax.persistence.*;

@Entity
public class Reservation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "tableasd")
    private Table Table;
    private String reserveDate;
    private String reserveTime;
    private int reserveNum;
    @ManyToOne
    private Member member;
    private boolean isCar;
}
