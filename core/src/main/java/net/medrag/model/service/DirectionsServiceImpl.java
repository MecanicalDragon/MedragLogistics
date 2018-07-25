package net.medrag.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.model.domain.entity.City;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CityDto;
import net.medrag.model.service.dto.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DirectionsServiceImpl implements DirectionsService {

    private CityService<CityDto, City> cityService;
    private static final String URL = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    private static final String KEY = "&key=AIzaSyDGfoBub9yxLqFtIVYk_bwSE7Kn8SSvkdI";

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    public Integer[] getTripTime(CityDto departure, CityDto destination) throws MedragServiceException{
        final String request = URL + departure.getCoordinatesX() + "," + departure.getCoordinatesY() + "&destination=" +
                destination.getCoordinatesX() + "," + destination.getCoordinatesY() + KEY;

        try (InputStream is = new URL(request).openStream()) {
//            final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            JsonNode response = new ObjectMapper().readTree(is);
            JsonNode legs = response.findValue("legs");
            JsonNode firstElement = legs.get(0);
            JsonNode distance = firstElement.get("distance").get("value");
            JsonNode duration = firstElement.get("duration").get("value");
            Integer kms = distance.asInt()/1000;
            Integer minutes = duration.asInt()/60+240;
            //10560 limit
            Integer[] trip = new Integer[2];
            trip[0] = kms;
            trip[1] = minutes;

            return trip;

        } catch (IOException e) {
            throw new MedragServiceException(e);
        }

    }

    public Integer getComletePersent(CargoDto cargo) throws MedragServiceException {

//        Get all three main transfer points of the cargo
        CityDto departure = cityService.getDtoById(new CityDto(), new City(), cargo.getDepartureId());
        CityDto currentCity = cityService.getDtoById(new CityDto(), new City(), cargo.getCurrentCityId());
        CityDto destination = cityService.getDtoById(new CityDto(), new City(), cargo.getDestinationId());

//        Counting the percent of delivery
        Integer[]totalTime = getTripTime(departure, destination);
        Integer[]leftTime = getTripTime(currentCity, destination);
        return 100-(100*leftTime[0]/totalTime[0]);
    }
}
