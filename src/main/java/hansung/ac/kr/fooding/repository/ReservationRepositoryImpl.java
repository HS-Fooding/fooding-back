package hansung.ac.kr.fooding.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hansung.ac.kr.fooding.dto.chart.ChartProjectionDTO;

import javax.persistence.EntityManager;
import java.time.LocalDate;
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
    public List<ChartProjectionDTO> search(Long restId, String start, String end) {
        return queryFactory
//                .select(restaurant, reservation, member)
                .select(Projections.constructor(ChartProjectionDTO.class,
                        reservation.id, reservation.reserveTime, reservation.reserveNum,
                        restaurant.weekdaysWorkHour, restaurant.weekendsWorkHour,
                        member
                ))
                .from(restaurant)
                .join(restaurant.reservations, reservation)
                .leftJoin(member).on(reservation.booker.member_id.eq(member.id))
                .where(
                        restaurant.id.eq(restId),
                        reservation.reserveDate.in(getDuration(start, end))
                )
                .fetch();
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
