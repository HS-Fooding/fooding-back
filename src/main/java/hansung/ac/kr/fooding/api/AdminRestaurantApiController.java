package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.dto.MenuPostDTO;
import hansung.ac.kr.fooding.dto.RestaurantPostDTO;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.MenuService;
import hansung.ac.kr.fooding.service.RestaurantService;
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

    @ApiOperation(value = "매장 등록")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity postRestaurant(@RequestPart RestaurantPostDTO dto,
                                         @RequestPart(value = "image", required = false) List<MultipartFile> images){
        try {
            restaurantService.save(dto);
        } catch (SecurityException e){
            return new ResponseEntity<String>("Fooding-Not Admin", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "메뉴 등록")
    @RequestMapping(path = "/{id}/menu", method = RequestMethod.POST)
    public ResponseEntity postMenu(@PathVariable(value = "id") Long id,
                                   @RequestPart(value = "menu") MenuPostDTO menuPostDTO,
                                   @RequestPart(required = false) MultipartFile image){
        try {
            menuService.addMenu(menuPostDTO, image, id);
        } catch (IllegalStateException e){
            return new ResponseEntity<String>("Fooding-"+e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<String>("Fooding-"+e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }
}
