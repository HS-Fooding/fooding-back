package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Booker;
import hansung.ac.kr.fooding.dtd.ReservStructGetDTO;
import hansung.ac.kr.fooding.dto.reservation.*;
import hansung.ac.kr.fooding.dto.TableDTO;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    EntityManager em;

    @Test
    @WithMockUser(username = "userID")
    public void postReservationTest() throws Exception {
        //given
        ReservPostDTO dto = new ReservPostDTO();
        dto.setCar(true);
        dto.setTableNum("2");
        dto.setReserveDate("1997-06-05");
        dto.setReserveTime("15:05");
        dto.setReserveNum(10);
        //when
        reservationService.postReservation(dto, 2L);
        //then
    }

    @Test
    @WithMockUser(username = "userID")
    public void deleteReservationTest() {
        reservationService.deleteReservation(1L);
    }

    @Test
    public void getAvailableReservationTest() throws Exception {
        //given
        Long restId = 1L;
        String date = "1997-06-05";
        String time = "10:00";
        int num = 2;
        //when
        ReservAvailGetDTO availableReservation = reservationService.getAvailableReservation(restId, date, time, num);

        //then
        System.out.println(availableReservation);
    }

    @Test
    public void myTest() {
        //given
        Long restId = 1L;
        String date = "1997-06-05";
        String time = "10:00";
        int num = 3;

        // when
        ReservStructGetDTO availableStruct = reservationService.getAvailableReservation2(restId, date, time, num);
        ReservFloorDTO reservFloorDTO = availableStruct.getFloors().get(0);
        assertThat(reservFloorDTO.getTables().size()).isEqualTo(3);
        assertThat(reservFloorDTO.getDoors().size()).isEqualTo(0);

        List<TableDTO> tables = reservFloorDTO.getTables();
        TableDTO table1 = tables.get(0);
        TableDTO table2 = tables.get(1);
        TableDTO table3 = tables.get(2);

        assertThat(table1.getTableNum()).isEqualTo("Num0");
        assertThat(table2.getTableNum()).isEqualTo("Num1");
        assertThat(table3.getTableNum()).isEqualTo("Num2");


        assertThat(table1.getCanReserv()).isFalse();
        assertThat(table2.getCanReserv()).isTrue();
        assertThat(table3.getCanReserv()).isFalse();
    }

    @Test
    public void getTodayRestReservations() throws Exception {
        //given
        String date = "2022-05-01";
        //when
        AdminReservGetDTO todayRestReservations = reservationService.getRestReservations(2L, date);
        //then
        System.out.println(todayRestReservations.toString());
    }

    @Test
    @Transactional
    @WithMockUser(username = "manager2026")
    public void adminPostReservation(){
        System.out.println("##################"+SecurityContextHolder.getContext().getAuthentication().getName());
        Long restId = 35L;
        AdminReservPostDTO adminReservPostDTO = new AdminReservPostDTO();
        adminReservPostDTO.setTableNum("1");
        adminReservPostDTO.setReserveDate("2022-05-24");
        adminReservPostDTO.setReserveTime("15:30");
        adminReservPostDTO.setReserveNum(2);
        adminReservPostDTO.setBooker(null);
        adminReservPostDTO.setCar(true);
        reservationService.adminPostReservation(restId, adminReservPostDTO);
    }
}

