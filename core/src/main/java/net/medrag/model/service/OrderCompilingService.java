package net.medrag.model.service;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CustomerDto;
import net.medrag.model.dto.OrderrDto;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderCompilingService {
    public OrderrDto compileOrder(List<CargoDto> cargoList, CustomerDto customer);
}
