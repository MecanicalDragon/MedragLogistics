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
    void identifyEmployee(UserDto user) throws MedragServiceException;
    String generatePassword() throws MedragServiceException;
    void generateNewPassword(Integer id) throws MedragServiceException;
    void removeUserIfItsDriver(UserDto user) throws MedragServiceException;
    void removeUserAndDriverWithinSingleTransaction(UserDto user, DriverDto driver) throws MedragServiceException;
}
