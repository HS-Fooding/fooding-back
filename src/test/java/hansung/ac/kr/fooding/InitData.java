package hansung.ac.kr.fooding;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.dto.RestaurantPostDTO;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.AccountService;
import hansung.ac.kr.fooding.service.CommentService;
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
        @Autowired CommentService commentService;

        @Transactional
        public void init()
                throws X_NickNameAlreadyExistsException, X_NotRegisteredRole, X_IdAlreadyExistsException {
            // 1. 계정 생성 (관리자)
            Set<Role> adminRoles = new HashSet<>();
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");
            em.persist(role1);
            em.persist(role2);

            adminRoles.add(role1);
            adminRoles.add(role2);

            JoinReqDTO adminJoinReqDTO = new JoinReqDTO("adminID", "adminPW", "adminName",
                    "adminNickName", false, 10, null, adminRoles, null );
            accountService.join(adminJoinReqDTO);

            // 2. 계정 생성 (일반 사용자1)
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role2);
            JoinReqDTO userJoinReqDTO = new JoinReqDTO("userID", "userPW", "userName",
                    "userNickName", false, 11, null, userRoles, null );
            accountService.join(userJoinReqDTO);

            // 3. 계정 생성 (일반 사용자2)
            JoinReqDTO user2JoinReqDTO = new JoinReqDTO("user2ID", "user2PW", "user2Name",
                    "user2NickName", false, 12, null, userRoles, null );
            accountService.join(user2JoinReqDTO);


            // 3. 관리자의 매장 등록
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

            Account adminAccount = accountRepository.findByIdentifier("adminID");

            Restaurant restaurant = new Restaurant(restaurantPostDTO, (Admin) adminAccount);
            restaurantRepository.save(restaurant);

            // 4. 사용자의 리뷰 작성 (일반 사용자1)
            Account user1Account = accountRepository.findByIdentifier("userId");

            ReviewPostDTO reviewPostDTO1 = new ReviewPostDTO("title1", "content1", 1.5f);
            ReviewPostDTO reviewPostDTO2 = new ReviewPostDTO("title2", "content2", 2.5f);

            Long review1 = reviewService.postReview(user1Account, reviewPostDTO1, null, restaurant.getId());
            Long review2 = reviewService.postReview(user1Account, reviewPostDTO2, null, restaurant.getId());

            // 5. 사용자의 댓글 작성 (일반 사용자2)
            Account user2Account = accountRepository.findByIdentifier("user2Id");
            CommentPostDTO c1 = new CommentPostDTO(null, "comment1");
            CommentPostDTO c2 = new CommentPostDTO(null, "comment2");
            CommentPostDTO c3 = new CommentPostDTO(null, "comment3");
            CommentPostDTO c4 = new CommentPostDTO(null, "comment4");

            commentService.postComment(review1, user1Account, c1);
            commentService.postComment(review1, user1Account, c2);
            commentService.postComment(review2, user2Account, c3);
            commentService.postComment(review2, user2Account, c4);
        }
    }

}
