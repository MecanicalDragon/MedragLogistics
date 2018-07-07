package net.medrag.validator;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CityDto;
import net.medrag.form.CargoForm;
import net.medrag.model.dao.CargoDao;
import net.medrag.model.domain.entity.City;
import net.medrag.model.service.CityService;
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

        errors.rejectValue("name", "you.cheat");
    }

    public CargoDto validate(@Nullable CargoForm cargoForm, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departure", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destination", "notnull.field");

        CargoDto cargo = new CargoDto();

        if (errors.hasErrors()) {
            return cargo;
        }

        try {
            Float weight = Float.parseFloat(cargoForm.getWeight());
            if (weight < 2) {
                errors.rejectValue("weight", "too.easy");
            } else {
                cargo.setWeight(weight);
            }

        } catch (NumberFormatException e) {
            errors.rejectValue("weight", "not.a.number");
        }

        CityDto departure = cityService.getDtoByNaturalId(new CityDto(), new City(), cargoForm.getDeparture());
        if (departure == null) {
            errors.rejectValue("departure", "null.city");
        } else {
            cargo.setDeparture(departure);
        }

        CityDto destination = cityService.getDtoByNaturalId(new CityDto(), new City(), cargoForm.getDestination());
        if (destination == null) {
            errors.rejectValue("destination", "null.city");
        } else {
            cargo.setDestination(destination);
        }

        cargo.setName(cargoForm.getName());

        return cargo;
    }
}
