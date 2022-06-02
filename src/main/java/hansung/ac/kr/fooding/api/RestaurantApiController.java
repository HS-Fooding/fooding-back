package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.dtd.StructGetDTO;
import hansung.ac.kr.fooding.dto.menu.MenuGetDTO;
import hansung.ac.kr.fooding.dto.reservation.ReservAvailGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestInfoGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetWithLocDTO;
import hansung.ac.kr.fooding.service.MenuService;
import hansung.ac.kr.fooding.service.RestaurantService;
import hansung.ac.kr.fooding.service.SecurityService;
import hansung.ac.kr.fooding.service.StructService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {SwaggerConfig.API_RESTAURANT})
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/restaurant")
public class RestaurantApiController {
    private final RestaurantService restaurantService;
    private final StructService structService;
    private final MenuService menuService;
    private final SecurityService securityService;


    @ApiOperation("특정 매장 클릭")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getRestaurant(@PathVariable(value = "id") Long id) {
        RestInfoGetDTO restInfoGetDTO;
        try {
            Account account = securityService.getAccount();
            if (account == null) throw new SecurityException("Not Logged in");
            restInfoGetDTO = restaurantService.getRestaurantInfo(id, account);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(restInfoGetDTO, HttpStatus.OK);
    }

    @ApiOperation("전체 매장 리스트 검색")
    @RequestMapping(method = RequestMethod.GET)
    public Slice<Object> getRestaurants(
            @RequestParam(value = "coord", defaultValue = "false") String isCoord, Pageable pageable) {

        return restaurantService.getRestaurantList(isCoord, pageable);
    }

    @ApiOperation("매장 메뉴 조회")
    @RequestMapping(path = "/{id}/menu", method = RequestMethod.GET)
    public ResponseEntity getMenu(@PathVariable(value = "id") Long id) {
        List<MenuGetDTO> menuGetDTOList;
        try {
            menuGetDTOList = menuService.getMenuFromRestaurant(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(menuGetDTOList, HttpStatus.OK);
    }

    @ApiOperation("매장 구조 조회")
    @RequestMapping(path = "/{id}/structure", method = RequestMethod.GET)
    public ResponseEntity getStructure(@PathVariable(value = "id") Long id) {
        StructGetDTO structGetDTO;
        try {
            structGetDTO = restaurantService.getRestaurantStructure(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(structGetDTO, HttpStatus.OK);
    }

    @ApiOperation("매장 현재 이용 가능 테이블 조회")
    @RequestMapping(path = "/{restId}/table", method = RequestMethod.GET)
    public ResponseEntity getAvailTable(@PathVariable(value = "restId") Long restId) {
        ReservAvailGetDTO reservAvailGetDTO;
        try {
            reservAvailGetDTO = structService.getAvailTable(restId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservAvailGetDTO, HttpStatus.OK);
    }

    @ApiOperation("지역, 매장과 메뉴에 포함된 이름, 카테고리 검색 가능")
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity searchRestaurant(@RequestParam String keyword, Pageable pageable) {

        Slice<RestSimpleGetWithLocDTO> result;
        try {
            Account account = securityService.getAccount();
            if (account == null) throw new SecurityException("Not Logged in");
            result = restaurantService.searchByKeyword(keyword, pageable, account);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation("좌표에 대한 특정 반경 내에 있는 매장 리스트 반환")
    @RequestMapping(path = "/coord", method = RequestMethod.GET)
    public ResponseEntity getRestaurantsByCoord(@RequestParam Float x, @RequestParam Float y, Pageable pageable) {
        Slice<RestSimpleGetWithLocDTO> result;
        try {
            Account account = securityService.getAccount();
            if (account == null) throw new SecurityException("Not Logged in");
            result = restaurantService.getRestaurantByCoord(x, y, pageable, account);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}