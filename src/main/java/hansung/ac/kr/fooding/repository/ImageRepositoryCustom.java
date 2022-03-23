package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.image.Image;

import java.util.List;

public interface ImageRepositoryCustom {
    void saveImages(List<Image> images);
}
