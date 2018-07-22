package net.medrag.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dbfs")
public class DBFSController {

    @GetMapping()
    public String showInfo() {
        return "public/DBFS";
    }
}
