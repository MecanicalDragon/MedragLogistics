package net.medrag.service.dto.impl;

import net.medrag.dao.api.DriverDao;
import net.medrag.dao.MedragRepositoryException;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.enums.DriverState;
import net.medrag.service.api.MailService;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.RabbitService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.impl.DriverHandlerServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for DAO interface{@link DriverDao},
 * working with domain object, that represents a {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DriverServiceImpl<D extends DriverDto, E extends Driver> extends DTOServiceImpl<D, E>
        implements DriverService<D, E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverHandlerServiceImpl.class);

    /**
     * String, that denotes correct implementation for generalised superclass
     */
    private static final String implementation = "driverDaoImpl";

    /**
     * Service for sendimg MQ to the Watcher app
     */
    private RabbitService rabbitService;

    /**
     * Service for sending emails
     */
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setRabbitService(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    /**
     * Updates driver state, if it was changed. Sends MQ to watcher app.
     *
     * @param driver - updated driver
     * @param entity - blank jf Driver object for ModelMapper.
     * @throws MedragServiceException - i think not.
     */
    @Override
    @Transactional
    public void updateDtoStatus(D driver, E entity) throws MedragServiceException {
        String message = null;

        if (!driver.getState().equals(driver.getPreviousState())) {
            assignNewTime(driver);
            if (!((driver.getPreviousState().equals(DriverState.PORTER) ||
                    driver.getPreviousState().equals(DriverState.ON_SHIFT) ||
                    driver.getPreviousState().equals(DriverState.DRIVING))
                    &&
                    (driver.getState().equals(DriverState.PORTER) ||
                            driver.getState().equals(DriverState.ON_SHIFT) ||
                            driver.getState().equals(DriverState.DRIVING)))) {
                message = "driver->" + driver.getPreviousState().toString() + "->" + driver.getState().toString();
            }
            driver.setPreviousState(driver.getState());
        }
        super.updateDtoStatus(driver, entity);
        if (message != null) {
            rabbitService.sendMessage(message);
        }
    }

    /**
     * Mathod calculates worked and paid time, founding on LastChange parameter. Updates it with current time.
     *
     * @param driver - drives trucks.
     */
    private void assignNewTime(D driver) {
        Instant now = Instant.now();
        int minutes = (int) Instant.ofEpochSecond(driver.getLastChange()).until(now, ChronoUnit.MINUTES);
        driver.setLastChange(now.getEpochSecond());
        switch (driver.getPreviousState().toString()) {
            case "READY_TO_ROUTE":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + minutes);
                break;
            case "ON_SHIFT":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + (int) (1.4 * minutes));
                break;
            case "DRIVING":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + (int) (1.6 * minutes));
                break;
            case "PORTER":
                driver.setWorkedTime(driver.getWorkedTime() + minutes);
                driver.setPaidTime(driver.getPaidTime() + (int) (1.2 * minutes));
                break;
            default:
        }
    }

    /**
     * Method extract from database list of all available drivers, calculates actual worked and paid times each of them
     * and checks if their workedTime doesn't exceed limit of 176 hours. If it does, driver will be saved in database
     * with REST state, and an email will be send to the real driver with information about it.
     *
     * @param dto    - blank dto of driver for ModelMapper
     * @param entity - blank entity of driver for ModelMapper
     * @param args   - params for filtering drivers list (will be flashed to database as a column and parameter for each pair)
     * @return list of drivers, that doesn't exceed limit of time.
     * @throws MedragServiceException - never was yet.
     */
    @Override
    @Transactional
    public List<D> getDtoList(D dto, E entity, String... args) throws MedragServiceException {
        List<E> entityList;
        try {
            entityList = entityDao.getEntityList(entity, args);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            D driver = (D) new ModelMapper().map(e, dto.getClass());
            assignNewTime(driver);
            if (driver.getWorkedTime() >= 10560 && driver.getState().equals(DriverState.READY_TO_ROUTE)) {
                driver.setState(DriverState.REST);
                updateDtoStatus(driver, (E) new Driver());
                new Thread(() -> {
                    try {
                        mailService.sendWorkedTimeLimitMail(driver);
                    } catch (MessagingException e1) {
                        LOGGER.error("Email to driver {} hasn't been sent. {}", driver.getPersonalNumber(), e1);
                    }
                }).start();
            } else {
                dtoList.add(driver);
            }
        }
        return dtoList;
    }

    /**
     * Finds in database driver with denoted name.
     *
     * @param dto    - blank dto of driver for ModelMapper
     * @param entity - blank entity of driver for ModelMapper
     * @param id     - unique driver personal number
     * @return - driver with personalNumber of {@param id}
     * @throws MedragServiceException - praying fo not
     */
    @Override
    @Transactional
    public D getDtoByNaturalId(D dto, E entity, String id) throws MedragServiceException {
        E result;
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
