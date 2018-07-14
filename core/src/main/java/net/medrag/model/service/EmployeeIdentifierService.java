package net.medrag.model.service;

import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.UserDto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface EmployeeIdentifierService {
    void identifyEmployee(UserDto user);
    String generatePassword();
    void generateNewPassword(Integer id);
    void removeUserIfItsDriver(UserDto user);
    void removeUserAndDriverWithinSingleTransaction(UserDto user, DriverDto driver);
}
