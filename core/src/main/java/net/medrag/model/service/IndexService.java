package net.medrag.model.service;

import net.medrag.model.domain.dto.Dto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface IndexService {
    public String indicate(Dto dto) throws MedragServiceException;
}
