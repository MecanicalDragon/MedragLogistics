package net.medrag.controller.advice;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/drivers")
    @SendTo("/changes/inDrivers")
    public String sendDriverChanges(String message) throws Exception {
        System.out.println(message);
        return message;
    }

    @MessageMapping("/waypoints")
    @SendTo("/changes/inWaypoints")
    public String sendWaypointChanges(String message) throws Exception {
        System.out.println(message);
        return message;
    }
}
