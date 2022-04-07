package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Structure;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.FloorPostDTO;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.repository.FloorRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.StructureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StructService {
    private final SecurityService securityService;
    private final RestaurantRepository restaurantRepository;
    private final FloorRepository floorRepository;
    private final StructureRepository structureRepository;

    public void postStruct(StructPostDTO structPostDTO, Long restId) throws SecurityException{
        Account account = securityService.getAccount();
        if(account instanceof Member) throw new SecurityException("Account Not Admin");
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        if(optionalRestaurant.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        if(restaurant.getAdmin() != (Admin)account) throw new SecurityException("Account Not Admin of Restaurant");

        List<FloorPostDTO> floorPostDTOs = structPostDTO.getFloors();
        if(floorPostDTOs == null) return;
        for(int i = 0 ; i <floorPostDTOs.size(); i++){
            FloorPostDTO floorPostDTO = floorPostDTOs.get(i);
            Floor floor = new Floor();
            floor.setFloor(i+1);
            floor.addStructures(floorPostDTO.getTables());
            floor.addStructures(floorPostDTO.getDoors());
            floor.addStructures(floorPostDTO.getSeats());
            floor.addStructures(floorPostDTO.getWalls());
            floor.addStructures(floorPostDTO.getWindows());

            structureRepository.saveAll(floor.getStructures());
            floorRepository.save(floor);
            restaurant.addFloor(floor);
        }
    }
}
