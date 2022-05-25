package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.FloorDTO;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.dto.reservation.ReservAvailGetDTO;
import hansung.ac.kr.fooding.repository.*;
import hansung.ac.kr.fooding.var.CError;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StructService {
    private final SecurityService securityService;
    private final RestaurantRepository restaurantRepository;
    private final FloorRepository floorRepository;
    private final StructureRepository structureRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    @Transactional
    public void postStruct(StructPostDTO structPostDTO, Long restId) throws SecurityException, IllegalStateException {
        Account account = securityService.getAccount();
        if (account instanceof Member) throw new SecurityException("Account Not Admin");
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        if (restaurant.getAdmin() != (Admin) account) throw new SecurityException("Account Not Admin of Restaurant");

        List<FloorDTO> floorDTOS = structPostDTO.getFloors();
        if (floorDTOS == null) return;
        if (!restaurant.getFloors().isEmpty()) restaurant.deleteFloors();
        for (int i = 0; i < floorDTOS.size(); i++) {
            FloorDTO floorDTO = floorDTOS.get(i);
            Floor floor = new Floor();
            floor.setFloor(i + 1);
            floor.addStructures(floorDTO.getTables());
            floor.addStructures(floorDTO.getDoors());
            floor.addStructures(floorDTO.getSeats());
            floor.addStructures(floorDTO.getWalls());
            floor.addStructures(floorDTO.getWindows());

            structureRepository.saveAll(floor.getStructures());
            floorRepository.save(floor);
            restaurant.addFloor(floor);
        }
    }

    public ReservAvailGetDTO getAvailTable(Long restId){
        Restaurant restaurant;
        List<Table> tables;
        ReservAvailGetDTO reservAvailGetDTO = new ReservAvailGetDTO();

        Optional<Restaurant> optional = restaurantRepository.findById(restId);
        if (optional.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        tables = tableRepository.findByRestId(restId);
        reservAvailGetDTO.setTables(tables);
        return reservAvailGetDTO;
    }

    public ReservAvailGetDTO toggleTableAvailable(Long restId, String tableNum){
        Optional<Restaurant> optional = restaurantRepository.findById(restId);
        if (optional.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        if (!securityService.isRestaurantAdmin(optional.get())) throw new SecurityException(CError.USER_NOT_ADMIN_OF_REST.getMessage());

        List<Table> findTables = tableRepository.findTableByRestIdAndTableNum(restId, tableNum);
        if (findTables.isEmpty()) throw new IllegalStateException(CError.TABLE_NOT_FOUND.getMessage());
        Table table = findTables.get(0);
        table.setAvailable(!table.isAvailable());

        ReservAvailGetDTO reservAvailGetDTO = new ReservAvailGetDTO();
        List<Table> tables = tableRepository.findByRestId(restId);
        reservAvailGetDTO.setTables(tables);
        return reservAvailGetDTO;
    }
}
