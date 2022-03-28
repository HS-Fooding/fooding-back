package hansung.ac.kr.fooding.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Location {
    private String addressName;
    private String region1Depth;
    private String region2Depth;
    private String region3Depth;
    private String roadName;
    private String buildingNo;
    private Coordinate coordinate;

    @Override
    public String toString() {
        return "Location{" +
                "addressName='" + addressName + '\'' +
                ", region1Depth='" + region1Depth + '\'' +
                ", region2Depth='" + region2Depth + '\'' +
                ", region3Depth='" + region3Depth + '\'' +
                ", roadName='" + roadName + '\'' +
                ", buildingNo='" + buildingNo + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }
}
