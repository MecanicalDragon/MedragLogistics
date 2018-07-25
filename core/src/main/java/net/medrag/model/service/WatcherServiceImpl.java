package net.medrag.model.service;

import com.rabbitmq.client.*;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.service.dto.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
//@WebService(endpointInterface = "net.medrag.model.service.WatcherService")
public class WatcherServiceImpl implements WatcherService {

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @Override
    public List<CargoDto> getCargoesList() throws MedragServiceException {

        System.out.println("here will be rabbit");

        return cargoService.getDtoList(new CargoDto(), new Cargo());
    }
}
