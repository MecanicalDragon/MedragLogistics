package net.medrag.model.service;

import net.medrag.model.domain.dto.UserDto;

/**
 * Interface for EmployeeIdentifierServiceImpl.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface EmployeeIdentifierService {
    void identifyEmployee(UserDto user) throws MedragServiceException;
    String generatePassword() throws MedragServiceException;
    void generateNewPassword(Integer id) throws MedragServiceException;
    void removeUserIfItsDriver(UserDto user) throws MedragServiceException;
}
