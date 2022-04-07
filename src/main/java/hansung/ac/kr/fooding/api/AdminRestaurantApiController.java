package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.dto.MenuPostDTO;
import hansung.ac.kr.fooding.dto.RestaurantPostDTO;
import hansung.ac.kr.fooding.dto.StructPostDTO;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.MenuService;
import hansung.ac.kr.fooding.service.RestaurantService;
import hansung.ac.kr.fooding.service.StructService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @RequestMapping(path="/{restId}/structure", method = RequestMethod.POST)
    public ResponseEntity postStructure(@PathVariable(value = "restId") Long restId,
                                        @RequestBody StructPostDTO structPostDTO){
        try{
            structService.postStruct(structPostDTO, restId);
        } catch (Exception e){

        }
        return null;
    }
}
