package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.service.GeocodeService;
import hansung.ac.kr.fooding.var.CError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = {SwaggerConfig.API_GEOCODE})
@RestController
@RequestMapping(path = "/geocode")
@RequiredArgsConstructor
public class GeocodeApiController {
    private final GeocodeService geocodeService;

    @ApiOperation(value = "주소 -> 좌표")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity getGeocode(@RequestBody String address){
        Location location = geocodeService.getGeocode(address);
        if (location == null) return new ResponseEntity<String>(CError.GEOCODE_NOT_FOUND.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @ApiOperation(value = "좌표 -> 주소")
    @RequestMapping(path = "/address", method = RequestMethod.POST)
    public ResponseEntity getAddrFromGeocode(@RequestBody List<Double> coord){
        String region = geocodeService.getRegion(coord);
        if (region == null) return new ResponseEntity<String>(CError.REGION_NOT_FOUND.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<String>(region, HttpStatus.OK);
    }
}
