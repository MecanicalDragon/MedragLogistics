package net.medrag.validator;

import net.medrag.dto.CityDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
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
public class CityValidator implements Validator{

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
        CityDto city = (CityDto)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"index", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"coordinatesX", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"coordinatesY", "notnull.field");

        if (cityService.getDtoByNaturalId(city, new City(), city.getName()) != null) {
            errors.rejectValue("name", "city.exists");
        }

        try{
            Integer.parseInt(city.getCoordinatesX());
        } catch (NumberFormatException e){
            errors.rejectValue("coordinatesX", "NaN");
        }

        try{
            Integer.parseInt(city.getCoordinatesY());
        } catch (NumberFormatException e){
            errors.rejectValue("coordinatesY", "NaN");
        }

    }

    public void validateEdits(CityDto city, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"index", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"coordinatesX", "notnull.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"coordinatesY", "notnull.field");

        try{
            Integer.parseInt(city.getCoordinatesX());
        } catch (NumberFormatException e){
            errors.rejectValue("coordinatesX", "NaN");
        }

        try{
            Integer.parseInt(city.getCoordinatesY());
        } catch (NumberFormatException e){
            errors.rejectValue("coordinatesY", "NaN");
        }

    }
}
