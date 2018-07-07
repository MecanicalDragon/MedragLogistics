package net.medrag.validator;

import net.medrag.dto.CityDto;
import net.medrag.dto.TruckDto;
import net.medrag.form.TruckForm;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.CityService;
import net.medrag.model.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Typical validator for {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class TruckValidator implements Validator {

    private TruckService<TruckDto, Truck> truckService;

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TruckDto.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {

        errors.rejectValue("regNumber", "you.cheat");

    }

    public TruckDto validate(TruckForm truckForm, Errors errors) {

//        Checking parameters for not null and matching standards
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brigadeStr", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentCity", "notnull.field");

        if (!truckForm.getRegNumber().toUpperCase().matches("[A-Z]{2}\\d{5}")) {
            errors.rejectValue("regNumber", "wrong.reg.number");
        }

        TruckDto truckDto = new TruckDto();

//        return blank if hasErrors
        if (errors.hasErrors()) {
            return truckDto;
        }


//        set Registration Number or reject value, if wrong
        if (truckService.getDtoByNaturalId(truckDto, new Truck(), truckForm.getRegNumber().toUpperCase()) != null) {
            errors.rejectValue("regNumber", "truck.exists");
        } else {
            truckDto.setRegNumber(truckForm.getRegNumber().toUpperCase());
        }

//        set State (able-to-work or not or reject value
        if (truckForm.getState().equalsIgnoreCase("true") ||
                truckForm.getState().equalsIgnoreCase("false")) {
            truckDto.setState(Boolean.valueOf(truckForm.getState()));
        } else {
            errors.rejectValue("state", "wrong.state");
        }

//        initialize capacity or reject if wrong
        try {
            Integer capacity = Integer.parseInt(truckForm.getCapacity());
            if (capacity < 100 || capacity > 10000) {
                errors.rejectValue("capacity", "wrong.carrying");
            } else {
                truckDto.setCapacity(capacity);
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("capacity", "wrong.carrying");
        }

//        initialize brigade strength or reject if wrong
        try {
            Integer brigadeStr = Integer.parseInt(truckForm.getBrigadeStr());
            if (brigadeStr < 1 || brigadeStr > 6) {
                errors.rejectValue("brigadeStr", "wrong.brigadeStr");
            } else {
                truckDto.setBrigadeStr(brigadeStr);
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("brigadeStr", "wrong.brigadeStr");
        }

//        initialize current city or reject if wrong
        CityDto currentCity = cityService.getDtoByNaturalId(new CityDto(), new City(), truckForm.getCurrentCity());
        if (currentCity == null){
            errors.rejectValue("currentcity", "null.city");
        } else {
            truckDto.setCurrentCity(currentCity);
        }

        return truckDto;
    }
}
