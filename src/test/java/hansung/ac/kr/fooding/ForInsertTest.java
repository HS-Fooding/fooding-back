package hansung.ac.kr.fooding;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.exception.C_IllegalStateException;
import hansung.ac.kr.fooding.manager.RestaurantManager;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import hansung.ac.kr.fooding.repository.TableRepository;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback(value = false)
public class ForInsertTest {
    @Autowired
    RestaurantManager restaurantManager;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TableRepository tableRepository;

    @Test
    @Transactional
    @WithMockUser(username = "userId1")
    public void insert() throws C_IllegalStateException {
        Table table = tableRepository.findTableByRestIdAndTableNum(1L, "Num0").get(0);
        Account userAccount1 = accountRepository.findByIdentifier("userId1");
        Restaurant restaurant1 = restaurantManager.getRestaurant(1L);
        Reservation reservation1 = new Reservation();
        reservation1.setReserveDate("1997-06-05");
        reservation1.setReserveTime("10:00");
        reservation1.setCar(true);
        reservation1.setTable(table);
        reservation1.setReserveNum(2);
        reservation1.setBooker(Booker.from((Member)userAccount1));
        restaurant1.addReservation(reservation1);
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setReserveDate("1997-06-05");
        reservation2.setReserveTime("14:00");
        reservation2.setCar(true);
        reservation2.setTable(table);
        reservation2.setReserveNum(2);
        reservation2.setBooker(Booker.from((Member) userAccount1));
        restaurant1.addReservation(reservation2);
        reservationRepository.save(reservation2);
    }
}
