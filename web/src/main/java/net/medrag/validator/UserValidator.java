package net.medrag.validator;

import net.medrag.domain.dto.UserDto;
import net.medrag.domain.enums.UserRole;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * {@link UserDto} validator
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

    /**
     * Validating new user
     * @param o - new user
     * @param errors - {@link Errors}
     */
    @Override
    public void validate(@Nullable Object o, Errors errors) {
        UserDto newUser = (UserDto) o;

        if (!newUser.getEmail().matches("\\w+@\\w+\\.\\w+")) {
            errors.rejectValue("email", "not.email");
        }

        switch (newUser.getRole().toString()) {
            case "rsm":
                newUser.setRole(UserRole.ROLE_RESOURCE);
                break;
            case "whm":
                newUser.setRole(UserRole.ROLE_WAREHOUSEMAN);
                break;
            case "mgr":
                newUser.setRole(UserRole.ROLE_MANAGER);
                break;
            default:
                newUser.setRole(UserRole.ROLE_RESOURCE);
        }
    }
}
