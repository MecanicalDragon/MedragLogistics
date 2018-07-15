package net.medrag.controller.warehouse;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.service.dto.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles warehousePage
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-main")
public class WarehouseController {

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request){
        List<CargoDto> cargos = cargoService.getDtoList(new CargoDto(), new Cargo());
        request.getSession().setAttribute("globalCargoes", cargos);
        return "warehouse/warehouse";
    }

    @GetMapping("changeState")
    public String deliver(@RequestParam Integer index, @RequestParam Integer op, HttpServletRequest request) {
        List<CargoDto> cargoes = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        CargoDto deliveredCargo = cargoes.get(index);
        switch (op) {
            case 0:
                deliveredCargo.setState("TRANSIENT");
                break;
            case 1:
                deliveredCargo.setState("PREPARED");
                break;
            case 2:
                deliveredCargo.setState("ON_BOARD");
                break;
            case 3:
                deliveredCargo.setState("DESTINATION");
                break;
            case 4:
                deliveredCargo.setState("DELIVERED");
                break;
        }
        cargoService.updateDtoStatus(deliveredCargo, new Cargo());

        return "redirect: ../whm-main";
    }

}
