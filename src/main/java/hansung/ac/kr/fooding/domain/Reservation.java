package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.reservation.AdminReservPostDTO;
import hansung.ac.kr.fooding.dto.reservation.ReservPostDTO;
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
    @JoinColumn(name = "table_id")
    private Table table;
    private String reserveDate;
    private String reserveTime;
    private int reserveNum;
    @Embedded
    private Booker booker;
    private boolean isCar;

    public Reservation(ReservPostDTO dto,Table table,  Member member){
        this.table = table;
        reserveDate = dto.getReserveDate();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        booker = Booker.from(member);
        this.isCar = dto.isCar();
    }

    public Reservation(Table table, AdminReservPostDTO dto) {
        this.table = table;
        reserveDate = dto.getReserveDate();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        booker = dto.getBooker();
        this.isCar = dto.isCar();
    }

    public void edit(Table table, ReservPostDTO dto){
        this.table = table;
        reserveDate = dto.getReserveDate();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        booker = dto.getBooker();
        isCar = dto.isCar();
    }

    public void edit(Table table, AdminReservPostDTO dto){
        this.table = table;
        reserveDate = dto.getReserveDate();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        booker = dto.getBooker();
        isCar = dto.isCar();

    }
}
