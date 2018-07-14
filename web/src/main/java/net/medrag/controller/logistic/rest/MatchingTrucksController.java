//package net.medrag.controller.logistic.rest;
//
//import net.medrag.model.domain.entity.Truck;
//import net.medrag.model.dto.CargoDto;
//import net.medrag.model.dto.TruckDto;
//import net.medrag.model.service.dto.TruckService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * {@link}
// *
// * @author Stanislav Tretyakov
// * @version 1.0
// */
//@RestController
//@RequestMapping("mgr-trucks")
//public class MatchingTrucksController {
//
//    private TruckService<TruckDto, Truck> truckService;
//
//    @Autowired
//    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
//        this.truckService = truckService;
//    }
//
//    @GetMapping(path = "getList", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void getMatchingTrucks() {
//
//        CargoDto cargo = new CargoDto();
//
//        System.out.println(cargo);
//        System.out.println(cargo.getClass().getSimpleName());
//        System.out.println(cargo.getClass().getName());
//        System.out.println(cargo.getClass().getCanonicalName());
//
//        List<TruckDto>trucks = truckService.getDtoList(new TruckDto(), new Truck());
//        List<TruckDto>matchingTrucks = new ArrayList<>();
//        for (TruckDto truck : trucks) {
//            if (truck.getCityName().equals("Moscow") &&
//                    truck.getStatus().equals("STAY_IDLE")){
//                matchingTrucks.add(truck);
//            }
//        }
//    }
//}