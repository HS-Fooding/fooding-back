package hansung.ac.kr.fooding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import hansung.ac.kr.fooding.domain.structure.*;
import lombok.Data;

import java.util.List;

@Data
public class FloorPostDTO {
    private List<Table> tables;
    private List<Seat> seats;
    private List<Door> doors;
    private List<Wall> walls;
    private List<Window> windows;
}
