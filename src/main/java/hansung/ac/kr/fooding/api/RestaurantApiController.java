package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Restaurant;
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
    private final RestaurantRepository restaurantRepository;
    private final MenuService menuService;

    @ApiOperation("특정 매장 검색")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getRestaurant(@PathVariable(value = "id") Long id){
        RestInfoGetDTO restInfoGetDTO;
        System.out.println("getRestaurant############");
        try {
            restInfoGetDTO = restaurantService.getRestaurantInfo(id);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("Fooding-Restaurant Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RestInfoGetDTO>(restInfoGetDTO, HttpStatus.OK);
    }

    @ApiOperation("전체 매장 리스트 검색")
    @RequestMapping(method = RequestMethod.GET)
    public Page getRestaurants(
            @RequestParam(value = "coord", defaultValue = "false") String isCoord ,Pageable pageable) {
        Page<Restaurant> page = restaurantRepository.findAll(pageable);
        Page result;
        if (isCoord.equals("true")){
            result = page.map(m -> RestSimpleGetWithLocDTO.from(m));
        } else {
            result = page.map(m -> RestSimpleGetDTO.from(m));
        }
        return result;
    }

    @ApiOperation("매장 메뉴 조회")
    @RequestMapping(path = "/{id}/menu", method = RequestMethod.GET)
    public ResponseEntity getMenu(@PathVariable(value = "id") Long id){
        List<MenuGetDTO> menuGetDTOList;
        try {
            menuGetDTOList = menuService.getMenuFromRestaurant(id);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("Fooding-"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<MenuGetDTO>>(menuGetDTOList, HttpStatus.OK);
    }
}
