package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.dto.ReservPostDTO;
import hansung.ac.kr.fooding.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurant/{restId}")
@RequiredArgsConstructor
public class ReservationApiController {
    private final ReservationService reservationService;

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

//    @RequestMapping(path = "/reservation/{reservId}", method =RequestMethod.GET){
//
//    }
}
