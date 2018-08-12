package net.medrag.model.service.dto;

import net.medrag.model.dao.MedragRepositoryException;
import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.domain.dto.TruckDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.RabbitService;
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
