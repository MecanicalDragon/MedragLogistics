package net.medrag.model.service;

import net.medrag.dto.Dto;
import net.medrag.dto.UserDto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface EmployeeIdentifierService {
    void identifyEmployee(Dto dto);
    void generateNewPassword(Integer id);
}
