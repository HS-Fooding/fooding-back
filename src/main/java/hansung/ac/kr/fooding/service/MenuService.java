package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Menu;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.dto.MenuGetDTO;
import hansung.ac.kr.fooding.dto.MenuPostDTO;
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
    private final SecurityService securityService;

    public void addMenu(MenuPostDTO menuPostDTO, MultipartFile image, Long id) throws IllegalStateException, SecurityException{
        Member loginAccount = (Member)securityService.getAccount();
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");
        Restaurant restaurant = optional.get();
        if(restaurant.getAdmin() != loginAccount) throw new SecurityException("No Authorization");
        System.out.println("##########"+menuPostDTO.toString());
        Menu menu = new Menu(menuPostDTO, null);
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
