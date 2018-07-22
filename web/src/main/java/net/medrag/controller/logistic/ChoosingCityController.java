package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.City;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller handles chooseCity.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-addCargoes")
public class ChoosingCityController {

    /**
     * Third step - adding compiled truck load  and cities list to the session.
     * Next step - {@link AddingDriversController}
     */
    @PostMapping()
    public String addCargoes(@RequestParam("cargoesList") String cargoesList, HttpServletRequest request){

        List<CargoDto> cityCargoes = (List<CargoDto>) request.getSession().getAttribute("cityCargoes");

//        Getting truck load and adding it to the session
        List<CargoDto> truckLoad = new ArrayList<>();
        String[] split = cargoesList.split("/");
        for (String s : split) {
            truckLoad.add(cityCargoes.get(Integer.valueOf(s)));
        }
        request.getSession().setAttribute("truckLoad", truckLoad);


        return "logistic/chooseCity";
    }

}
