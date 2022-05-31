package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Member;
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
import hansung.ac.kr.fooding.var.CError;
import lombok.RequiredArgsConstructor;
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
    private final SearchService searchService;

    @Transactional
    public Long save(RestaurantPostDTO postDTO) throws SecurityException {
        if (!(securityService.getAccount() instanceof Admin)) throw new SecurityException(CError.USER_NOT_ADMIN_ACOUNT.getMessage());
        Admin admin = (Admin) securityService.getAccount(); // TODO: 2022-03-28 Admin으로 수정 필요
        Restaurant restaurant = new Restaurant(postDTO, admin);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

//    public void addImage(Long restId, MultipartFile image){
//        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
//        if(optionalRestaurant.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getErrorMessage());
//        Restaurant restaurant = optionalRestaurant.get();
//        if (!securityService.isRestaurantAdmin(restaurant)) throw new SecurityException(CError.NOT_ADMIN_OF_REST.getErrorMessage())
//    }

    @Transactional
    public Long saveWithImage(RestaurantPostDTO postDTO, List<MultipartFile> multipartImages) throws SecurityException {
        Account account = securityService.getAccount();
        if (!(account instanceof Admin)) throw new SecurityException(CError.USER_NOT_ADMIN_ACOUNT.getMessage());

        Restaurant restaurant = new Restaurant(postDTO, (Admin) account);
        restaurantRepository.save(restaurant);

        List<Image> images = ImageHandler.upload(multipartImages);

        if (images != null) {
            imageRepository.saveImages(images);
            restaurant.addImages(images);
        }

        return restaurant.getId();
    }

    public RestInfoGetDTO getRestaurantInfo(Long id, Account account) throws IllegalStateException {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        Restaurant restaurant = optional.orElseThrow(() -> new IllegalStateException(CError.REST_NOT_FOUND.getMessage()));
        restaurant.plusViewCount();
        if(account instanceof Member) {
            if (((Member)account).getBookmark().contains(restaurant))
                return RestInfoGetDTO.from(restaurant, true);
            else
                return RestInfoGetDTO.from(restaurant, false);
        } else {
            return RestInfoGetDTO.from(restaurant, false);
        }
    }

    public RestInfoGetDTO getRestaurantInfoByName(String restName) throws IllegalStateException {
        Optional<Restaurant> optional = restaurantRepository.findByName(restName);
        Restaurant restaurant = optional.orElseThrow(() -> new IllegalStateException(CError.REST_NOT_FOUND.getMessage()));
        restaurant.setViewCount(restaurant.getViewCount() + 1);

        return RestInfoGetDTO.from(restaurant);
    }

    public StructGetDTO getRestaurantStructure(Long id) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        Restaurant restaurant = optional.orElseThrow(() -> new IllegalStateException(CError.REST_NOT_FOUND.getMessage()));
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
    @Transactional
    public Slice<RestSimpleGetWithLocDTO> searchByKeyword(String keyword, Pageable pageable) {
        searchService.saveSearch(keyword);
        // keyword - 지역, 음식점 이름, 메뉴, 카테고리 일 수 있음 && 여러 단어일 수도..
        String target = keyword.trim();
        String[] tokens = target.split(" ");
        Set<Long> result = new HashSet<>();

        for (String token : tokens) {
            // 지역
            result.addAll(restaurantRepository.findAllByRegion2Depth(token));
            result.addAll(restaurantRepository.findAllByRegion3Depth(token));
            // 매장 이름, 혹은 메뉴 이름
            result.addAll(restaurantRepository.findAllByName(token));
            result.addAll(restaurantRepository.findAllByMenu(token));
        }
        Slice<Restaurant> restaurants = restaurantRepository.findAllByIds(result, pageable);
        return restaurants.map(RestSimpleGetWithLocDTO::from);
    }

    public Slice<RestSimpleGetWithLocDTO> getRestaurantByCoord(Float x, Float y, Pageable pageable) {
        // x, y 좌표를 중점으로하고, 특정 반경 내에 위치하는 매장들의 리스트들을 반환
        // Float r = 0.0037f; // "한성대"와 "한성대역 입구" 사이의 직선거리의 절반 거리

        Float r = 0.9f; // 0.3(약 300m)에서 0.9로 수정함, 확인 필요

        Slice<Restaurant> restaurants = restaurantRepository.findRestByCoord((double) x, (double) y, (double) r, pageable);
        return restaurants.map(RestSimpleGetWithLocDTO::from);
    }
}
