package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.Dto;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.service.MedragServiceException;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface IndexService {

    /**
     * The only method indexes cargoes and orders with taking time and city indexes.
     *
     * @param dto - {@link CargoDto} or {@link OrderrDto}, that is needed to be indexed.
     * @return - indexed {@link CargoDto} or {@link OrderrDto}
     * @throws MedragServiceException - hasn't being happened.
     */
    String indicate(Dto dto) throws MedragServiceException;
}
