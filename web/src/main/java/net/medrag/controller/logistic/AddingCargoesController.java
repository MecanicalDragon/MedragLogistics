package net.medrag.controller.logistic;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.TruckDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller handles addCargoes.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-confirmTruck")
public class AddingCargoesController {

    /**
     * Second step: adding attribute of chosen truck to the session and filtering list of global cargoes to a list
     * of cargoes of the chosen city.
     * Next point - {@link ChoosingCityController}
     *
     * @param index - index of chosen truck in session city trucks list
     */
    @PostMapping
    public String assignTruck(@RequestParam Integer index, HttpServletRequest request) {

//        Adding attribute of chosen truck in session and setting list of city trucks to null
        List<TruckDto> truckList = (List<TruckDto>) request.getSession().getAttribute("trucksInCity");
        TruckDto chosenTruck = truckList.get(index);
        request.getSession().setAttribute("chosenTruck", chosenTruck);

//        Filtering global cargoes list to cargoes in city list
        List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        List<CargoDto> filteredCargoList = new ArrayList<>();
        for (CargoDto cargoDto : cargoList) {
            if (cargoDto.getCurrentCityId().equals(chosenTruck.getCityId())) {
                filteredCargoList.add(cargoDto);
            }
        }

//        Adding to session new attribute of city cargoes list and nulling global cargoes.
        request.getSession().setAttribute("cityCargoes", filteredCargoList);

        return "logistic/addCargoes";
    }
}
