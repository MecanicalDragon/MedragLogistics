package net.medrag.service.dto.impl;

import net.medrag.dao.api.TruckDao;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.RabbitService;
import net.medrag.service.dto.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for DAO interface{@link TruckDao},
 * working with domain object, that represents a {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class TruckServiceImpl<D extends TruckDto, E extends Truck> extends DTOServiceImpl<D, E> implements TruckService<D, E> {

    /**
     * String, that denotes correct implementation for generalised superclass
     */
    private static final String implementation = "truckDaoImpl";

    /**
     * Service for sendimg MQ to the Watcher app
     */
    private RabbitService rabbitService;

    @Autowired
    public void setRabbitService(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    /**
     * The only goal of this overridden method is sending MQ to the watcher app, if truck status has been changed.
     * @param truck - changed truck
     * @param entity - blank entity for ModelMapper
     * @throws MedragServiceException - never been happened.
     */
    @Override
    @Transactional
    public void updateDtoStatus(D truck, E entity) throws MedragServiceException {
        String message = null;

        if (!truck.getStatus().equals(truck.getPrevStatus())) {
            message = "truck->" + truck.getPrevStatus().toString() + "->" + truck.getStatus().toString();
        }
        truck.setPrevStatus(truck.getStatus());
        super.updateDtoStatus(truck, entity);
        if (message != null) {
            rabbitService.sendMessage(message);
        }

    }

}
