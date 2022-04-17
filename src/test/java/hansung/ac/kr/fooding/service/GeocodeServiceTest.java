package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GeocodeServiceTest {
    GeocodeService geocodeService = new GeocodeService();

    @Test
    public void getGeocode() {
        //given

        String wrongAddress = "경기도 파주시 교하로";
        String wrongAddress1 = "경기도 파주시";
        String address = "경기도 파주시 교하로 100";

        //when
        Location result1 = geocodeService.getGeocode(wrongAddress);
        Location result2 = geocodeService.getGeocode(wrongAddress1);
        Location result3 = geocodeService.getGeocode(address);


        //then
        System.out.println(result3.toString());
        assertThat(result1 == null).isTrue();
        assertThat(result2 == null).isTrue();
        assertThat(result3 == null).isFalse();
    }

    @Test
    public void getGecodeTest2() throws Exception {
        // given
        String oldAddress = "서울특별시 성북구 석관동 10 두산아파트";
        String roadAddress = "서울특별시 성북구 화랑로48길 16, 110동 1201호";

        // when
        Location result1 = geocodeService.getGeocode(oldAddress);
        Location result2 = geocodeService.getGeocode(roadAddress);

        // then
        System.out.println(result1);
        System.out.println(result2);
    }
}