package hansung.ac.kr.fooding.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hansung.ac.kr.fooding.dto.searchCondition.SearchCond;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hansung.ac.kr.fooding.domain.QMember.member;
import static hansung.ac.kr.fooding.domain.QReservation.reservation;
import static hansung.ac.kr.fooding.domain.QRestaurant.restaurant;


public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReservationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    // member는 ..?
    public List<Tuple> search(Long restId, SearchCond condition) {
        return queryFactory
                .select(restaurant, reservation)
                .from(restaurant) // theta join - 연관관계가 없는 두 테이블을 조인
                .join(restaurant.reservations, reservation)
                .where(
                        restaurant.id.eq(restId),
                        reservation.reserveDate.in(getDuration(condition.getStartDate(), condition.getEndDate())),
                        // favor ??
                        getReserveNum(condition.getReserveNum()),
                        getReserveTime(condition.getStartTime(), condition.getEndTime()),
                        getCar(condition.getIsCar())
                )
                .fetch();
    }

//    member.job.in(condition.getJob()),
//    getAge(condition.getAge()),
//    getSex(condition.getSex()),

    public BooleanExpression getCar(Boolean car) {
        if (car == null) return null;
        return car ? reservation.isCar.isTrue() : reservation.isCar.isFalse();
    }

    public BooleanExpression getReserveTime(String start, String end) {
        if (start == null || end == null) return null;
        String[] startSplit = start.split(":");
        String[] endSplit = end.split(":");

        int startHour = Integer.parseInt(startSplit[0]);
        int endHour = Integer.parseInt(endSplit[0]);

        List<String> times = new ArrayList<>();
        for (int i = startHour; i <= endHour; i++) {
            times.add(String.format("%02d:00", i));
            times.add(String.format("%02d:30", i));
        }
        return reservation.reserveTime.in(times);
    }

    public BooleanExpression getReserveNum(Integer num) {
        return num != null ? reservation.reserveNum.eq(num) : null;
    }

    public BooleanExpression getSex(Boolean sex) {
        if (sex == null) return null;
        return sex ? member.sex.eq(true) : member.sex.eq(false);
    }

    public BooleanExpression getAge(Integer age) {
        // reservation에서 사용자의 정보를 어떻게 참조?
        if (age == null) return null;
        else if (age < 20) return member.age.between(0, 20);
        else if (age < 30) return member.age.between(20, 30);
        else if (age < 40) return member.age.between(30, 40);
        else if (age < 50) return member.age.between(40, 50);
        else return member.age.gt(50);
    }

    private List<String> getDuration(String start, String end) {
        String[] startSplit = start.split("-");
        String[] endSplit = end.split("-");

        int startYear = Integer.parseInt(startSplit[0]);
        int startMonth = Integer.parseInt(startSplit[1]);
        int startDay = Integer.parseInt(startSplit[2]);

        int endYear = Integer.parseInt(endSplit[0]);
        int endMonth = Integer.parseInt(endSplit[1]);
        int endDay = Integer.parseInt(endSplit[2]);

        LocalDate _startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate _endDate = LocalDate.of(endYear, endMonth, endDay);

        List<LocalDate> dates = _startDate.datesUntil(_endDate).collect(Collectors.toList());
        dates.add(_endDate); // 마지막날 포함
        return dates.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
