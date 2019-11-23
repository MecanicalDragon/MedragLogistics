package net.medrag.service.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.Dto;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.domain.entity.City;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.IndexService;
import net.medrag.service.dto.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for indexing {@link CargoDto} and {@link OrderrDto}
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

    /**
     * The only method indexes cargoes and orders with taking time and city indexes.
     *
     * @param dto - {@link CargoDto} or {@link OrderrDto}, that is needed to be indexed.
     * @return - indexed {@link CargoDto} or {@link OrderrDto}
     * @throws MedragServiceException - hasn't been happened.
     */
    public String indicate(Dto dto) throws MedragServiceException {

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
        sb.append(new SecureRandom().nextInt(8999) + 1000);

        return sb.toString();
    }

}
