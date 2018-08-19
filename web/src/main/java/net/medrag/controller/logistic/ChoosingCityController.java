package net.medrag.controller.logistic;

import net.medrag.domain.dto.CargoDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("mgr-chooseCity")
public class ChoosingCityController {

    /**
     * Third step - adding compiled truck load  and cities list to the session.
     * Next step - {@link AddingDriversController}
     *
     * @param cargoesList - list of chosen cargoes
     * @param request - request
     * @return - chooseCity.jsp
     */
    @PostMapping()
    public String addCargoes(@RequestParam("cargoesList") String cargoesList, HttpServletRequest request){

        if (cargoesList.length() == 0){
            return "redirect: mgr-main";
        }

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

    /**
     * Step back in wizard
     *
     * @param request - request
     * @return chooseCity.jsp
     */
    @GetMapping
    public String stepBack(HttpServletRequest request){
        request.getSession().setAttribute("destinationCity", null);
        request.getSession().setAttribute("drivers", null);
        return "logistic/chooseCity";
    }

}
