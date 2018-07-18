package net.medrag.validator;

import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.TruckService;
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
public class TruckValidator{

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

    public void validate(@Nullable Object target, Errors errors) throws MedragServiceException {
        TruckDto truck = (TruckDto) target;

//        Checking parameters for not null and matching standards
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brigadeStr", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName", "notnull.field");

        if (!truck.getRegNumber().toUpperCase().matches("[A-Z]{2}\\d{5}")) {
            errors.rejectValue("regNumber", "wrong.reg.number");
        } else {
            truck.setRegNumber(truck.getRegNumber().toUpperCase());
        }

        TruckDto truckDto = new TruckDto();

//        return blank if hasErrors
        if (!errors.hasErrors()) {


//        set Registration Number or reject value, if wrong
            if (truckService.getDtoByNaturalId(truckDto, new Truck(), truck.getRegNumber().toUpperCase()) != null) {
                errors.rejectValue("regNumber", "truck.exists");
            } else {
                truckDto.setRegNumber(truck.getRegNumber().toUpperCase());
            }

//        initialize capacity or reject if wrong
            try {
                Integer brigadeStr = Integer.parseInt(truck.getBrigadeStr());
                if (brigadeStr < 1 || brigadeStr > 6) {
                    errors.rejectValue("brigadeStr", "wrong.brigadeStr");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("brigadeStr", "wrong.brigadeStr");
            }

//        initialize brigade strength or reject if wrong
            try {
                Integer capacity = Integer.parseInt(truck.getCapacity());
                if (capacity < 100 || capacity > 10000) {
                    errors.rejectValue("capacity", "wrong.carrying");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("capacity", "wrong.carrying");
            }

//        initialize current city or reject if wrong
            CityDto currentCity = cityService.getDtoByNaturalId(new CityDto(), new City(), truck.getCityName());
            if (currentCity == null) {
                errors.rejectValue("cityName", "null.city");
            } else {
                truck.setCityId(currentCity.getId());
            }

            truck.setStatus("STAY_IDLE");
        }
    }


    public TruckDto validateEdits(TruckDto truck, Errors errors)throws MedragServiceException {

        TruckDto dbTruck = truckService.getDtoById(new TruckDto(), new Truck(), truck.getId());

        if (truck.getRegNumber().trim().length() > 0) {
            TruckDto namedTruck = truckService.getDtoByNaturalId(truck, new Truck(), truck.getRegNumber());
            if (namedTruck == null){
                if (!truck.getRegNumber().toUpperCase().matches("[A-Z]{2}\\d{5}")) {
                    errors.rejectValue("regNumber", "wrong.reg.number");
                } else {
                    dbTruck.setRegNumber(truck.getRegNumber().toUpperCase());
                }
            } else {
                if (!namedTruck.getRegNumber().equalsIgnoreCase(dbTruck.getRegNumber())) {
                    errors.rejectValue("regNumber", "truck.exists");
                }
            }

        }

        if (truck.getBrigadeStr().trim().length() > 0) {
            try {
                Integer brigadeStr = Integer.parseInt(truck.getBrigadeStr());
                if (brigadeStr < 1 || brigadeStr > 6) {
                    errors.rejectValue("brigadeStr", "wrong.brigadeStr");
                } else {
                    dbTruck.setBrigadeStr(brigadeStr.toString());
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("brigadeStr", "wrong.brigadeStr");
            }
        }

        if (truck.getCapacity().trim().length() > 0) {
            try {
                Integer capacity = Integer.parseInt(truck.getCapacity());
                if (capacity < 100 || capacity > 10000) {
                    errors.rejectValue("capacity", "wrong.carrying");
                } else {
                    dbTruck.setCapacity(capacity.toString());
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("capacity", "wrong.carrying");
            }
        }

        if (truck.getCityName().trim().length() > 0) {
            CityDto currentCity = cityService.getDtoByNaturalId(new CityDto(), new City(), truck.getCityName());
            if (currentCity == null) {
                errors.rejectValue("cityName", "null.city");
            } else {
                dbTruck.setCityName(currentCity.getName());
                dbTruck.setCityId(currentCity.getId());
            }
        }

        return dbTruck;
    }
}
