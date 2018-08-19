package net.medrag.validator;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.entity.City;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Class for validating {@link CargoDto}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class CargoValidator{

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    /**
     * Validating new Cargo
     * @param target - new cargo
     * @param errors - {@link Errors}
     * @throws MedragServiceException - throws.
     */
    public void validate(@Nullable Object target, Errors errors)throws MedragServiceException {

        CargoDto cargo = (CargoDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureName", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationName", "notnull.field");

        if (errors.hasErrors()) {
            return;
        }

        try {
            Integer weight = Integer.parseInt(cargo.getWeight());
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
