package net.medrag.validator;

import net.medrag.model.dto.UserDto;
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
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        UserDto newUser = (UserDto) o;

        if (!newUser.getEmail().matches("\\w+@\\w+\\.\\w+")) {
            errors.rejectValue("email", "not.email");
        }

        switch (newUser.getRole()) {
            case "rsm":
                newUser.setRole("ROLE_RESOURCE");
                break;
            case "whm":
                newUser.setRole("ROLE_WAREHOUSEMAN");
                break;
            case "mgr":
                newUser.setRole("ROLE_MANAGER");
                break;
            default:
                newUser.setRole("ROLE_RESOURCE");
        }
    }
}
