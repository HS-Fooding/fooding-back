package hansung.ac.kr.fooding.dto.image;

import hansung.ac.kr.fooding.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResDTO {
    private long id;
    private String path;

    public ImageResDTO(Image image) {
        this.id = image.getId();
        this.path = image.getPath();
    }
}
