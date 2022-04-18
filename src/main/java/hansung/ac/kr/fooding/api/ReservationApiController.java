package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.dto.ReservAvailGetDTO;
import hansung.ac.kr.fooding.dto.ReservPostDTO;
import hansung.ac.kr.fooding.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {SwaggerConfig.API_RESERVATION})
@RestController
@RequestMapping(path = "/restaurant/{restId}")
@RequiredArgsConstructor
public class ReservationApiController {
    private final ReservationService reservationService;

    @ApiOperation("예약 진행")
    @RequestMapping(path = "/reservation", method = RequestMethod.POST)
    public ResponseEntity postReservation(@PathVariable(value = "restId") Long restId,
                                          @RequestBody ReservPostDTO reservPostDTO){
        Long id;
        try {
            id = reservationService.postReservation(reservPostDTO, restId);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    
    @ApiOperation("예약 취소")
    @RequestMapping(path = "/reservation/{reservId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteReservation(@PathVariable(value="restId") Long restId,
                                            @PathVariable(value = "reservId") Long reservId){
        try{
            reservationService.deleteReservation(restId, reservId);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/reservation", method =RequestMethod.GET)
    public ResponseEntity getAvailReservation(@PathVariable(value = "restId") Long restId,
                                              @RequestParam(value = "date") String date,
                                              @RequestParam(value = "time") String time,
                                              @RequestParam(value = "num") int num){
        ReservAvailGetDTO reservAvailGetDTO;
        try{
            reservAvailGetDTO = reservationService.getAvailableReservation(restId, date, time, num);
        } catch (Exception e){

        }
        return null;
    }
}
