package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetWithLocDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestaurantPostDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebAppConfiguration
@SpringBootTest
class RestaurantServiceTest {
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    GeocodeService geocodeService;

    @Test
    @WithMockUser(username = "testadmin", roles = "ADMIN")
    public void saveTest() throws Exception {
        //given
        Location location = geocodeService.getGeocode("경기도 파주시 교하로 100");
        System.out.println(location.toString());
        List<String> tel = new ArrayList<String>();
        tel.add("1234");
        tel.add("3456");
        WorkHour weekday = new WorkHour("1", "1");
        WorkHour weekend = new WorkHour("1","1");
        // RestaurantPostDTO dto = new RestaurantPostDTO("TestRestaurnat", tel, weekday, weekend, "intro", null, location, null);
        //when
        //restaurantService.save(dto);

        //then
    }

    @Test
    public void getRestaurantList() throws Exception {
        // 3. 관리자의 매장 등록
        Location addressResult = Location.builder()
                .addressName("경기도 파주시 교하로 100")
                .region1Depth("경기도")
                .region2Depth("파주시")
                .region3Depth("목동동")
                .roadName("교하로")
                .buildingNo("16")
                .coordinate(new Coordinate(127.095f, 37.5035f)).build();

        RestaurantPostDTO restaurantPostDTO = new RestaurantPostDTO("restName2", List.of("01011"), new WorkHour("1", "2"), new WorkHour("3", "4")
                , "Parking here", "intro", addressResult, null, 3.6f);

        Account adminAccount = accountRepository.findByIdentifier("adminID");

        Restaurant restaurant = new Restaurant(restaurantPostDTO, (Admin) adminAccount);
        restaurantRepository.save(restaurant);

        PageRequest pageRequest = PageRequest.of(0, 3,
                Sort.by(Sort.Direction.DESC, "name"));

        Slice<Object> result = restaurantService.getRestaurantList("", pageRequest);
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    public void searchByKeyword() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 3,
                Sort.by(Sort.Direction.DESC, "name"));

        Slice<RestSimpleGetWithLocDTO> result = restaurantService.searchByKeyword("re", pageRequest);
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    public void searchByCoord() {
        PageRequest pageRequest = PageRequest.of(0, 3,
                Sort.by(Sort.Direction.DESC, "name"));

        Slice<RestSimpleGetWithLocDTO> result = restaurantService.getRestaurantByCoord(127.095f, 37.5035f, pageRequest);
        assertThat(result.getContent().size()).isEqualTo(2);
    }
}