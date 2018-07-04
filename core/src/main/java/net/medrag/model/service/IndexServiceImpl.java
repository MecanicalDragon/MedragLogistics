package net.medrag.model.service;

import net.medrag.dto.CargoDto;
import net.medrag.dto.Dto;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class IndexServiceImpl implements IndexService {

    private static final String orderIndex = "ORD";
    private static final String cargoIndex = "CGO";

    public String indicate(Dto dto) {

        String index = dto instanceof CargoDto ? cargoIndex : orderIndex;
        StringBuilder sb = new StringBuilder(index);

        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        sb.append("-").append(dateFormat.format(date)).append("-");

        if (dto instanceof CargoDto) {
            sb.append(((CargoDto) dto).getDeparture().getIndex()).append("-")
                    .append(((CargoDto) dto).getDestination().getIndex()).append("-");
        }
        sb.append(new Random().nextInt(8999) + 1000);

        return sb.toString();
    }

}
