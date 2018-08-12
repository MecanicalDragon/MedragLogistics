package net.medrag.model.service;

import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.domain.dto.CustomerDto;
import net.medrag.model.domain.dto.OrderrDto;

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
