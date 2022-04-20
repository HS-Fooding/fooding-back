package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.dtd.StructGetDTO;
import hansung.ac.kr.fooding.dto.FloorDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestInfoGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestSimpleGetWithLocDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestaurantPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {
    private final SecurityService securityService;
    private final RestaurantRepository restaurantRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long save(RestaurantPostDTO postDTO) throws SecurityException {
        if (!(securityService.getAccount() instanceof Admin)) throw new SecurityException("No Authorization");
        Admin admin = (Admin) securityService.getAccount(); // TODO: 2022-03-28 Admin으로 수정 필요
        Restaurant restaurant = new Restaurant(postDTO, admin);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    @Transactional
    public Long saveWithImage(RestaurantPostDTO postDTO, List<MultipartFile> multipartImages) throws SecurityException {
        Account account = securityService.getAccount();
        if (!(account instanceof Admin)) throw new SecurityException("No Authorization");

        Restaurant restaurant = new Restaurant(postDTO, (Admin) account);
        restaurantRepository.save(restaurant);

        List<Image> images = ImageHandler.upload(multipartImages);

        System.out.println("111111111111111111111111111111");
        if (images != null) {
            System.out.println("22222222222222222222222222");
            imageRepository.saveImages(images);
            restaurant.addImages(images);
        }

        return restaurant.getId();
    }

    @Transactional
    public RestInfoGetDTO getRestaurantInfo(Long id) throws IllegalStateException {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if (optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optional.get();
        restaurant.plusViewCount();

        return RestInfoGetDTO.from(restaurant);
    }

    public RestInfoGetDTO getRestaurantInfoByName(String restName) throws IllegalStateException {
        Optional<Restaurant> optional = restaurantRepository.findByName(restName);
        if (optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optional.get();
        restaurant.setViewCount(restaurant.getViewCount() + 1);

        return RestInfoGetDTO.from(restaurant);
    }

    public StructGetDTO getRestaurantStructure(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optionalRestaurant.get();
        List<Floor> floors = restaurant.getFloors();
        if (floors == null) return null;
        List<FloorDTO> floorDTOS = FloorDTO.from(floors);
        StructGetDTO structGetDTO = new StructGetDTO();
        structGetDTO.setFloors(floorDTOS);
        return structGetDTO;
    }

    public Slice<Object> getRestaurantList(String isCoord, Pageable pageable) {
        Slice<Restaurant> page = restaurantRepository.findAllRest(pageable);
        Slice<Object> result;
        if (isCoord.equals("true")) {
            result = page.map(RestSimpleGetWithLocDTO::from);
        } else {
            result = page.map(RestSimpleGetDTO::from);
        }
        return result;
    }

    // 키워드로 검색
    public Slice<Object> searchByKeyword(String keyword, Pageable pageable) {
        // keyword - 지역, 음식점 이름, 메뉴, 카테고리 일 수 있음 && 여러 단어일 수도..
        String target = keyword.trim();
        String[] tokens = target.split(" ");
        Set<Long> result = new HashSet<>();

        for (String token : tokens) {
            // 지역
            result.addAll(restaurantRepository.findAllByRegion2Depth(target));
            result.addAll(restaurantRepository.findAllByRegion3Depth(target));
            // 매장 이름, 혹은 메뉴 이름
            result.addAll(restaurantRepository.findAllByName(target));
            result.addAll(restaurantRepository.findAllByMenu(target));
        }
        Slice<Restaurant> restaurants = restaurantRepository.findAllByIds(result, pageable);
        return restaurants.map(RestSimpleGetDTO::from);
    }
}
