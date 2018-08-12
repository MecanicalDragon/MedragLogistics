package net.medrag.validator;

import net.medrag.model.domain.dto.CityDto;
import net.medrag.model.domain.dto.DriverDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.time.Instant;
import java.util.Random;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class DriverValidator {

    private DriverService<DriverDto, Driver> driverService;

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    public void validate(@Nullable Object target, Errors errors)throws MedragServiceException {
        DriverDto driverDto = (DriverDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName", "notnull.field");

        if (!errors.hasErrors()) {

            if (!driverDto.getName().trim().matches("\\w\\D+")) {
                errors.rejectValue("name", "letters.only");
            }
            if (!driverDto.getSurname().trim().matches("\\w\\D+")) {
                errors.rejectValue("surname", "letters.only");
            }
            if (!driverDto.getEmail().matches("\\w+@\\w+\\.\\w+")){
                errors.rejectValue("email", "not.email");
            }

            if (!errors.hasErrors()) {

                CityDto city = cityService.getDtoByNaturalId(new CityDto(), new City(), driverDto.getCityName());

                if (city == null) {
                    errors.rejectValue("cityName", "null.city");
                } else {

                    driverDto.setLastChange(Instant.now().getEpochSecond());
                    driverDto.setName(driverDto.getName().trim());
                    driverDto.setSurname(driverDto.getSurname().trim());
                    driverDto.setEmail(driverDto.getEmail().trim());
                    driverDto.setCityName(city.getName().trim());
                    driverDto.setCityId(city.getId());
                    driverDto.setWorkedTime(0);
                    driverDto.setPaidTime(0);
                    driverDto.setState("REST");
                    driverDto.setPreviousState("REST");

                    do {
                        int random = new Random().nextInt(89999) + 10000;
                        String pn = "DRV-" + random;
                        driverDto.setPersonalNumber(pn);
                    }
                    while (driverService.getDtoByNaturalId(driverDto, new Driver(), driverDto.getPersonalNumber()) != null);
                }
            }
        }
    }

    public DriverDto validateEdits(DriverDto driver, Errors errors)throws MedragServiceException {

        DriverDto dbDriver = driverService.getDtoById(driver, new Driver(), driver.getId());

        if (driver.getName().trim().length() > 0) {
            if (!driver.getName().matches("\\w\\D+")) {
                errors.rejectValue("name", "letters.only");
            } else {
                dbDriver.setName(driver.getName());
            }
        }

        if (driver.getSurname().trim().length() > 0) {
            if (!driver.getSurname().matches("\\w\\D+")) {
                errors.rejectValue("surname", "letters.only");
            } else {
                dbDriver.setSurname(driver.getSurname());
            }
        }

        if (driver.getEmail().trim().length() > 0) {
            if (!driver.getEmail().matches("\\w+@\\w+\\.\\w+")){
                errors.rejectValue("email", "not.email");
            } else {
                String tempEmail = dbDriver.getEmail();
                dbDriver.setEmail(driver.getEmail());
                driver.setEmail(tempEmail);
            }
        }

        if (driver.getCityName().trim().length() > 0) {
            CityDto city = cityService.getDtoByNaturalId(new CityDto(), new City(), driver.getCityName());
            if (city == null) {
                errors.rejectValue("cityName", "null.city");
            } else {
                dbDriver.setCityId(city.getId());
                dbDriver.setCityName(city.getName());
            }
        }

        return dbDriver;
    }
}
