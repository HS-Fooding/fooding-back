package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.ReservAvailGetDTO;
import hansung.ac.kr.fooding.dto.ReservPostDTO;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final SecurityService securityService;

    @Transactional
    public Long postReservation(ReservPostDTO dto, Long restId) throws IllegalStateException{
        Member member = (Member)securityService.getAccount();
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        Table table = tableRepository.findTableByTableNum(dto.getTableNum(), restaurant.getId()).get(0);
        Reservation reservation = new Reservation(dto, table, member);
        restaurant.addReservation(reservation);
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Transactional
    public void deleteReservation(Long restId, Long reservId) throws IllegalStateException, SecurityException{
        Member member = (Member)securityService.getAccount();

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservId);
        if(optionalReservation.isEmpty()) throw new IllegalStateException("Fooding-Reservation Not Found");
        Reservation reservation = optionalReservation.get();

        if(reservation.getMember() != member) throw new SecurityException("Fooding-Not Reservation Owner");

        restaurant.getReservations().remove(reservation);
        reservationRepository.delete(reservation);
    }

    @Transactional
    public ReservAvailGetDTO getAvailableReservation(Long restId, String date, String time, int num){
        Restaurant restaurant;
        float maximumUsageTime;
        List<Table> tables;
        Set<Table> unavailableTables = new HashSet<>();
        ReservAvailGetDTO reservAvailGetDTO = new ReservAvailGetDTO();

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        restaurant = optionalRestaurant.get();

        maximumUsageTime = restaurant.getMaximumUsageTime();

        int checkTime = (int)maximumUsageTime /30;
        String[] strs = time.split(":");
        int minute = Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);
        for(int i = -checkTime; i <= checkTime; i++){
            int res = minute + 30 * i;
            String resString = fromMinutesToHHmm(res);
            unavailableTables.addAll(tableRepository.findUnavailByRestIdWithDateAndTime(restId, num, date, resString));
        }

        tables = tableRepository.findByRestIdWithNum(restId, num);
        tables.removeAll(unavailableTables);
        reservAvailGetDTO.setTables(tables);
        return reservAvailGetDTO;
    }

    private static String fromMinutesToHHmm(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, remainMinutes);
    }
}
