package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Menu;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.menu.MenuGetDTO;
import hansung.ac.kr.fooding.dto.menu.MenuPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.MenuRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.var.CError;
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
        if(optional.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optional.get();
        if(restaurant.getAdmin() != loginAccount) throw new SecurityException(CError.USER_NOT_ADMIN_OF_REST.getMessage());

        Menu menu = new Menu(menuPostDTO, null);
        Image image = ImageHandler.upload(multipartImage);
        if (image != null) {
            imageRepository.save(image);
            menu.setImage(image);
        }
        restaurant.addMenu(menu);
        menuRepository.save(menu);
    }

    public List<MenuGetDTO> getMenuFromRestaurant(Long id) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optional.get();
        List<MenuGetDTO> result = new ArrayList<MenuGetDTO>();
        restaurant.getMenus().forEach(m-> result.add(MenuGetDTO.from(m)));
        return result;
    }
    
    public void deleteMenu(Long restId, Long menuId){
        Optional<Restaurant> optional = restaurantRepository.findById(restId);
        if(optional.isEmpty()) throw new IllegalStateException(CError.REST_NOT_FOUND.getMessage());
        Restaurant restaurant = optional.get();
        Optional<Menu> optionalMenu = restaurant.getMenuById(menuId);
        if(optionalMenu.isEmpty()) throw new IllegalStateException(CError.MENU_NOT_FOUND.getMessage());
        Menu menu = optionalMenu.get();
        if(menu.getImage() != null) {
            Image image = menu.getImage();
            menu.setImage(null);
            imageRepository.delete(image);
        }
        menuRepository.delete(menu);
    }
}
