package hansung.ac.kr.fooding;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import hansung.ac.kr.fooding.domain.image.Image;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
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
        @Autowired
        ImageRepository imageRepository;

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
            JoinReqDTO adminJoinReqDTO0 = new JoinReqDTO("testAdminID", "testAdminPW", "testAdminName",
                    "testAdminNickName", false, 10, List.of(Favor.KOREAN, Favor.NOODLE), adminRoles, Job.FREELANCER);
            JoinReqDTO adminJoinReqDTO1 = new JoinReqDTO("adminID1", "adminPW1", "adminName1",
                    "adminNickName1", false, 10, List.of(Favor.KOREAN, Favor.NOODLE), adminRoles, Job.FREELANCER);
            JoinReqDTO adminJoinReqDTO2 = new JoinReqDTO("adminID2", "adminPW2", "adminName2",
                    "adminNickName2", true, 20, List.of(Favor.CAFE, Favor.CHINESE), adminRoles, Job.HOUSEWIVES);
            JoinReqDTO adminJoinReqDTO3 = new JoinReqDTO("adminID3", "adminPW3", "adminName3",
                    "adminNickName3", false, 30, List.of(Favor.KOREAN, Favor.JAPANESE), adminRoles, Job.NONE);
            accountService.join(adminJoinReqDTO0);
            accountService.join(adminJoinReqDTO1);
            accountService.join(adminJoinReqDTO2);
            accountService.join(adminJoinReqDTO3);


            // 2. 계정 생성 (일반 사용자 세 명)
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role2);
            JoinReqDTO joinReqDTO1 = new JoinReqDTO("userID1", "userPW1", "userName1",
                    "userNickName1", false, 11, List.of(Favor.CAFE, Favor.NOODLE, Favor.CHINESE), userRoles, Job.STUDENT);
            JoinReqDTO joinReqDTO2 = new JoinReqDTO("userID2", "userPW2", "userName2",
                    "userNickName2", true, 12, List.of(Favor.JAPANESE, Favor.BAR, Favor.BBQ), userRoles, Job.OFFICER);
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

            Location address3 = Location.builder()
                    .addressName("서울 성북구 삼선교로10길 10")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("삼선동")
                    .roadName("삼선교로 10길")
                    .buildingNo("10")
                    .coordinate(new Coordinate(127.008912f, 37.5874333f)).build();

            Location address4 = Location.builder()
                    .addressName("서울 성북구 삼선교로10길 25 치킨마루")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("삼선동")
                    .roadName("삼선교로 10길")
                    .buildingNo("25")
                    .coordinate(new Coordinate(127.009568f, 37.5869377f)).build();

            Location address5 = Location.builder()
                    .addressName("서울 성북구 삼선교로14길 25")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("삼선동")
                    .roadName("삼선교 14길")
                    .buildingNo("25")
                    .coordinate(new Coordinate(127.010304f, 37.5869647f)).build();

            RestaurantPostDTO restaurantPostDTO1 = new RestaurantPostDTO("restName1", List.of("010-1111-1111", "010-2222-2222"), new WorkHour("11:00", "20:00"), new WorkHour("11:00", "20:00")
                    , "주차 공간 없음", "우리 가게는 영국에서 시작되어..", address1, List.of(Favor.KOREAN, Favor.SNACK), 150f);

            RestaurantPostDTO restaurantPostDTO2 = new RestaurantPostDTO("restName2", List.of("010-3333-3333", "010-4444-4444"), new WorkHour("11:00", "20:00"), new WorkHour("11:00", "20:00")
                    , "주차 10대 가능", "50년 전통의 우리 가게..", address2, List.of(Favor.LAMB, Favor.BAR), 120f);
            RestaurantPostDTO restaurantPostDTO3 = new RestaurantPostDTO("한성회세꼬시", null, new WorkHour("12:00", "24:00"), new WorkHour("12:00", "24:00"),
                    "주차 불가능", "한성대 대표 횟집", address3, List.of(Favor.JAPANESE), 120f);
            RestaurantPostDTO restaurantPostDTO4 = new RestaurantPostDTO("치킨마루 한성대점", List.of("02-741-9544"), new WorkHour("12:00", "24:00"), new WorkHour("12:00", "24:00"),
                    "주차 1대 가능", "치킨마루 한성대점!", address4, List.of(Favor.CHICKEN, Favor.PUB), 120f);
            RestaurantPostDTO restaurantPostDTO5 = new RestaurantPostDTO("김치나베돈까스돈카츠전문점", List.of("070-4696-0758"), new WorkHour("06:00", "19:00"), new WorkHour("06:00", "19:00"),
                    "주차 불가능", "깨끗하고 맛있는 김치나베돈카츠 전문점입니다.", address5, List.of(Favor.KOREAN,Favor.JAPANESE,Favor.WESTERN), 120f);


            Location address6 = Location.builder()
                    .addressName("서울 성북구 삼선교로16길 40")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("삼선동")
                    .roadName("삼선교로 16길")
                    .buildingNo("40")
                    .coordinate(new Coordinate(127.010655f, 37.5864962f)).build();
            RestaurantPostDTO restaurantPostDTO6 = new RestaurantPostDTO("스타동 한성대점", List.of("02-743-7707"), new WorkHour("11:30", "20:30"), new WorkHour("11:30", "20:30"),
                    "주차 불가능", "사람들이 많이 찾는 스타동", address6, List.of(Favor.JAPANESE,Favor.WESTERN), 120f);

            Location address7 = Location.builder()
                    .addressName("서울 성북구 삼선교로16길 35 삼선 SK VIEW 아파트")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("삼선동")
                    .roadName("삼선교로 16길 35")
                    .buildingNo("삼선 SK VIEW 아파트")
                    .coordinate(new Coordinate(127.010722f, 37.587394f)).build();
            RestaurantPostDTO restaurantPostDTO7 = new RestaurantPostDTO("운봉손칼국수", List.of("02-953-5155"), new WorkHour("11:00", "22:00"), new WorkHour("11:30", "20:30"),
                    "주차 불가능", "보쌈 맛집 운봉 칼국수", address7, List.of(Favor.KOREAN), 120f);


            Location address8 = Location.builder()
                    .addressName("서울 성북구 삼선교로 34")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("삼선동")
                    .roadName("삼선교로")
                    .buildingNo("34")
                    .coordinate(new Coordinate(127.009353f, 37.5879559f)).build();
            RestaurantPostDTO restaurantPostDTO8 = new RestaurantPostDTO("역전할머니맥주 서울한성대점", List.of("010-3348-0327"), new WorkHour("14:00", "24:00"), new WorkHour("14:00", "24:00"),
                    "주차 불가능", "역전할맥", address8, List.of(Favor.KOREAN, Favor.BBQ, Favor.WESTERN, Favor.CHICKEN, Favor.PUB), 120f);

            Location address9 = Location.builder()
                    .addressName("서울 성북구 성북로 24-1")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("성북동")
                    .roadName("성북로")
                    .buildingNo("24-1")
                    .coordinate(new Coordinate(127.004474f, 37.5902446f)).build();
            RestaurantPostDTO restaurantPostDTO9 = new RestaurantPostDTO("피자스쿨 성북점", List.of("02-747-7778"), new WorkHour("11:30", "23:00"), new WorkHour("11:30", "23:00"),
                    "주차 불가능", "17년 4월 14일 새로 오픈한 피자스쿨 성북점입니다. 저렴한 가격으로 맛있는 양질의 피자를 드실 수 있습니다.^^",
                    address9, List.of(Favor.BBQ, Favor.WESTERN), 90f);

            Location address10 = Location.builder()
                    .addressName("서울 종로구 동숭길 86 2, 3층")
                    .region1Depth("서울")
                    .region2Depth("종로구")
                    .region3Depth("동숭동")
                    .roadName("동숭길 86")
                    .buildingNo("2")
                    .coordinate(new Coordinate(127.004292f, 37.5820907f)).build();
            RestaurantPostDTO restaurantPostDTO10 = new RestaurantPostDTO("핏제리아오", List.of("0507-1429-5005"),
                    new WorkHour("11:30", "22:00"), new WorkHour("11:30", "22:00"),
                    "주차 불가능", "맛있는 정통 피자",
                    address10, List.of(Favor.BBQ, Favor.WESTERN, Favor.PUB), 90f);

            Location address11 = Location.builder()
                    .addressName("서울 종로구 대명1길 10 1층")
                    .region1Depth("서울")
                    .region2Depth("종로구")
                    .region3Depth("명륜동")
                    .roadName("대명1길")
                    .buildingNo("10")
                    .coordinate(new Coordinate(127.000771f, 37.5830998f)).build();
            RestaurantPostDTO restaurantPostDTO11 = new RestaurantPostDTO("역전할머니맥주 대학로점", List.of("0507-1327-3582"),
                    new WorkHour("14:00", "04:00"), new WorkHour("14:00", "04:00"),
                    "주차 불가능", "역전할맥 대학로점",
                    address11, List.of(Favor.BBQ, Favor.WESTERN, Favor.PUB, Favor.KOREAN, Favor.JAPANESE), 90f);

            Location address12 = Location.builder()
                    .addressName("서울 종로구 대학로8가길 133")
                    .region1Depth("서울")
                    .region2Depth("종로구")
                    .region3Depth("동숭동")
                    .roadName("대학로8가길")
                    .buildingNo("133")
                    .coordinate(new Coordinate(127.003488f, 37.5801085f)).build();
            RestaurantPostDTO restaurantPostDTO12 = new RestaurantPostDTO("토끼정 대학로점", List.of("0507-1386-1030"),
                    new WorkHour("11:00", "22:00"), new WorkHour("11:00", "22:00"),
                    "주차 불가능", "토끼정 대학로점입니다.",
                    address12, List.of(Favor.BBQ, Favor.WESTERN, Favor.PUB, Favor.KOREAN, Favor.JAPANESE), 90f);

            Location address13 = Location.builder()
                    .addressName("서울 성북구 보문로21길 19-1")
                    .region1Depth("서울")
                    .region2Depth("성북구")
                    .region3Depth("보문동")
                    .roadName("보문로21길")
                    .buildingNo("19-1")
                    .coordinate(new Coordinate(127.019395f, 37.5829182f)).build();
            RestaurantPostDTO restaurantPostDTO13 = new RestaurantPostDTO("야미가 성북점", List.of("0507-1337-3507"),
                    new WorkHour("10:30","20:30"), new WorkHour("x","x"),
                    "주차 불가능", "야미가 성북점입니다.",
                    address13, List.of(Favor.BBQ, Favor.WESTERN, Favor.PUB, Favor.KOREAN, Favor.JAPANESE), 90f);

            Account testAdminAccount = accountRepository.findByIdentifier("testAdminID");
            Account adminAccount1 = accountRepository.findByIdentifier("adminID1");
            Account adminAccount2 = accountRepository.findByIdentifier("adminID2");

            Restaurant restaurant1 = new Restaurant(restaurantPostDTO1, (Admin) adminAccount1);
            Restaurant restaurant2 = new Restaurant(restaurantPostDTO2, (Admin) adminAccount2);
            Restaurant restaurant3 = new Restaurant(restaurantPostDTO3, (Admin) testAdminAccount);
            Restaurant restaurant4 = new Restaurant(restaurantPostDTO4, (Admin) testAdminAccount);
            Restaurant restaurant5 = new Restaurant(restaurantPostDTO5, (Admin) testAdminAccount);
            Restaurant restaurant6 = new Restaurant(restaurantPostDTO6, (Admin) testAdminAccount);
            Restaurant restaurant7 = new Restaurant(restaurantPostDTO7, (Admin) testAdminAccount);
            Restaurant restaurant8 = new Restaurant(restaurantPostDTO8, (Admin) testAdminAccount);
            Restaurant restaurant9 = new Restaurant(restaurantPostDTO9, (Admin) testAdminAccount);
            Restaurant restaurant10 = new Restaurant(restaurantPostDTO10, (Admin) testAdminAccount);
            Restaurant restaurant11= new Restaurant(restaurantPostDTO11, (Admin) testAdminAccount);
            Restaurant restaurant12= new Restaurant(restaurantPostDTO12, (Admin) testAdminAccount);
            Restaurant restaurant13= new Restaurant(restaurantPostDTO13, (Admin) testAdminAccount);

            Image image1 = new Image();
            image1.setPath("http://13.124.207.219:8080/fooding/image/image1.jpg");
            restaurant1.addImage(image1);

            Image image2 = new Image();
            image2.setPath("http://13.124.207.219:8080/fooding/image/image2.jpg");
            restaurant2.addImage(image2);

            Image image3 = new Image();
            image3.setPath("http://13.124.207.219:8080/fooding/image/image3.jpg");
            restaurant3.addImage(image3);

            Image image4 = new Image();
            image4.setPath("http://13.124.207.219:8080/fooding/image/image4.jpg");
            restaurant4.addImage(image4);

            Image image5 = new Image();
            image5.setPath("http://13.124.207.219:8080/fooding/image/image5.jpg");
            restaurant5.addImage(image5);

            Image image6 = new Image();
            image6.setPath("http://13.124.207.219:8080/fooding/image/image6.jpg");
            restaurant6.addImage(image6);

            Image image7 = new Image();
            image7.setPath("http://13.124.207.219:8080/fooding/image/image7.jpg");
            restaurant7.addImage(image7);

            Image image8 = new Image();
            image8.setPath("http://13.124.207.219:8080/fooding/image/image8.jpg");
            restaurant8.addImage(image8);

            Image image9 = new Image();
            image9.setPath("http://13.124.207.219:8080/fooding/image/image9.jpg");
            restaurant9.addImage(image9);

            Image image10 = new Image();
            image10.setPath("http://13.124.207.219:8080/fooding/image/image10.jpg");
            restaurant10.addImage(image10);

            Image image11 = new Image();
            image11.setPath("http://13.124.207.219:8080/fooding/image/image11.jpg");
            restaurant11.addImage(image11);

            Image image12 = new Image();
            image12.setPath("http://13.124.207.219:8080/fooding/image/image12.jpg");
            restaurant13.addImage(image12);

            Image image13 = new Image();
            image13.setPath("http://13.124.207.219:8080/fooding/image/image13.jpg");
            restaurant13.addImage(image13);

            List<Image> images = new ArrayList<>();
            images.add(image1);
            images.add(image2);
            images.add(image3);
            images.add(image4);
            images.add(image5);
            images.add(image6);
            images.add(image7);
            images.add(image8);
            images.add(image9);
            images.add(image10);
            images.add(image11);
            images.add(image12);
            images.add(image13);

            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);
            restaurantRepository.save(restaurant3);
            restaurantRepository.save(restaurant4);
            restaurantRepository.save(restaurant5);
            restaurantRepository.save(restaurant6);
            restaurantRepository.save(restaurant7);
            restaurantRepository.save(restaurant8);
            restaurantRepository.save(restaurant9);
            restaurantRepository.save(restaurant10);
            restaurantRepository.save(restaurant11);
            restaurantRepository.save(restaurant12);
            restaurantRepository.save(restaurant13);

            imageRepository.saveImages(images);




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
            reservation1.setBooker(Booker.from((Member)userAccount1));
            restaurant1.addReservation(reservation1);
            reservationRepository.save(reservation1);

            Reservation reservation2 = new Reservation();
            reservation2.setReserveDate("1997-06-05");
            reservation2.setReserveTime("14:00");
            reservation2.setCar(true);
            reservation2.setTable(table);
            reservation2.setReserveNum(3);
            reservation2.setBooker(Booker.from((Member) userAccount1));
            restaurant1.addReservation(reservation2);
            reservationRepository.save(reservation2);

            Reservation reservation3 = new Reservation();
            reservation3.setReserveDate("1997-06-05");
            reservation3.setReserveTime("20:00");
            reservation3.setCar(false);
            reservation3.setTable(table1);
            reservation3.setReserveNum(4);
            reservation3.setBooker(Booker.from((Member) userAccount1));
            restaurant1.addReservation(reservation3);
            reservationRepository.save(reservation3);

            Reservation reservation4 = new Reservation();
            reservation4.setReserveDate("1997-06-06");
            reservation4.setReserveTime("12:00");
            reservation4.setCar(false);
            reservation4.setTable(table2);
            reservation4.setReserveNum(4);
            reservation4.setBooker(Booker.from((Member) userAccount1));
            restaurant1.addReservation(reservation4);
            reservationRepository.save(reservation4);

            Reservation reservation5 = new Reservation();
            reservation5.setReserveDate("1997-06-06");
            reservation5.setReserveTime("14:00");
            reservation5.setCar(true);
            reservation5.setTable(table1);
            reservation5.setReserveNum(6);
            reservation5.setBooker(Booker.from((Member) userAccount1));
            restaurant1.addReservation(reservation5);
            reservationRepository.save(reservation5);

            Reservation reservation6 = new Reservation();
            reservation6.setReserveDate("1997-06-06");
            reservation6.setReserveTime("18:00");
            reservation6.setCar(true);
            reservation6.setTable(table);
            reservation6.setReserveNum(2);
            reservation6.setBooker(Booker.from((Member) userAccount1));
            restaurant1.addReservation(reservation6);
            reservationRepository.save(reservation6);

            Reservation reservation7 = new Reservation();
            reservation7.setReserveDate("1997-06-07");
            reservation7.setReserveTime("15:00");
            reservation7.setCar(true);
            reservation7.setTable(table1);
            reservation7.setReserveNum(3);
            reservation7.setBooker(Booker.from((Member) userAccount1));
            restaurant1.addReservation(reservation7);
            reservationRepository.save(reservation7);
        }
    }

}
