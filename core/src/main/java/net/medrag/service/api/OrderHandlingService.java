package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CustomerDto;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderHandlingService {

    OrderrDto compileOrder(List<CargoDto> cargoList, CustomerDto customer) throws MedragServiceException;

    void deliverCargo(CargoDto deliveredCargo) throws MedragServiceException;
}
