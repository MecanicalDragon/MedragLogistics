package net.medrag.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.domain.entity.City;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.DirectionsService;
import net.medrag.service.dto.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This service is responsible for google directions api.
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

    /**
     * Method calculates distance between two cities and time, needed for getting from one to another.
     * @param departure - departure city.
     * @param destination - destination city.
     * @return - array of integers, that has distance in kms in his first cell and time in minutes in second.
     * @throws MedragServiceException - if google refuses request
     */
    @Override
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

    /**
     * Method calculates percent of cargo delivery completeness.
     * @param cargo - that cargo
     * @return - founded percent
     * @throws MedragServiceException - didn't heared about it
     */
    @Override
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

    /**
     * Method calculates distance between two cities and time, needed for getting from one to another, basing on the truck data.
     * @param truck - data source
     * @return - array of integers, that has distance in kms in his first cell and time in minutes in second.
     * @throws MedragServiceException - if "getTripTime" fails.
     */
    @Override
    public Integer[] getTripTimeByTruck(TruckDto truck) throws MedragServiceException {
        CityDto departure = cityService.getDtoById(new CityDto(), new City(), truck.getCityId());
        CityDto destination = cityService.getDtoById(new CityDto(), new City(), truck.getDestinationId());
        return getTripTime(departure, destination);
    }
}
