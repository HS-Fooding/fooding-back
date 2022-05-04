package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.dto.restaurant.RestaurantPostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ElementCollection
    @CollectionTable(name="tel", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> tel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "open", column = @Column(name = "days_open")),
            @AttributeOverride(name = "close", column = @Column(name = "days_close"))
        })
    private WorkHour weekdaysWorkHour;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "open", column = @Column(name = "ends_open")),
            @AttributeOverride(name = "close", column = @Column(name = "ends_close"))
    })
    private WorkHour weekendsWorkHour;
    private String parkingInfo;
    private String intro;
    private float maximumUsageTime;

    @OneToMany
    private List<Image> images = new ArrayList<>();

    @Embedded
    private Location location;

    @ElementCollection
    @CollectionTable(name = "category",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Favor> category = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Floor> floors = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Review> reviews = new ArrayList<>();

    private int viewCount;

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void addReservation(Reservation reservation) {reservations.add(reservation);}

    public void addMenu(Menu menu){
        menus.add(menu);
    }

    public void addReview(Review review) { reviews.add(review); }

    public Optional<Menu> getMenuById(Long id){
        for(Menu menu : menus){
            if (menu.getId() == id){
                return Optional.of(menu);
            }
        }
        return Optional.empty();
    }

    public void deleteMenu (Menu menu){
        menus.remove(menu);
    }

    public void deleteFloors(){
        this.floors = null;
    }

    public void addImages(List<Image> images) {
        for(Image image : images){
            this.images.add(image);
        }
    }

    public void addImage(Image image){
        this.images.add(image);
    }

    public void addFloor(Floor floor){
        floors.add(floor);
    }

    public Restaurant(RestaurantPostDTO dto, Admin admin){
        this.name = dto.getName();
        this.admin = admin;
        this.tel = dto.getTel();
        this.weekdaysWorkHour = dto.getWeekdaysWorkHour();
        this.weekendsWorkHour = dto.getWeekendsWorkHour();
        this.location = dto.getLocation();
        this.intro = dto.getIntro();
        this.category = dto.getCategory();
        this.maximumUsageTime = dto.getMaximumUsageTime();
        this.parkingInfo = dto.getParkingInfo();
    }

    public void plusViewCount() {
        this.setViewCount(this.getViewCount() + 1);
    }
}
