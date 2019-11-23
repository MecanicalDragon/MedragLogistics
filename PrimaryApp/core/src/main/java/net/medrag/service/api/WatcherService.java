package net.medrag.service.api;

import net.medrag.domain.dto.CargoForm;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * API for working with Watcher app and handling it's requests.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface WatcherService {

    /**
     * Method sends list of last cargoes to the Watcher App.
     *
     * @return - last 10 cargoes list.
     * @throws MedragServiceException - it's not.
     */
    List<CargoForm> getCargoesList() throws MedragServiceException;

    /**
     * Method sends information about drivers and trucks to Watcher App.
     *
     * @return - information about drivers and trucks.
     * @throws MedragServiceException - as usual.
     */
    Integer[] getStats() throws MedragServiceException;

}
