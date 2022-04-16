package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.ReservPostDTO;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
