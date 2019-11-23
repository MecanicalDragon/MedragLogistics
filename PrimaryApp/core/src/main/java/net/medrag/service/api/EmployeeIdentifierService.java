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

    /**
     * Adding new Employee to the database.
     *
     * @param user - new user.
     * @throws MedragServiceException - we don't employ bad people.
     */
    void identifyEmployee(UserDto user) throws MedragServiceException;

    /**
     * Just generate new password.
     *
     * @return - new random password.
     */
    String generatePassword() throws MedragServiceException;

    /**
     * Method generates new password for user.
     *
     * @param id - user id.
     * @throws MedragServiceException - if there is no database, connected to app.
     */
    void generateNewPassword(Integer id) throws MedragServiceException;
}
