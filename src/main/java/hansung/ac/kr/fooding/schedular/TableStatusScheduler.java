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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TableStatusScheduler {
    private final ReservationRepository reservationRepository;

    private Map<LocalDateTime, List<Table>> handleTables = new HashMap<>();

    @Transactional
    public void adminAddReservation(Reservation reservation){
        LocalDateTime localDateTime = LocalDateTime.parse(String.format("%sT%s",reservation.getReserveDate(), reservation.getReserveTime()));
        LocalDateTime parsedAfterDateTime = localDateTime.plusMinutes((long)reservation.getRestaurant().getMaximumUsageTime());
        LocalDateTime parsedNowDateTime = getParsedNowDateTime();

        if(parsedAfterDateTime.isAfter(parsedNowDateTime)){
            List<Table> insertedTables;
            Table table = reservation.getTable();
            if(!handleTables.containsKey(parsedAfterDateTime))
                insertedTables = new ArrayList<Table>();
            else
                insertedTables = handleTables.get(parsedAfterDateTime);
            insertedTables.add(table);
            table.setAvailable(false);
        }
    }

    @Transactional
    public void adminDeleteReservation(Reservation reservation){
        LocalDateTime localDateTime = LocalDateTime.parse(String.format("%sT%s",reservation.getReserveDate(), reservation.getReserveTime()));
        LocalDateTime parsedNowDateTime = getParsedNowDateTime();
        if(localDateTime.isAfter(parsedNowDateTime))
            return;
        LocalDateTime parsedAfterDateTime = localDateTime.plusMinutes((long)reservation.getRestaurant().getMaximumUsageTime());

        List<Table> insertedTables = handleTables.get(parsedAfterDateTime);
        Table table = reservation.getTable();

        if (insertedTables == null)
            return;
        insertedTables.remove(table);
        table.setAvailable(true);
    }



    @Transactional
    @Scheduled(cron = "0 0/30 * * * *")
    public void updateTableStatus() {
        LocalDateTime parsedNowDateTime = getParsedNowDateTime();
        String nowDate = parsedNowDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nowTime = parsedNowDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

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

    public LocalDateTime getParsedNowDateTime(){
        LocalDateTime nowDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        int hour = nowDateTime.getHour();
        int minute = nowDateTime.getMinute();
        if(minute < 30)
            minute = 0;
        else
            minute = 30;
        String nowDate = String.format("%d-%02d-%02d", nowDateTime.getYear(), nowDateTime.getMonth().getValue(), nowDateTime.getDayOfMonth());
        String nowTime = String.format("%02d:%02d", hour, minute);
        return LocalDateTime.parse(String.format("%sT%s",nowDate, nowTime));
    }
}
