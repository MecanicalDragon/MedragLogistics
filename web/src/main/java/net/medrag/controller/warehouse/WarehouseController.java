package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String returnView(Model model){
        List<CargoDto> cargos = cargoService.getDtoList(new CargoDto(), new Cargo());
        model.addAttribute("cargos", cargos);
        return "warehouse/warehousePage";
    }

}
