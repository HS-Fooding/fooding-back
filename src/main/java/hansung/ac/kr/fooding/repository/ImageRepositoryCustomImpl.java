package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.image.Image;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom{

    private final EntityManager em;

    @Override
    public void saveImages(List<Image> images) {
        for(Image image: images)
            em.persist(image);
    }
}
