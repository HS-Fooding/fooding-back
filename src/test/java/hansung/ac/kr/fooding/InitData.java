package hansung.ac.kr.fooding;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.comment.CommentPostDTO;
import hansung.ac.kr.fooding.dto.login.JoinReqDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestaurantPostDTO;
import hansung.ac.kr.fooding.dto.review.ReviewPostDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.repository.*;
import hansung.ac.kr.fooding.service.AccountService;
import hansung.ac.kr.fooding.service.CommentService;
import hansung.ac.kr.fooding.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
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
        @Autowired
        RestaurantRepository restaurantRepository;
        @Autowired
        AccountRepository accountRepository;
        @Autowired
        AccountService accountService;
        @Autowired
        ReviewService reviewService;
        @Autowired
        EntityManager em;
        @Autowired
        CommentService commentService;
        @Autowired
        StructureRepository structureRepository;
        @Autowired
        FloorRepository floorRepository;
        @Autowired
        ReservationRepository reservationRepository;

        @Transactional
        public void init()
                throws X_NickNameAlreadyExistsException, X_NotRegisteredRole, X_IdAlreadyExistsException {
            // 1. 계정 생성 (관리자 두 명)
            Set<Role> adminRoles = new HashSet<>();
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");
            em.persist(role1);
            em.persist(role2);

            adminRoles.add(role1);
            adminRoles.add(role2);

            JoinReqDTO adminJoinReqDTO1 = new JoinReqDTO("adminID1", "adminPW1", "adminName1",
                    "adminNickName1", false, 10, List.of(Favor.KOREAN, Favor.NOODLE), adminRoles, Job.FREELANCER);
            JoinReqDTO adminJoinReqDTO2 = new JoinReqDTO("adminID2", "adminPW2", "adminName2",
                    "adminNickName2", true, 20, List.of(Favor.CAFE, Favor.CHINESE), adminRoles, Job.HOUSEWIVES);
            JoinReqDTO adminJoinReqDTO3 = new JoinReqDTO("adminID3", "adminPW3", "adminName3",
                    "adminNickName3", false, 30, List.of(Favor.KOREAN, Favor.JAPAN), adminRoles, Job.NONE);
            accountService.join(adminJoinReqDTO1);
            accountService.join(adminJoinReqDTO2);
            accountService.join(adminJoinReqDTO3);


            // 2. 계정 생성 (일반 사용자 세 명)
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role2);
            JoinReqDTO joinReqDTO1 = new JoinReqDTO("userID1", "userPW1", "userName1",
                    "userNickName1", false, 11, List.of(Favor.CAFE, Favor.NOODLE, Favor.CHINESE), userRoles, Job.STUDENT);
            JoinReqDTO joinReqDTO2 = new JoinReqDTO("userID2", "userPW2", "userName2",
                    "userNickName2", true, 12, List.of(Favor.JAPAN, Favor.BAR, Favor.BBQ), userRoles, Job.OFFICER);
            JoinReqDTO joinReqDTO3 = new JoinReqDTO("userID3", "userPW3", "userName3",
                    "userNickName3", true, 22, List.of(Favor.KOREAN, Favor.BAR, Favor.DESSERT), userRoles, Job.FREELANCER);

            accountService.join(joinReqDTO1);
            accountService.join(joinReqDTO2);
            accountService.join(joinReqDTO3);


            // 3. 관리자의 매장 등록
            Location address1 = Location.builder()
                    .addressName("경기도 파주시 교하로 100")
                    .region1Depth("경기도")
                    .region2Depth("파주시")
                    .region3Depth("목동동")
                    .roadName("교하로")
                    .buildingNo("16")
                    .coordinate(new Coordinate(127.095f, 37.5035f)).build();

            Location address2 = Location.builder()
                    .addressName("서울 성북구 화랑로48길 16")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("석관동")
                    .roadName("화랑로48길")
                    .buildingNo("16")
                    .coordinate(new Coordinate(127.095f, 37.5035f)).build();

            RestaurantPostDTO restaurantPostDTO1 = new RestaurantPostDTO("restName1", List.of("010-1111-1111", "010-2222-2222"), new WorkHour("1", "2"), new WorkHour("3", "4")
                    , "주차 공간 없음", "우리 가게는 영국에서 시작되어..", address1, List.of(Favor.KOREAN, Favor.SNACK), 150f);

            RestaurantPostDTO restaurantPostDTO2 = new RestaurantPostDTO("restName2", List.of("010-3333-3333", "010-4444-4444"), new WorkHour("1", "2"), new WorkHour("3", "4")
                    , "주차 10대 가능", "50년 전통의 우리 가게..", address2, List.of(Favor.LAMB, Favor.BAR), 120f);

            Account adminAccount1 = accountRepository.findByIdentifier("adminID1");
            Account adminAccount2 = accountRepository.findByIdentifier("adminID2");

            Restaurant restaurant1 = new Restaurant(restaurantPostDTO1, (Admin) adminAccount1);
            Restaurant restaurant2 = new Restaurant(restaurantPostDTO2, (Admin) adminAccount2);
            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);

            // 4. 사용자의 리뷰 작성 (일반 사용자1)
            Account userAccount1 = accountRepository.findByIdentifier("userId1");
            Account userAccount2 = accountRepository.findByIdentifier("userId2");

            ReviewPostDTO reviewPostDTO1 = new ReviewPostDTO("title1", "review1", 1.5f);
            ReviewPostDTO reviewPostDTO2 = new ReviewPostDTO("title2", "review2", 2.5f);
            ReviewPostDTO reviewPostDTO3 = new ReviewPostDTO("title3", "review3", 3.5f);
            ReviewPostDTO reviewPostDTO4 = new ReviewPostDTO("title4", "review4", 4.5f);
            ReviewPostDTO reviewPostDTO5 = new ReviewPostDTO("title5", "review5", 5.0f);

            Long review1 = reviewService.postReview(userAccount1, reviewPostDTO1, null, restaurant1.getId());
            Long review2 = reviewService.postReview(userAccount1, reviewPostDTO2, null, restaurant1.getId());
            Long review3 = reviewService.postReview(userAccount2, reviewPostDTO3, null, restaurant2.getId());
            Long review4 = reviewService.postReview(userAccount2, reviewPostDTO4, null, restaurant2.getId());
            Long review5 = reviewService.postReview(userAccount2, reviewPostDTO5, null, restaurant2.getId());

            // 5. 사용자의 댓글 작성 (일반 사용자2)
            CommentPostDTO c1 = new CommentPostDTO(null, "comment1");
            CommentPostDTO c2 = new CommentPostDTO(null, "comment2");
            CommentPostDTO c3 = new CommentPostDTO(null, "comment3");
            CommentPostDTO c4 = new CommentPostDTO(null, "comment4");

            CommentPostDTO c5 = new CommentPostDTO(null, "comment5");
            CommentPostDTO c6 = new CommentPostDTO(null, "comment6");
            CommentPostDTO c7 = new CommentPostDTO(null, "comment7");
            CommentPostDTO c8 = new CommentPostDTO(null, "comment8");

            commentService.postComment(review1, userAccount1, c1);
            commentService.postComment(review1, userAccount1, c2);
            commentService.postComment(review2, userAccount1, c3);
            commentService.postComment(review2, userAccount1, c4);

            commentService.postComment(review3, userAccount2, c5);
            commentService.postComment(review3, userAccount2, c6);
            commentService.postComment(review4, userAccount2, c7);
            commentService.postComment(review5, userAccount2, c8);

            // 6. 레스토랑 테이블 등록
            Floor floor = new Floor();
            Table table = new Table();
            table.setTableNum("Num0");
            table.setAvailable(true);
            table.setMaxPeople(4);
            table.setMinPeople(2);

            Table table1 = new Table();
            table1.setTableNum("Num1");
            table1.setAvailable(true);
            table1.setMaxPeople(4);
            table1.setMinPeople(2);

            Table table2 = new Table();
            table2.setTableNum("Num2");
            table2.setAvailable(true);
            table2.setMaxPeople(6);
            table2.setMinPeople(4);

            floor.addStructure(table);
            floor.addStructure(table1);
            floor.addStructure(table2);
            restaurant1.addFloor(floor);
            structureRepository.saveAll(floor.getStructures());
            floorRepository.save(floor);
            restaurant1.addFloor(floor);

            // 7. 매장 예약 생성
            Reservation reservation1 = new Reservation();
            reservation1.setReserveDate("1997-06-05");
            reservation1.setReserveTime("10:00");
            reservation1.setCar(true);
            reservation1.setTable(table);
            reservation1.setReserveNum(2);
            reservation1.setMember((Member) userAccount1);
            restaurant1.addReservation(reservation1);
            reservationRepository.save(reservation1);

            Reservation reservation2 = new Reservation();
            reservation2.setReserveDate("1997-06-05");
            reservation2.setReserveTime("14:00");
            reservation2.setCar(true);
            reservation2.setTable(table);
            reservation2.setReserveNum(2);
            reservation2.setMember((Member) userAccount1);
            restaurant1.addReservation(reservation2);
            reservationRepository.save(reservation2);
        }
    }

}
