package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.WorkHour;
import hansung.ac.kr.fooding.domain.enumeration.AdminReservStatus;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dtd.ReservStructGetDTO;
import hansung.ac.kr.fooding.dto.*;
import hansung.ac.kr.fooding.dto.chart.ChartProjectionDTO;
import hansung.ac.kr.fooding.dto.chart.ChartResultDTO;
import hansung.ac.kr.fooding.dto.chart.ResultDTO;
import hansung.ac.kr.fooding.dto.mypage.ReservationsDTO;
import hansung.ac.kr.fooding.dto.reservation.*;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.TableRepository;
import hansung.ac.kr.fooding.schedular.TableStatusScheduler;
import hansung.ac.kr.fooding.var.CError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private final TableStatusScheduler tableStatusScheduler;

    @Transactional
    public Long postReservation(ReservPostDTO dto, Long restId) throws IllegalStateException {
        Member member = (Member) securityService.getAccount();
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        Table table = tableRepository.findTableByRestIdAndTableNum(restaurant.getId(), dto.getTableNum()).get(0);
        Reservation reservation = new Reservation(dto, table, member);
        restaurant.addReservation(reservation);
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    public void adminPostReservation(Long restId, AdminReservPostDTO adminReservPostDTO){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optionalRestaurant.get();
        if(!securityService.isRestaurantAdmin(restaurant)) throw new SecurityException(CError.USER_NOT_ADMIN_OF_REST.getMessage());

        Table table = tableRepository.findTableByRestIdAndTableNum(restaurant.getId(), adminReservPostDTO.getTableNum()).get(0);
        Reservation reservation = new Reservation(table, adminReservPostDTO);
        restaurant.addReservation(reservation);
        reservationRepository.save(reservation);
        tableStatusScheduler.adminAddReservation(reservation);
    }

    public void adminDeleteReservation(Long restId, AdminReservPostDTO adminReservPostDTO){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optionalRestaurant.get();
        if(!securityService.isRestaurantAdmin(restaurant)) throw new SecurityException(CError.USER_NOT_ADMIN_OF_REST.getMessage());

        Optional<Reservation> optionalReservation = reservationRepository.findById(adminReservPostDTO.getReservId());
        if(optionalReservation.isEmpty()) throw new IllegalStateException(CError.RESERV_NOT_FOUND.getMessage());
        Reservation reservation = optionalReservation.get();

        tableStatusScheduler.adminDeleteReservation(reservation);
        restaurant.getReservations().remove(reservation);
        reservationRepository.delete(reservation);
    }

    @Transactional
    public void deleteReservation(Long reserveId) throws IllegalStateException, SecurityException {
        Member member = (Member) securityService.getAccount();

//        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
//        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Fooding-Restaurant Not Found");
//        Restaurant restaurant = optionalRestaurant.get();

        Optional<Reservation> optionalReservation = reservationRepository.findById(reserveId);
        if (optionalReservation.isEmpty()) throw new IllegalStateException("Fooding-Reservation Not Found");
        Reservation reservation = optionalReservation.get();

        if (!Objects.equals(reservation.getBooker().getMember_id(), member.getId())) throw new SecurityException("Fooding-Not Reservation Owner");

        reservation.getRestaurant().getReservations().remove(reservation);
        reservationRepository.delete(reservation);
    }

    @Transactional
    public ReservAvailGetDTO getAvailableReservation(Long restId, String date, String time, int num) {
        if (time.equals("undefined")) return null;
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
        for (int i = -checkTime + 1; i < checkTime; i++) {
            int res = minute + 30 * i;
            String resString = fromMinutesToHHmm(res);
            unavailableTables.addAll(tableRepository.findUnavailByRestIdWithDateAndTime(restId, num, date, resString));
        }

        tables = tableRepository.findByRestIdWithNum(restId, num);
        tables.removeAll(unavailableTables);
        tables.removeIf(table -> !table.isAvailable());
        reservAvailGetDTO.setTables(tables);
        return reservAvailGetDTO;
    }

    @Transactional
    public ReservStructGetDTO getAvailableReservation2(Long restId, String date, String time, int num) {
        Restaurant restaurant;
        float maximumUsageTime;
        Set<TableDTO> unavailableTables;

        // ?????? ??????????????? ?????? (?????? ?????? ?????? ??????)
        restaurant = restaurantRepository.findResById(restId);

        maximumUsageTime = restaurant.getMaximumUsageTime();
        int checkTime = (int) maximumUsageTime / 30;
        String[] strs = time.split(":");
        int minute = Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);

        List<Floor> floors = restaurant.getFloors();
        if (floors == null) throw new IllegalStateException("Fooding-Restaurant have no floor");

        List<Reservation> reservations = restaurant.getReservations();

        // unavailable??? ???????????? ????????? ???????????? ??????
        List<Reservation> unavailableReservations = reservations.stream().filter(m -> m.getReserveDate().equals(date) &&
            (Integer.parseInt(m.getReserveTime().split(":")[0]) * 60 + Integer.parseInt(m.getReserveTime().split(":")[1]) >= minute - maximumUsageTime &&
            Integer.parseInt(m.getReserveTime().split(":")[0]) * 60 + Integer.parseInt(m.getReserveTime().split(":")[1]) <= minute + maximumUsageTime)
        ).collect(Collectors.toList()); // ?????? ???????????? ????????? ?????????

        unavailableTables = unavailableReservations.stream().map(u -> new TableDTO(u.getTable())).collect(Collectors.toSet()); // ??? ????????? ???????????? ??????

        List<ReservFloorDTO> floordto = ReservFloorDTO.from(floors);

        // ???????????? ?????? ??? ????????? ?????? ???????????? ??????????????? ????????? ?????? ??????????????? ???????????? ???
        floordto.forEach((f ->
                f.getTables().forEach(t -> {
                    if(t.getMaxPeople() < num || t.getMinPeople() > num) t.setCanReserv(false);
                    unavailableTables.forEach(u -> {
                        if (u.getTableNum().equals(t.getTableNum()))
                            t.setCanReserv(false);
                    });
                })
        )); // ?????? ????????? ????????????, ???????????? ???????????? ??????

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
    public AdminReservGetDTO getRestReservations(Long restId, String date) throws IllegalStateException{
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findWithFloorsById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optionalRestaurant.get();

        AdminTableInfoDTO adminTableInfoDTO = AdminTableInfoDTO.from(restaurant, date);

        List<AdminReservDTO> adminReservDTOs = new ArrayList<>();

        List<Reservation> reservations = reservationRepository.findByReserveDate(restId, adminTableInfoDTO.getDate());

        reservations.forEach(m -> adminReservDTOs.add(AdminReservDTO.from(m)));
        AdminReservGetDTO adminReservGetDTO = new AdminReservGetDTO(adminTableInfoDTO, adminReservDTOs);
        return adminReservGetDTO;
    }


    public void editReservation(Long restId, Long reservId, ReservPostDTO reservPostDTO) {
        if(!securityService.isLogined()) throw new SecurityException(CError.USER_NOT_LOGIN.getMessage());
        Member member = (Member)securityService.getAccount();
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservId);
        if(optionalReservation.isEmpty()) throw new IllegalStateException(CError.RESERV_NOT_FOUND.getMessage());
        Reservation reservation = optionalReservation.get();

        if(reservation.getBooker().getMember_id() != member.getId()) throw new IllegalStateException(CError.USER_NOT_BOOKER_OF_RESERV.getMessage());
        List<Table> findTables = tableRepository.findTableByRestIdAndTableNum(restId, reservPostDTO.getTableNum());
        if(findTables.isEmpty()) throw new IllegalStateException(CError.TABLE_NOT_FOUND.getMessage());
        Table findTable = findTables.get(0);
        reservation.edit(findTable, reservPostDTO);
    }

    public void adminEditReservation(Long restId, Long reservId, AdminReservPostDTO adminReservPostDTO) throws SecurityException{
        if(!securityService.isLogined()) throw new SecurityException(CError.USER_NOT_LOGIN.getMessage());
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optionalRestaurant.get();
        if(!securityService.isRestaurantAdmin(restaurant)) throw new SecurityException(CError.USER_NOT_ADMIN_OF_REST.getMessage());
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservId);
        if(optionalReservation.isEmpty()) throw new IllegalStateException(CError.RESERV_NOT_FOUND.getMessage());
        Reservation reservation = optionalReservation.get();

        List<Table> findTables = tableRepository.findTableByRestIdAndTableNum(restId, adminReservPostDTO.getTableNum());
        if(findTables.isEmpty()) throw new IllegalStateException(CError.TABLE_NOT_FOUND.getMessage());
        Table findTable = findTables.get(0);

        tableStatusScheduler.adminDeleteReservation(reservation);
        reservation.edit(findTable, adminReservPostDTO);
        tableStatusScheduler.adminAddReservation(reservation);
    }

    @Transactional
    public void adminEditReservations(Long restId, List<AdminReservUpdateDTO> reservUpdateDTOs)
            throws IllegalStateException, SecurityException{
        for(AdminReservUpdateDTO dto : reservUpdateDTOs){
            AdminReservStatus flag = dto.getFlag();
            AdminReservPostDTO adminReservPostDTO = dto.getAdminReservPostDTO();
            switch(flag){
                case EDIT:
                    adminEditReservation(restId, dto.getAdminReservPostDTO().getReservId(), adminReservPostDTO);
                    break;
                case NEW:
                    adminPostReservation(restId, adminReservPostDTO);
                    break;
                case DELETE:
                    adminDeleteReservation(restId, adminReservPostDTO);
            }
        }
    }

    public ResultDTO getChart(Long restId, String start, String end) {
        List<ChartProjectionDTO> data = reservationRepository.getChart(restId, start, end);
        List<ChartResultDTO> collect = data.stream().map(ChartResultDTO::new).collect(Collectors.toList());
        WorkHour workingTime = restaurantRepository.findWorkingHourById(restId);

        return new ResultDTO<>(collect, workingTime);
    }

    public List<ReservationsDTO> getReservationsById(Long memberId) {
        List<Reservation> reservations = reservationRepository.findByBookerId(memberId);
        return reservations.stream().map(ReservationsDTO::new).collect(Collectors.toList());
    }
}
