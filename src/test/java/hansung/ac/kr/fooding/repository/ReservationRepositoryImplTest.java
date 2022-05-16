package hansung.ac.kr.fooding.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hansung.ac.kr.fooding.dto.chart.ChartProjectionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ReservationRepositoryImplTest {
    @Autowired EntityManager em;
    @Autowired ReservationRepository reservationRepository;

    JPAQueryFactory queryFactory;

    @Test
    public void chartTest() {
        Long restId = 1L;
        String start = "1997-06-05";
        String end = "1997-06-06";

        List<ChartProjectionDTO> search = reservationRepository.getChart(restId, start, end);
        for (ChartProjectionDTO chartProjectionDTO : search) {
            System.out.println("chartDTO = " + chartProjectionDTO);
        }

    }

    @Test
    public void dateTest() {
        String startDate = "2022-04-29";
        String endDate = "2022-05-05";

        String[] startSplit = startDate.split("-");
        String[] endSplit = endDate.split("-");

        int startYear = Integer.parseInt(startSplit[0]);
        int startMonth = Integer.parseInt(startSplit[1]);
        int startDay = Integer.parseInt(startSplit[2]);

        int endYear = Integer.parseInt(endSplit[0]);
        int endMonth = Integer.parseInt(endSplit[1]);
        int endDay = Integer.parseInt(endSplit[2]);

        LocalDate _startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate _endDate = LocalDate.of(endYear, endMonth, endDay);

        List<LocalDate> dates = _startDate.datesUntil(_endDate).collect(Collectors.toList());
        dates.add(_endDate);
        List<String> result = dates.stream().map(m -> String.valueOf(m)).collect(Collectors.toList());


        for (String s : result) {
            System.out.println("s = " + s);
        }

    }
}