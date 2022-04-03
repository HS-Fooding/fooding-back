package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.RestInfoGetDTO;
import hansung.ac.kr.fooding.dto.RestaurantPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


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

        if (multipartImages != null) {
            List<Image> images = ImageHandler.upload(multipartImages);
            imageRepository.saveImages(images);
            restaurant.addImages(images);
        }

        return restaurant.getId();
    }

    public RestInfoGetDTO getRestaurantInfo(Long id) throws IllegalStateException {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if (optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optional.get();
        return RestInfoGetDTO.from(restaurant);
    }
}
