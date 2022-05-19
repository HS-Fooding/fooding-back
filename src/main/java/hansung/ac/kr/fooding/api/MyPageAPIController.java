package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.dto.mypage.ReservationsDTO;
import hansung.ac.kr.fooding.service.ReservationService;
import hansung.ac.kr.fooding.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {SwaggerConfig.API_MYPAGE})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mypage")
public class MyPageAPIController {
    private final ReservationService reservationService;
    private final SecurityService securityService;

    @ApiOperation(value = "예약 리스트")
    @RequestMapping(path="/reservation", method = RequestMethod.GET)
    public ResponseEntity reservationList() {
        List<ReservationsDTO> result;
        try {
            Member account = (Member) securityService.getAccount();
            if (account == null) throw new SecurityException("Not Logged in");
            result = reservationService.getReservationsById(account.getId());
        } catch (Exception e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "예약 취소")
    @RequestMapping(path = "/reservation/{reserveId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteReservation(@PathVariable(value = "reserveId") Long reserveId){
        try{
            reservationService.deleteReservation(reserveId);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "즐겨찾기 추가")
    @RequestMapping(path = "/bookmark/{restId}", method = RequestMethod.POST)
    public ResponseEntity addBookmark(@PathVariable(value = "restId") Long restId) {
       return null;
    }
}
