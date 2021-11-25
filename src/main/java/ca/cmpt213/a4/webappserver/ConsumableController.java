package ca.cmpt213.a4.webappserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumableController {

    @GetMapping("/ping")
    public String pingServer() {
        return "System is up!";
    }
}
