package hansung.ac.kr.fooding.service;

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

    @Test
    @WithMockUser(username = "userID")
    public void postReservationTest() throws Exception {
        //given
        ReservPostDTO dto = new ReservPostDTO();
        dto.setCar(true);
        dto.setTableNum("2");
        dto.setReserveDate("2022-04-18");
        dto.setReserveTime("15:05:33");
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
}