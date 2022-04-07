package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.InitData;
import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Door;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.domain.structure.Wall;
import hansung.ac.kr.fooding.dto.FloorPostDTO;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @WithMockUser(username = "testIdentifier")
    @Transactional
    public void postStructTest() {
        //given
        Restaurant restaurant = restaurantRepository.findByName("restName");
        StructPostDTO structPostDTO = new StructPostDTO();
        List<FloorPostDTO> floorPostDTOs = new ArrayList<>();
        FloorPostDTO floorPostDTO = new FloorPostDTO();
        List<Door> doors = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        for(int i = 0 ; i < 3; i++){
            doors.add(new Door());
            walls.add(new Wall());
            tables.add(new Table());
        }
        floorPostDTO.setDoors(doors);
        floorPostDTO.setWalls(walls);
        floorPostDTO.setTables(tables);
        floorPostDTOs.add(floorPostDTO);
        structPostDTO.setFloors(floorPostDTOs);

        //when
        structService.postStruct(structPostDTO, restaurant.getId());
    }
}