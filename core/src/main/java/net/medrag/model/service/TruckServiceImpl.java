package net.medrag.model.service;

import net.medrag.dto.Dto;
import net.medrag.model.dao.CityDao;
import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Entity;
import net.medrag.model.domain.entity.Truck;
import net.medrag.dto.TruckDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

}
