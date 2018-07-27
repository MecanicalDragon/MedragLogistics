package net.medrag.model.service;

import net.medrag.model.dto.CargoForm;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
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
