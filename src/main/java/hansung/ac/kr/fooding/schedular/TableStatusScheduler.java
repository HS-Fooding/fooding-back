package hansung.ac.kr.fooding.schedular;

import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TableStatusScheduler {
    private final ReservationRepository reservationRepository;

    private Map<LocalDateTime, List<Table>> handleTables = new HashMap<>();

    @Transactional
    @Scheduled(cron = "0 0/30 * * * *")
    public void updateTableStatus() {
        LocalDateTime nowDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        int hour = nowDateTime.getHour();
        int minute = nowDateTime.getMinute();
        if(minute < 30)
            minute = 0;
        else
            minute = 30;
        String nowDate = String.format("%d-%02d-%02d", nowDateTime.getYear(), nowDateTime.getMonth().getValue(), nowDateTime.getDayOfMonth());
        String nowTime = String.format("%02d:%02d", hour, minute);

        LocalDateTime parsedNowDateTime = LocalDateTime.parse(String.format("%sT%s",nowDate, nowTime));

        List<Table> findTables = handleTables.get(parsedNowDateTime);
        if(findTables != null){
            for(Table table : findTables){
                if (!table.isAvailable())
                    table.setAvailable(true);
            }
            handleTables.remove(parsedNowDateTime);
        }

        List<Reservation> startReservations = reservationRepository.findByDateAndTime(nowDate, nowTime);
        for(Reservation reservation : startReservations){
            List<Table> insertedTables;
            LocalDateTime localDateTime = LocalDateTime.parse(String.format("%sT%s",reservation.getReserveDate(), reservation.getReserveTime()));
            LocalDateTime parsedAfterDateTime = localDateTime.plusMinutes((long)reservation.getRestaurant().getMaximumUsageTime());
            Table table = reservation.getTable();

            if(!handleTables.containsKey(parsedAfterDateTime))
                insertedTables = new ArrayList<Table>();
            else
                insertedTables = handleTables.get(parsedAfterDateTime);

            insertedTables.add(table);
            table.setAvailable(false);
        }
    }
}
