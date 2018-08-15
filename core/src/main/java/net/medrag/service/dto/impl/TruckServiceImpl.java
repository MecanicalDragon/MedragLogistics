package net.medrag.service.dto.impl;

import net.medrag.dao.MedragRepositoryException;
import net.medrag.dao.api.TruckDao;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.RabbitService;
import net.medrag.service.dto.api.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Standard service implementation for employment DAO interface{@link TruckDao},
 * working with domain object, that represents a {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class TruckServiceImpl<D extends TruckDto, E extends Truck> extends DTOServiceImpl<D, E> implements TruckService<D, E> {

    private static final String implementation = "truckDaoImpl";

    private RabbitService rabbitService;

    @Autowired
    public void setRabbitService(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @Override
    @Transactional
    public void updateDtoStatus(D truck, E entity) throws MedragServiceException {
        String message = null;

        if (!truck.getStatus().equals(truck.getPrevStatus())) {
                message = "truck->" + truck.getPrevStatus() + "->" + truck.getStatus();
            }
            truck.setPrevStatus(truck.getStatus());
        try {
            E e = (E) new ModelMapper().map(truck, entity.getClass());
            entityDao.updateEntityStatus(e);
            if (message != null) {
                rabbitService.sendMessage(message);
            }
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

}
