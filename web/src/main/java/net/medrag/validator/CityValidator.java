package net.medrag.validator;

import net.medrag.model.dto.CityDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.service.dto.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class CityValidator implements Validator {

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CityDto.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {
        CityDto city = (CityDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "index", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "coordinatesX", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "coordinatesY", "notnull.field");

        if (cityService.getDtoByNaturalId(city, new City(), city.getName()) != null) {
            errors.rejectValue("name", "city.exists");
        }

        if (!city.getName().matches("\\w+")) {
            errors.rejectValue("name", "letters.only");
        }

        try {
            Integer.parseInt(city.getCoordinatesX());
        } catch (NumberFormatException e) {
            errors.rejectValue("coordinatesX", "NaN");
        }

        try {
            Integer.parseInt(city.getCoordinatesY());
        } catch (NumberFormatException e) {
            errors.rejectValue("coordinatesY", "NaN");
        }

    }

    public CityDto validateEdits(CityDto city, Errors errors) {

        CityDto dbCity = cityService.getDtoById(city, new City(), city.getId());

        if (city.getName().trim().length() > 0) {
            CityDto namedCity = cityService.getDtoByNaturalId(city, new City(), city.getName());
            if (namedCity == null) {
                if (city.getName().trim().matches("\\w+")) {
                    dbCity.setName(city.getName().trim());
                } else {
                    errors.rejectValue("name", "letters.only");
                }
            } else {
                if (!namedCity.getName().equalsIgnoreCase(dbCity.getName())) {
                    errors.rejectValue("name", "city.exists");
                }
            }
        }

        if (city.getIndex().trim().length() > 0) {
            dbCity.setIndex(city.getIndex());
        }

        if (city.getCoordinatesX().trim().length() > 0) {
            try {
                Integer.parseInt(city.getCoordinatesX());
                dbCity.setCoordinatesX(city.getCoordinatesX());
            } catch (NumberFormatException e) {
                errors.rejectValue("coordinatesX", "NaN");
            }

        }

        if (city.getCoordinatesY().trim().length() > 0) {
            try {
                Integer.parseInt(city.getCoordinatesY());
                dbCity.setCoordinatesY(city.getCoordinatesY());
            } catch (NumberFormatException e) {
                errors.rejectValue("coordinatesY", "NaN");
            }
        }

        return dbCity;
    }
}
