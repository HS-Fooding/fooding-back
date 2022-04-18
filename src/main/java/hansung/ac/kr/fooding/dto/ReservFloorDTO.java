package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.structure.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservFloorDTO {
    private static List<TableDTO> tables;
    private List<Seat> seats;
    private List<Door> doors;
    private List<Wall> walls;
    private List<Window> windows;

    public ReservFloorDTO(Floor floor){
        tables = new ArrayList<>();
        seats = new ArrayList<>();
        doors = new ArrayList<>();
        walls = new ArrayList<>();
        windows = new ArrayList<>();

        for (Structure structure : floor.getStructures()){
            if(structure instanceof Table)
                tables.add(new TableDTO((Table)structure));
            else if (structure instanceof Seat)
                seats.add((Seat)structure);
            else if (structure instanceof Door)
                doors.add((Door)structure);
            else if (structure instanceof Wall)
                walls.add((Wall)structure);
            else if (structure instanceof Window)
                windows.add((Window)structure);
            else;
        }
    }

    public static List<ReservFloorDTO> from(List<Floor> floors, List<TableDTO> dto){
        if (floors == null) return null;
        List<ReservFloorDTO> floorDTOs = new ArrayList<>();
        for(Floor floor : floors){
            floorDTOs.add(new ReservFloorDTO(floor));
        }
        tables = dto;
        return floorDTOs;
    }

}