//package net.medrag.controller.warehouse;
//
//import net.medrag.model.domain.dto.CargoDto;
//import net.medrag.model.domain.dto.DriverDto;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping(path = "whm-rest", produces = MediaType.APPLICATION_JSON_VALUE)
//public class WhmRestController {
//
//    @GetMapping
//    public Set<DriverDto> getBrigade(@RequestParam Integer index, HttpServletRequest request){
//
//        System.out.println(index);
//        List<CargoDto>cargoes = (List<CargoDto>)request.getSession().getAttribute("globalCargoes");
//        Set<DriverDto>brigade =  cargoes.get(index).getCurrentTruck().getBrigade();
//        System.out.println(brigade);
//        return brigade;
//    }
//}
