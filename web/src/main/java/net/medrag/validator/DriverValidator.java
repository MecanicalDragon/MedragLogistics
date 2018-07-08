package net.medrag.validator;

import net.medrag.dto.DriverDto;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class DriverValidator implements Validator {

    private DriverService<DriverDto, Driver>driverService;

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

    }
}
