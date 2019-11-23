package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CustomerDto;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.domain.entity.Orderr;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * API for handling some {@link Orderr} requests.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderHandlingService {

    /**
     * Compiling order method. Compiles order and all it's cargoes.
     *
     * @param cargoList - list of new cargoes.
     * @param customer - owner of cargoList
     * @return - compiled order.
     * @throws MedragServiceException - if it was happening, then a long-long time ago.
     */
    OrderrDto compileOrder(List<CargoDto> cargoList, CustomerDto customer) throws MedragServiceException;

    /**
     * Method delivers cargo, and completes order, if this cargo was last in it.
     * @param deliveredCargo - delivered cargo, surprise.
     * @throws MedragServiceException - I don't remember.
     */
    void deliverCargo(CargoDto deliveredCargo) throws MedragServiceException;
}
