package net.medrag.controller.advice;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Websocket controller
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
public class WebSocketController {

    /**
     * drivers message queue
     */
    @MessageMapping("/drivers")
    @SendTo("/changes/inDrivers")
    public String sendDriverChanges(String message) throws Exception {
        System.out.println(message);
        return message;
    }

    /**
     * waypoints message queue
     */
    @MessageMapping("/waypoints")
    @SendTo("/changes/inWaypoints")
    public String sendWaypointChanges(String message) throws Exception {
        System.out.println(message);
        return message;
    }
}
