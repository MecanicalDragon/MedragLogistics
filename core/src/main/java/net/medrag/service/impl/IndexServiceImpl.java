package net.medrag.service.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.Dto;
import net.medrag.domain.entity.City;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.IndexService;
import net.medrag.service.dto.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    public String indicate(Dto dto)throws MedragServiceException {

        String index = dto instanceof CargoDto ? cargoIndex : orderIndex;
        StringBuilder sb = new StringBuilder(index);

        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        sb.append("-").append(dateFormat.format(date)).append("-");

        if (dto instanceof CargoDto) {

            CityDto departure = cityService.getDtoByNaturalId(new CityDto(), new City(), ((CargoDto) dto).getDepartureName());
            CityDto destination = cityService.getDtoByNaturalId(new CityDto(), new City(), ((CargoDto) dto).getDestinationName());

            sb.append(departure.getIndex()).append("-")
                    .append(destination.getIndex()).append("-");
        }
        sb.append(new Random().nextInt(8999) + 1000);

        return sb.toString();
    }

}
