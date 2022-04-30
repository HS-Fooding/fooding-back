package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dtd.ReservStructGetDTO;
import hansung.ac.kr.fooding.dto.*;
import hansung.ac.kr.fooding.dto.reservation.*;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final SecurityService securityService;

    @Transactional
    public Long postReservation(ReservPostDTO dto, Long restId) throws IllegalStateException {
        Member member = (Member) securityService.getAccount();
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        Table table = tableRepository.findTableByTableNum(dto.getTableNum(), restaurant.getId()).get(0);
        Reservation reservation = new Reservation(dto, table, member);
        restaurant.addReservation(reservation);
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Transactional
    public void deleteReservation(Long restId, Long reservId) throws IllegalStateException, SecurityException {
        Member member = (Member) securityService.getAccount();

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservId);
        if (optionalReservation.isEmpty()) throw new IllegalStateException("Fooding-Reservation Not Found");
        Reservation reservation = optionalReservation.get();

        if (reservation.getMember() != member) throw new SecurityException("Fooding-Not Reservation Owner");

        restaurant.getReservations().remove(reservation);
        reservationRepository.delete(reservation);
    }

    @Transactional
    public ReservAvailGetDTO getAvailableReservation(Long restId, String date, String time, int num) {
        Restaurant restaurant;
        float maximumUsageTime;
        List<Table> tables;
        Set<Table> unavailableTables = new HashSet<>();
        ReservAvailGetDTO reservAvailGetDTO = new ReservAvailGetDTO();

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        restaurant = optionalRestaurant.get();

        maximumUsageTime = restaurant.getMaximumUsageTime();

        int checkTime = (int) maximumUsageTime / 30;
        String[] strs = time.split(":");
        int minute = Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);
        for (int i = -checkTime; i <= checkTime; i++) {
            int res = minute + 30 * i;
            String resString = fromMinutesToHHmm(res);
            unavailableTables.addAll(tableRepository.findUnavailByRestIdWithDateAndTime(restId, num, date, resString));
        }

        tables = tableRepository.findByRestIdWithNum(restId, num);
        tables.removeAll(unavailableTables);
        reservAvailGetDTO.setTables(tables);
        return reservAvailGetDTO;
    }

    @Transactional
    public ReservStructGetDTO getAvailableReservation2(Long restId, String date, String time, int num) {
        Restaurant restaurant;
        float maximumUsageTime;
        Set<TableDTO> unavailableTables;

        // 해당 레스토랑을 찾음 (예약 정보 페치 조인)
        restaurant = restaurantRepository.findResById(restId);

        maximumUsageTime = restaurant.getMaximumUsageTime();
        int checkTime = (int) maximumUsageTime / 30;
        String[] strs = time.split(":");
        int minute = Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);

        List<Floor> floors = restaurant.getFloors();
        if (floors == null) throw new IllegalStateException("Fooding-Restaurant have no floor");

        List<Reservation> reservations = restaurant.getReservations();

        // unavailable한 조건들을 가지는 예약들을 추출
        List<Reservation> unavailableReservations = reservations.stream().filter(m -> m.getReserveDate().equals(date) &&
            (Integer.parseInt(m.getReserveTime().split(":")[0]) * 60 + Integer.parseInt(m.getReserveTime().split(":")[1]) >= minute - maximumUsageTime &&
            Integer.parseInt(m.getReserveTime().split(":")[0]) * 60 + Integer.parseInt(m.getReserveTime().split(":")[1]) <= minute + maximumUsageTime)
        ).collect(Collectors.toList()); // 예약 불가능한 테이블 정보들

        unavailableTables = unavailableReservations.stream().map(u -> new TableDTO(u.getTable())).collect(Collectors.toSet()); // 층 정보는 구분하지 않음

        List<ReservFloorDTO> floordto = ReservFloorDTO.from(floors);

        // 테이블의 인원 수 체크는 예약 정보에서 시작하는게 아니라 전체 테이블에서 체크해야 함
        floordto.forEach((f ->
                f.getTables().forEach(t -> {
                    if(t.getMaxPeople() < num || t.getMinPeople() > num) t.setCanReserv(false);
                    unavailableTables.forEach(u -> {
                        if (u.getTableNum().equals(t.getTableNum()))
                            t.setCanReserv(false);
                    });
                })
        )); // 예약 가능한 테이블과, 불가능한 테이블을 구분

        ReservStructGetDTO result = new ReservStructGetDTO();
        result.setFloors(floordto);

        return result;
    }

    private static String fromMinutesToHHmm(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, remainMinutes);
    }

    @Transactional
    public AdminReservGetDTO getTodayRestReservations(Long restId) throws IllegalStateException{
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findWithFloorsById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        AdminTableInfoDTO adminTableInfoDTO = AdminTableInfoDTO.from(restaurant);
        List<AdminReservDTO> adminReservDTOs = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findByReserveDate(adminTableInfoDTO.getDate());
        reservations.forEach(m -> adminReservDTOs.add(AdminReservDTO.from(m)));
        AdminReservGetDTO adminReservGetDTO = new AdminReservGetDTO(adminTableInfoDTO, adminReservDTOs);
        return adminReservGetDTO;
    }
}
