package net.medrag.model.service;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.dto.OrderDto;
import net.medrag.model.domain.entity.Orderr;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Standard service implementation for employment DAO interface{@link net.medrag.model.dao.OrderDao},
 * working with domain object, that represents a {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class OrderServiceImpl<D extends OrderDto, E extends Orderr> extends DTOServiceImpl<D, E>
        implements OrderService<D, E> {

    private final String implementation = "orderDaoImpl";
    private final String index = "ORD";


    @Override
    @Transactional
    public OrderDto compileOrder(List<CargoDto> cargoList, CustomerDto customer) {
        for (CargoDto cargoDto : cargoList) {
            cargoDto.setOwner(customer);
            setNumber(cargoDto);
        }





        return null;
    }

    private void setNumber(CargoDto cargoDto) {
        Date date = new Date();

    }
}
