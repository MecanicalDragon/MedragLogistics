package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.City;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CityDto;
import net.medrag.model.service.dto.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    /**
     * Third step - adding compiled truck load  and cities list to the session.
     * Next step - {@link AddingDriversController}
     */
    @PostMapping()
    public String addCargoes(@RequestParam("cargoesList") String cargoesList, HttpServletRequest request) {

        List<CargoDto> cityCargoes = (List<CargoDto>) request.getSession().getAttribute("cityCargoes");

//        Getting truck load and adding it to the session
        List<CargoDto> truckLoad = new ArrayList<>();
        String[] split = cargoesList.split("/");
        for (String s : split) {
            truckLoad.add(cityCargoes.get(Integer.valueOf(s)));
        }
        request.getSession().setAttribute("truckLoad", truckLoad);

//        Getting a city list, if it's null, and adding it to the session too.
        if (request.getSession().getAttribute("cities") == null) {
            List<CityDto> cities = cityService.getDtoList(new CityDto(), new City());
            request.getSession().setAttribute("cities", cities);
        }

        return "logistic/chooseCity";
    }
}
