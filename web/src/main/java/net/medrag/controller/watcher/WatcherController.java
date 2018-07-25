package net.medrag.controller.watcher;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.WatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * {@link}
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

    @GetMapping
    public List<CargoDto> getLastOrders() throws MedragControllerException{

        try {
            return watcherService.getCargoesList();
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
    }
}
