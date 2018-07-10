package net.medrag.validator;

import net.medrag.dto.CityDto;
import net.medrag.dto.DriverDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.service.CityService;
import net.medrag.model.service.DriverService;
import net.medrag.model.service.EmployeeIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Random;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class DriverValidator implements Validator {

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

    @Override
    public boolean supports(Class<?> clazz) {
        return DriverDto.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {
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

                    driverDto.setName(driverDto.getName().trim());
                    driverDto.setSurname(driverDto.getSurname().trim());
                    driverDto.setEmail(driverDto.getEmail().trim());
                    driverDto.setCityName(city.getName().trim());
                    driverDto.setCityId(city.getId());
                    driverDto.setWorkedTime(0);
                    driverDto.setPaidTime(0);
                    driverDto.setState("REST");

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


    public DriverDto validateEdits(DriverDto driver, Errors errors) {

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
            dbDriver.setEmail(driver.getEmail().trim());
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
