package net.medrag.model.service;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.dto.OrderDto;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderCompilingService {
    public OrderDto compileOrder(List<CargoDto> cargoList, CustomerDto customer);
}
