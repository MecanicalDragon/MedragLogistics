package net.medrag.controller.driver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("drv-main")
public class DriverPageController {

    @GetMapping()
    public String returnView(){
        return "driver/driverPage";
    }
}
