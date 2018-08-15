package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.service.MedragServiceException;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface RabbitService {

    void run() throws MedragServiceException;

    void sendMessage(String message) throws MedragServiceException;
    void sendCargo(CargoDto cargo) throws MedragServiceException;
}
