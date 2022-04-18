package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.InitData;
import hansung.ac.kr.fooding.dto.ReservAvailGetDTO;
import hansung.ac.kr.fooding.dto.ReservPostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class ReservationServiceTest {
    @Autowired ReservationService reservationService;
    @Autowired InitData initData;

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
    public void deleteReservationTest(){
        reservationService.deleteReservation(2L,1L);
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
}