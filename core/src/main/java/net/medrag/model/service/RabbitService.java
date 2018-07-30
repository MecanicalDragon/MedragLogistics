package net.medrag.model.service;

import net.medrag.model.dto.CargoDto;

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
