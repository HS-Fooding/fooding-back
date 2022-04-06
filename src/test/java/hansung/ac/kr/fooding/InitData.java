package hansung.ac.kr.fooding;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.dto.RestaurantPostDTO;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.AccountService;
import hansung.ac.kr.fooding.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile("test")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init()
            throws X_NickNameAlreadyExistsException, X_NotRegisteredRole, X_IdAlreadyExistsException {
        initService.init();
    }


    @Component
    static class InitService {
        @Autowired RestaurantRepository restaurantRepository;
        @Autowired AccountRepository accountRepository;
        @Autowired AccountService accountService;
        @Autowired ReviewService reviewService;
        @Autowired EntityManager em;

        @Transactional
        public void init()
                throws X_NickNameAlreadyExistsException, X_NotRegisteredRole, X_IdAlreadyExistsException {
            // 계정 생성
            Set<Role> roles = new HashSet<>();
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");
            em.persist(role1);
            em.persist(role2);

            roles.add(role1);
            roles.add(role2);

            JoinReqDTO joinReqDTO = new JoinReqDTO("testIdentifier", "testPW", "testName", "testNickName", false, 11, null, roles, null );
            accountService.join(joinReqDTO);

            // 매장 등록
            Location addressResult = Location.builder()
                    .addressName("경기도 파주시 교하로 100")
                    .region1Depth("경기도")
                    .region2Depth("파주시")
                    .region3Depth("목동동")
                    .roadName("교하로")
                    .buildingNo("16")
                    .coordinate(new Coordinate(127.095f, 37.5035f)).build();

            RestaurantPostDTO restaurantPostDTO = new RestaurantPostDTO("restName", List.of("010"), new WorkHour("1", "2"), new WorkHour("3", "4")
                    , "Parking here", "intro", addressResult, null, 3.6f);

            Account account = accountRepository.findByIdentifier("testIdentifier");

            Restaurant restaurant = new Restaurant(restaurantPostDTO, (Admin) account);
            restaurantRepository.save(restaurant);

            // when
            ReviewPostDTO reviewPostDTO1 = new ReviewPostDTO("title1", "content1", 1.5f);
            ReviewPostDTO reviewPostDTO2 = new ReviewPostDTO("title2", "content2", 2.5f);

            reviewService.postReview(account, reviewPostDTO1, null, restaurant.getId());
            reviewService.postReview(account, reviewPostDTO2, null, restaurant.getId());
        }
    }

}
