package net.medrag.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * dbfs.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("dbfs")
public class DBFSController {

    /**
     * We don't need this page
     * @return dbfs.jsp
     */
    @GetMapping()
    public String showInfo() {
        return "public/DBFS";
    }
}
