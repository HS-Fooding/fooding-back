package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.domain.structure.Floor;

import javax.persistence.*;
import java.util.List;

@Entity
public class Restaurant extends BaseEntity{
    @Id @GeneratedValue
    private long id;
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
    private String intro;

    @OneToMany(mappedBy = "restaurant")
    private List<Image> images;

    @Embedded
    private Location location;

    @ElementCollection
    @CollectionTable(name = "category",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Favor> category;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Floor> floors;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Menu> menus;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Reservation> reservations;
    private int viewCount;
}
