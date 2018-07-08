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
import org.springframework.validation.BindingResult;
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

//        initialize capacity or reject if wrong
        checkCapacity(truckForm, errors, truckDto);

//        initialize brigade strength or reject if wrong
        checkBrigadeStrength(truckForm, errors, truckDto);

//        initialize current city or reject if wrong
        CityDto currentCity = cityService.getDtoByNaturalId(new CityDto(), new City(), truckForm.getCurrentCity());
        if (currentCity == null) {
            errors.rejectValue("currentCity", "null.city");
        } else {
            truckDto.setCurrentCity(currentCity);
        }

        truckDto.setStatus("STAY_IDLE");

        return truckDto;
    }

    public TruckDto validateEdits(TruckForm truckForm, Errors errors) {

        TruckDto dbTruck = truckService.getDtoById(new TruckDto(), new Truck(), truckForm.getId());

        if (truckForm.getRegNumber().trim().length() > 0) {

            if (!truckForm.getRegNumber().toUpperCase().matches("[A-Z]{2}\\d{5}")) {
                errors.rejectValue("regNumber", "wrong.reg.number");
            } else {
                dbTruck.setRegNumber(truckForm.getRegNumber().toUpperCase());
            }
        }

        if (truckForm.getCapacity().trim().length() > 0) {
            checkCapacity(truckForm, errors, dbTruck);
        }

        if (truckForm.getBrigadeStr().trim().length() > 0) {
            checkBrigadeStrength(truckForm, errors, dbTruck);
        }

        if (truckForm.getCurrentCity().trim().length() > 0) {
            CityDto currentCity = cityService.getDtoByNaturalId(new CityDto(), new City(), truckForm.getCurrentCity());
            if (currentCity == null) {
                errors.rejectValue("currentCity", "null.city");
            } else {
                dbTruck.setCurrentCity(currentCity);
            }
        }

        return dbTruck;
    }

    private void checkBrigadeStrength(TruckForm truckForm, Errors errors, TruckDto bdTruck) {
        try {
            Integer brigadeStr = Integer.parseInt(truckForm.getBrigadeStr());
            if (brigadeStr < 1 || brigadeStr > 6) {
                errors.rejectValue("brigadeStr", "wrong.brigadeStr");
            } else {
                bdTruck.setBrigadeStr(brigadeStr);
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("brigadeStr", "wrong.brigadeStr");
        }
    }

    private void checkCapacity(TruckForm truckForm, Errors errors, TruckDto bdTruck) {
        try {
            Integer capacity = Integer.parseInt(truckForm.getCapacity());
            if (capacity < 100 || capacity > 10000) {
                errors.rejectValue("capacity", "wrong.carrying");
            } else {
                bdTruck.setCapacity(capacity);
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("capacity", "wrong.carrying");
        }
    }
}
