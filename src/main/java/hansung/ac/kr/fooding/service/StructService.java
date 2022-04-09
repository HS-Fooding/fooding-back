package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.dto.FloorDTO;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.repository.FloorRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.StructureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StructService {
    private final SecurityService securityService;
    private final RestaurantRepository restaurantRepository;
    private final FloorRepository floorRepository;
    private final StructureRepository structureRepository;

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

}
