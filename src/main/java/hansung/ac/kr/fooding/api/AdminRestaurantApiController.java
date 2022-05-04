package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.dto.menu.MenuPostDTO;
import hansung.ac.kr.fooding.dto.reservation.AdminReservGetDTO;
import hansung.ac.kr.fooding.dto.reservation.AdminReservUpdateDTO;
import hansung.ac.kr.fooding.dto.reservation.ReservPostDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestaurantPostDTO;
import hansung.ac.kr.fooding.service.MenuService;
import hansung.ac.kr.fooding.service.ReservationService;
import hansung.ac.kr.fooding.service.RestaurantService;
import hansung.ac.kr.fooding.service.StructService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {SwaggerConfig.API_ADMIN_RESTAURANT})
@RestController
@RequestMapping(path = "/admin/restaurant")
@RequiredArgsConstructor
public class AdminRestaurantApiController {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final StructService structService;
    private final ReservationService reservationService;

    @ApiOperation(value = "매장 등록")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity postRestaurant(@RequestPart(value = "restaurant") RestaurantPostDTO dto,
                                         @RequestPart(value = "image", required = false) List<MultipartFile> images) {
        Long id;
        try {
            id = restaurantService.saveWithImage(dto, images);
        } catch (SecurityException e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

//    @ApiOperation(value = "매장 사진 추가")
//    @RequestMapping(method = RequestMethod.POST, path = "/{restId}/image")
//    public ResponseEntity postImage(@PathVariable(value = "restId") Long restId,
//                                    @RequestPart(value ="image") MultipartFile image){
//        try{
//            restaurantService
//        }catch (Exception e){
//
//        }
//        return ResponseEntity.ok().build();
//    }

    @ApiOperation(value = "메뉴 등록")
    @RequestMapping(path = "/{id}/menu", method = RequestMethod.POST)
    public ResponseEntity postMenu(@PathVariable(value = "id") Long id,
                                   @RequestPart(value = "menu") MenuPostDTO menuPostDTO,
                                   @RequestPart(required = false) MultipartFile image) {
        try {
            menuService.addMenu(menuPostDTO, image, id);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value="메뉴 삭제")
    @RequestMapping(path = "/{restId}/menu/{menuId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMenu(@PathVariable(value = "restId") Long restId,
                                     @PathVariable(value = "menuId") Long menuId){
        try {
            menuService.deleteMenu(restId, menuId);
        } catch(IllegalStateException e){
            return new ResponseEntity<String>("Fooding-"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "매장 구조 생성")
    @RequestMapping(path="/{restId}/structure", method = RequestMethod.POST)
    public ResponseEntity postStructure(@PathVariable(value = "restId") Long restId,
                                        @RequestBody StructPostDTO structPostDTO){
        try{
            structService.postStruct(structPostDTO, restId);
        } catch (Exception e){
            return new ResponseEntity<String>("Fooding-"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "매장 예약 확인")
    @RequestMapping(path="/{restId}/reservation", method = RequestMethod.GET)
    public ResponseEntity getReservations(@PathVariable(value = "restId") Long restId,
                                          @RequestParam String date){
        AdminReservGetDTO adminReservGetDTO;
        try {
            adminReservGetDTO = reservationService.getRestReservations(restId, date);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<AdminReservGetDTO>(adminReservGetDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "매장 엑셀 관리")
    @RequestMapping(path="/{restId}/reservation", method = RequestMethod.POST)
    public ResponseEntity postReservations(@PathVariable(value = "restId") Long restId,
                                           @RequestBody List<AdminReservUpdateDTO> adminReservUpdateDTOs){
        try {
            reservationService.adminEditReservations(restId, adminReservUpdateDTOs);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

//    @RequestMapping(path = "/{restId}/reservation/{reservId}")
//    public ResponseEntity editReservation(@PathVariable(value = "restId") Long restId,
//                                          @PathVariable(value = "reservId") Long reservId,
//                                          @RequestBody ReservPostDTO reservPostDTO){
//        try {
//            reservationService.editReservation(restId, reservId, reservPostDTO);
//        } catch (Exception e){
//
//        }
//        return null;
//    }
}
