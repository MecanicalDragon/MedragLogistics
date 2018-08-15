package net.medrag.service.api;

import net.medrag.domain.dto.UserDto;
import net.medrag.service.MedragServiceException;

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
}
