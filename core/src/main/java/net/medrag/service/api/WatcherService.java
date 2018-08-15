package net.medrag.service.api;

import net.medrag.domain.dto.CargoForm;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface WatcherService {

    List<CargoForm> getCargoesList() throws MedragServiceException;
    Integer[] getStats() throws MedragServiceException;
}
