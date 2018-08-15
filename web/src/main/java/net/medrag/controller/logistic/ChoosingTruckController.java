package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("mgr-chooseTruck")
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
    public String startManage(@RequestParam Integer index, HttpServletRequest request)throws MedragControllerException {

//        Getting cargo with index 'index' from globalCargoes list
        List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        CargoDto cargo = cargoList.get(index);

//        Getting list of trucks and filtering it with the requirements of capacity, status and city dislocation
        List<TruckDto> truckList;
        try {
            truckList = truckService.getDtoList(new TruckDto(), new Truck(), "CURRENT_CITY_ID",
                    cargo.getCurrentCityId().toString(), "STATUS", "'STAY_IDLE'");
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
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
    public String stepBack(HttpServletRequest request){
        request.getSession().setAttribute("chosenTruck", null);
        request.getSession().setAttribute("brigade", null);
        return "logistic/chooseTruck";
    }

}
