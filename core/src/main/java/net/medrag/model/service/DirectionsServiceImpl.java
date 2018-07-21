package net.medrag.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.model.dto.CityDto;
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

    private static final String URL = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    private static final String KEY = "&key=AIzaSyDGfoBub9yxLqFtIVYk_bwSE7Kn8SSvkdI";

    public Integer[] getTripTime(CityDto departure, CityDto destination) throws MedragServiceException{
        final String request = URL + departure.getCoordinatesX() + "," + departure.getCoordinatesY() + "&destination=" +
                destination.getCoordinatesX() + "," + destination.getCoordinatesY() + KEY;

        try (InputStream is = new URL(request).openStream()) {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            JsonNode response = new ObjectMapper().readTree(is);
            JsonNode legs = response.findValue("legs");
            JsonNode firstElement = legs.get(0);
            JsonNode distance = firstElement.get("distance").get("value");
            JsonNode duration = firstElement.get("duration").get("value");
            Integer kms = distance.asInt()/1000;
            Integer minutes = duration.asInt()/60+240;
            System.out.println(kms);
            //10560 limit
            System.out.println(minutes);
            Integer[] trip = new Integer[2];
            trip[0] = kms;
            trip[1] = minutes;

            return trip;

        } catch (IOException e) {
            throw new MedragServiceException(e);
        }

    }
}
