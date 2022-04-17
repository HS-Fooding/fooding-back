package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.InitData;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Door;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.domain.structure.Wall;
import hansung.ac.kr.fooding.dto.FloorDTO;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Rollback(value = false)
@ExtendWith(MockitoExtension.class)
class StructServiceTest {
    @Autowired
    InitData initData;
    @Autowired
    StructService structService;
    @Autowired
    SecurityService securityService;
    @Autowired
    RestaurantRepository restaurantRepository;


    @Test
    @WithMockUser(username = "adminID")
    public void postStructTest() {
        //given
        Restaurant restaurant = restaurantRepository.findByName("restName").orElse(null);
        StructPostDTO structPostDTO = new StructPostDTO();
        List<FloorDTO> floorDTOS = new ArrayList<>();
        FloorDTO floorDTO = new FloorDTO();
        List<Door> doors = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        for(int i = 0 ; i < 3; i++){
            doors.add(new Door());
            walls.add(new Wall());
            tables.add(new Table());
        }
        floorDTO.setDoors(doors);
        floorDTO.setWalls(walls);
        floorDTO.setTables(tables);
        floorDTOS.add(floorDTO);
        structPostDTO.setFloors(floorDTOS);

        //when
        structService.postStruct(structPostDTO, restaurant.getId());
    }
}