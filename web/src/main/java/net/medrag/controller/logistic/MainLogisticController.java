package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.service.dto.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-main")
public class MainLogisticController {

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request){
        List<CargoDto> cargos = cargoService.getDtoList(new CargoDto(), new Cargo());
        List<CargoDto> filteredCargos = new ArrayList<>();
        for (CargoDto cargo : cargos) {
            if (cargo.getState().equals("TRANSIENT")){
                filteredCargos.add(cargo);
            }
        }
        request.getSession().setAttribute("globalCargoes", filteredCargos);
        return "logistic/main";
    }
}
