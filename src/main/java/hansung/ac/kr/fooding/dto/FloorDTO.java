package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.structure.*;
import hansung.ac.kr.fooding.repository.FloorRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FloorDTO {
    private List<Table> tables;
    private List<Seat> seats;
    private List<Door> doors;
    private List<Wall> walls;
    private List<Window> windows;

    public FloorDTO(Floor floor){
        tables = new ArrayList<>();
        seats = new ArrayList<>();
        doors = new ArrayList<>();
        walls = new ArrayList<>();
        windows = new ArrayList<>();

        for (Structure structure : floor.getStructures()){
            if(structure instanceof Table) {
                tables.add((Table) structure);
                ((Table)structure).setAvailable(true);
            }
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

    public static List<FloorDTO> from(List<Floor> floors){
        if (floors == null) return null;
        List<FloorDTO> floorDTOs = new ArrayList<>();
        for(Floor floor : floors){
            floorDTOs.add(new FloorDTO(floor));
        }
        return floorDTOs;
    }
/*
    public static List<ReservFloorDTO> from2(List<FloorDTO> floors){
        if (floors == null) return null;
        List<ReservFloorDTO> reservFloorDTOS = new ArrayList<>();
        for (ReservFloorDTO floor : reservFloorDTOS) {
            reservFloorDTOS.add(new ReservFloorDTO(floor, false))
        }

        return floorDTOs;
    }*/
}
