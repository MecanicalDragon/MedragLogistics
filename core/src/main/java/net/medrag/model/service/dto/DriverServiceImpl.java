package net.medrag.model.service.dto;

import net.medrag.model.dao.DriverDao;
import net.medrag.model.dao.MedragRepositoryException;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.service.MedragServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard service implementation for employment DAO interface{@link DriverDao},
 * working with domain object, that represents a {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DriverServiceImpl<D extends DriverDto, E extends Driver> extends DTOServiceImpl<D, E>
        implements DriverService<D, E> {

    private static final String implementation = "driverDaoImpl";

    @Override
    @Transactional
    public void updateDtoStatus(D driver, E entity) throws MedragServiceException {

        if (!driver.getState().equals(driver.getPreviousState())) {
            assignNewTime(driver);
            driver.setPreviousState(driver.getState());
        }
        try {
            E e = (E) new ModelMapper().map(driver, entity.getClass());
            entityDao.updateEntityStatus(e);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    private void assignNewTime(D driver) {
        Instant now = Instant.now();
        int minutes = (int) Instant.ofEpochSecond(driver.getLastChange()).until(now, ChronoUnit.MINUTES);
        driver.setLastChange(now.getEpochSecond());
        switch (driver.getPreviousState()) {
            case "READY_TO_ROUTE":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + minutes);
                break;
            case "ON_SHIFT":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + (int)(1.4*minutes));
                break;
            case "DRIVING":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + (int)(1.6*minutes));
                break;
            case "PORTER":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + (int)(1.2*minutes));
                break;
            default:
        }
    }

    @Override
    @Transactional
    public List<D> getDtoList(D dto, E entity, String... args) throws MedragServiceException{
        List<E> entityList = null;
        try {
            entityList = entityDao.getEntityList(entity, args);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            D driver = (D) new ModelMapper().map(e, dto.getClass());
            assignNewTime(driver);
            dtoList.add(driver);
        }
        return dtoList;
    }

    @Override
    @Transactional
    public D getDtoByNaturalId(D dto, E entity, String id) throws MedragServiceException {
        E result = null;
        try {
            result = entityDao.getEntityByNaturalId(entity, id);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        if (result == null) {
            return null;
        } else {
            D driver = (D) new ModelMapper().map(result, dto.getClass());
            assignNewTime(driver);
            return driver;
        }
    }
}
