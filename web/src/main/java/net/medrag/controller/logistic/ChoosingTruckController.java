package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.Truck;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.dto.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller, that handles chooseTruck.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-startManage")
public class ChoosingTruckController {

    private TruckService<TruckDto, Truck> truckService;

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    /**
     * First method in RouteWizard. Main goal is getting a list of available trucks in the city of chosen cargo.
     * Next step - {@link AddingCargoesController}
     *
     * @param index - index of chosen cargo in session global cargo list
     */
    @PostMapping
    public String startManage(@RequestParam Integer index, HttpServletRequest request) {

//        Getting cargo with index 'index' from globalCargoes list
        List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        CargoDto cargo = cargoList.get(index);

//        Getting list of trucks and filtering it with the requirements of capacity, status and city dislocation
        List<TruckDto> truckList = truckService.getDtoList(new TruckDto(), new Truck(), "CURRENT_CITY_ID",
                cargo.getCurrentCityId().toString(), "STATUS", "'STAY_IDLE'");
        List<TruckDto> filteredTruckList = new ArrayList<>();
        for (TruckDto truckDto : truckList) {
            if (Integer.valueOf(truckDto.getCapacity()) >= Integer.valueOf(cargo.getWeight())) {
                filteredTruckList.add(truckDto);
            }
        }
//          Adding new attributes to the session
        request.getSession().setAttribute("trucksInCity", filteredTruckList);
        request.getSession().setAttribute("chosenCargoId", cargo.getId());

        return "logistic/chooseTruck";
    }

    @GetMapping
    public String backward(){
        return "logistic/chooseTruck";
    }
}
