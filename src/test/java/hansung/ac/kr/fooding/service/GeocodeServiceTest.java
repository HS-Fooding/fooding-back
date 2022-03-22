package hansung.ac.kr.fooding.service;

import org.junit.jupiter.api.Test;
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
        Map result1 = geocodeService.getGeocode(wrongAddress);
        Map result2 = geocodeService.getGeocode(wrongAddress1);
        Map result3 = geocodeService.getGeocode(address);


        //then
        System.out.println(result1.toString());
        System.out.println(result2.toString());
        System.out.println(result3.toString());
        assertThat(result1.isEmpty()).isTrue();
        assertThat(result2.isEmpty()).isTrue();
        assertThat(result3.isEmpty()).isFalse();
    }
}