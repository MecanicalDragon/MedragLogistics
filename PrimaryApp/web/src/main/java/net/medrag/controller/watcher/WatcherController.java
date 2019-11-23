package net.medrag.controller.watcher;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CargoForm;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.WatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * RestController for Watcher App.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@RestController
@RequestMapping(path = "watch", produces = MediaType.APPLICATION_JSON_VALUE)
public class WatcherController {

    private WatcherService watcherService;

    @Autowired
    public void setWatcherService(WatcherService watcherService) {
        this.watcherService = watcherService;
    }

    /**
     * Getting last 10 cargoes list
     * @return last 10 cargoes list
     * @throws MedragControllerException - throws MedragControllerException
     */
    @GetMapping("cargoes")
    public List<CargoForm> getLastOrders() throws MedragControllerException{
        try {
            watcherService.getStats();
            return watcherService.getCargoesList();
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
    }

    /**
     * Getting drivers and trucks info
     * @return drivers and trucks info
     * @throws MedragControllerException - throws MedragControllerException
     */
    @GetMapping("stats")
    public Integer[] getStats() throws MedragControllerException{
        try {
            watcherService.getStats();
            return watcherService.getStats();
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
    }
}
