package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Menu;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.MenuGetDTO;
import hansung.ac.kr.fooding.dto.MenuPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.MenuRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final ImageRepository imageRepository;
    private final SecurityService securityService;

    public void addMenu(MenuPostDTO menuPostDTO, MultipartFile multipartImage, Long id) throws IllegalStateException, SecurityException{
        Account loginAccount = (Account)securityService.getAccount();
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optional.get();
        if(restaurant.getAdmin() != loginAccount) throw new SecurityException("No Authorization");

        System.out.println("##########"+menuPostDTO.toString());
        Menu menu = new Menu(menuPostDTO, null);
        Image image = ImageHandler.upload(multipartImage);
        imageRepository.save(image);
        menu.setImage(image);
        restaurant.addMenu(menu);
        menuRepository.save(menu);
    }

    public List<MenuGetDTO> getMenuFromRestaurant(Long id) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optional.get();
        List<MenuGetDTO> result = new ArrayList<MenuGetDTO>();
        restaurant.getMenus().forEach(m-> result.add(MenuGetDTO.from(m)));
        return result;
    }
}
