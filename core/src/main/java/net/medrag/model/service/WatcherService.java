package net.medrag.model.service;

import net.medrag.model.dto.CargoDto;

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
//@WebService
//@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WatcherService {

//    @WebMethod
    List<CargoDto> getCargoesList() throws MedragServiceException;
}
