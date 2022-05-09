package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.dtd.StructGetDTO;
import hansung.ac.kr.fooding.dto.menu.MenuGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestInfoGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetWithLocDTO;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.MenuService;
import hansung.ac.kr.fooding.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = {SwaggerConfig.API_RESTAURANT})
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/restaurant")
public class RestaurantApiController {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    private final MenuService menuService;

    @ApiOperation("지역, 매장과 메뉴에 포함된 이름, 카테고리 검색 가능")
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public Slice<RestSimpleGetWithLocDTO> searchRestaurant(@RequestParam String keyword, Pageable pageable) {

        return restaurantService.searchByKeyword(keyword, pageable);
    }

    @ApiOperation("특정 매장 클릭")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getRestaurant(@PathVariable(value = "id") Long id) {
        RestInfoGetDTO restInfoGetDTO;
        try {
            restInfoGetDTO = restaurantService.getRestaurantInfo(id);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>("Fooding-Restaurant Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RestInfoGetDTO>(restInfoGetDTO, HttpStatus.OK);
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
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<MenuGetDTO>>(menuGetDTOList, HttpStatus.OK);
    }

    @ApiOperation("매장 구조 조회")
    @RequestMapping(path = "/{id}/structure", method = RequestMethod.GET)
    public ResponseEntity getStructure(@PathVariable(value = "id") Long id) {
        StructGetDTO structGetDTO;
        try {
            structGetDTO = restaurantService.getRestaurantStructure(id);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<StructGetDTO>(structGetDTO, HttpStatus.OK);
    }

    @ApiOperation("좌표에대한 특정 반경 내에 있는 매장 리스트 반환")
    @RequestMapping(path = "/coord", method = RequestMethod.GET)
    public Slice<RestSimpleGetDTO> getRestaurantsByCoord(@RequestParam Float x, @RequestParam Float y, Pageable pageable) {
        return restaurantService.getRestaurantByCoord(x, y, pageable);
    }
}