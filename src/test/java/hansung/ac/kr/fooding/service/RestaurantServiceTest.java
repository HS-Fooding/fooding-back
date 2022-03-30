package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.WorkHour;
import hansung.ac.kr.fooding.dto.RestaurantPostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class RestaurantServiceTest {
    @Autowired
    RestaurantService restaurantService;
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
}