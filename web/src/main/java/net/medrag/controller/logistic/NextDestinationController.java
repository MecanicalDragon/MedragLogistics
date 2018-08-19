package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.entity.City;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mgr-nextDestination")
public class NextDestinationController {

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public String getNextDestination(HttpServletRequest request, @RequestParam String cargoesList, Model model) throws MedragControllerException{

        if (cargoesList.length() == 0){
            return "redirect: mgr-route";
        }

//        Getting list of cities
        List<CityDto>cities;
        if (request.getSession().getAttribute("cities") == null){
            try {
                cities = cityService.getDtoList(new CityDto(), new City());
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        } else {
            cities = (List<CityDto>) request.getSession().getAttribute("cities");
        }

//        Denoting next departure city
        TruckDto truck = (TruckDto)request.getSession().getAttribute("chosenTruck");
        CityDto departure;
        try {
            departure = cityService.getDtoById(new CityDto(), new City(), truck.getDestinationId());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

//        Forming truck load and adding it to the session
        List<CargoDto> cargoes = (List<CargoDto>) request.getSession().getAttribute("cityCargoes");
        List<CargoDto> truckLoad = new ArrayList<>();
        String[] split = cargoesList.split("/");
        for (String s : split) {
            truckLoad.add(cargoes.get(Integer.valueOf(s)));
        }

//        Adding data to the session
        request.getSession().setAttribute("departureCity", departure);
        request.getSession().setAttribute("newTruckLoad", truckLoad);
        request.getSession().setAttribute("cities", cities);
        model.addAttribute("reroute", true);

        return "logistic/chooseCity";
    }
}
