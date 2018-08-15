package net.medrag.service.api;

import net.medrag.domain.dto.Dto;
import net.medrag.service.MedragServiceException;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface IndexService {
    public String indicate(Dto dto) throws MedragServiceException;
}
