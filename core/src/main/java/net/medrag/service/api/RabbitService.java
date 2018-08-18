package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.service.MedragServiceException;

/**
 * API for MQ service.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface RabbitService {

    /**
     * Method is being called through the fist request from the Watcher app. Activates RabbitMQ.
     *
     * @throws MedragServiceException - nope.
     */
    void run() throws MedragServiceException;

    /**
     * Method sends message with changes in truck or driver statuses.
     *
     * @param message - changes in truck or driver.
     * @throws MedragServiceException - never been.
     */
    void sendMessage(String message) throws MedragServiceException;

    /**
     * Method sends to Watcher app the changed cargo.
     *
     * @param cargo - changed cargo.
     * @throws MedragServiceException - as a previous method.
     */
    void sendCargo(CargoDto cargo) throws MedragServiceException;
}
