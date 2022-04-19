package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.structure.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservFloorDTO {
    private List<TableDTO> tables = new ArrayList<>();
    private List<Seat> seats = new ArrayList<>();
    private List<Door> doors = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private List<Window> windows = new ArrayList<>();

    public ReservFloorDTO(Floor floor){
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

    public ReservFloorDTO(FloorDTO floordto) {
        seats = floordto.getSeats();
        doors = floordto.getDoors();
        walls = floordto.getWalls();
        windows = floordto.getWindows();
    }


    /*public static List<ReservFloorDTO> from(List<FloorDTO> floordto, List<TableDTO> tableDTOS) {
        if(floordto == null) return  null;
        List<ReservFloorDTO> reservdto = new ArrayList<>();
        for (FloorDTO t : floordto) {
            reservdto.add(new ReservFloorDTO(t));
        }
        for(TableDTO t : tableDTOS) {

        }
        return reservdto;
    }*/

    public static List<ReservFloorDTO> from(List<Floor> floors){
        if (floors == null) return null;
        List<ReservFloorDTO> floorDTOs = new ArrayList<>();
        for(Floor floor : floors){
            floorDTOs.add(new ReservFloorDTO(floor));
        }
        return floorDTOs;
    }

}