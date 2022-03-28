package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.WorkHour;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RestaurantPostDTO {
    private String name;
    private List<String> tel;
    private WorkHour weekdaysWorkHour;
    private WorkHour weekendsWorkHour;
    private String intro;
    private Location location;
    private List<Favor> category;
}
