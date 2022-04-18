package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.ReservPostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Reservation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "tableasd")
    private Table table;
    private String reserveDate;
    private String reserveTime;
    private int reserveNum;
    @ManyToOne
    private Member member;
    private boolean isCar;

    public Reservation(ReservPostDTO dto,Table table,  Member member){
        this.table = table;
        reserveDate = dto.getReserveDate();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        this.member = member;
        this.isCar = dto.isCar();
    }
}
