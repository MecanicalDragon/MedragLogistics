package net.medrag.validator;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CityDto;
import net.medrag.model.dao.CargoDao;
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
public class CargoValidator implements Validator {

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CargoDao.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {

        CargoDto cargo = (CargoDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureName", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationName", "notnull.field");

        if (errors.hasErrors()) {
            return;
        }

        try {
            Float weight = Float.parseFloat(cargo.getWeight());
            if (weight < 2) {
                errors.rejectValue("weight", "too.easy");
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("weight", "not.a.number");
        }

        CityDto departure = cityService.getDtoByNaturalId(new CityDto(), new City(), cargo.getDepartureName());
        if (departure == null) {
            errors.rejectValue("departureName", "null.city");
        } else {
            cargo.setDepartureId(departure.getId());
            cargo.setCurrentCityId(departure.getId());
            cargo.setCurrentCityName(departure.getName());
        }

        CityDto destination = cityService.getDtoByNaturalId(new CityDto(), new City(), cargo.getDestinationName());
        if (destination == null) {
            errors.rejectValue("destinationName", "null.city");
        } else {
            cargo.setDestinationId(destination.getId());
        }

    }

}
