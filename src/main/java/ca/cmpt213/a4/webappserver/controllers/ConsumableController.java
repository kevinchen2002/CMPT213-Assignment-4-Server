package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsumableController {
    ConsumableManager consumableManager = ConsumableManager.getInstance();

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public String pingServer() {
        return "System is up!";
    }

    @PostMapping("/addItem")
    @ResponseStatus(HttpStatus.CREATED)
    public String addConsumable(@RequestBody String gsonString) {
        Consumable consumable = ConsumableManager.deserializeConsumable(gsonString);
        consumableManager.addConsumable(consumable);
        return consumableManager.getAllConsumables();
    }

    @PostMapping("/removeItem")
    @ResponseStatus(HttpStatus.OK)
    public String removeConsumable(@RequestBody String uuid) {
        consumableManager.removeByUUID(uuid);
        return consumableManager.getAllConsumables();
    }

    @GetMapping("/listAll")
    @ResponseStatus(HttpStatus.OK)
    public String getAllConsumables() {
        return consumableManager.getAllConsumables();
    }

    @GetMapping("/listExpired")
    @ResponseStatus(HttpStatus.OK)
    public String getExpiredConsumables() {
        return consumableManager.getExpiredConsumables();
    }

    @GetMapping("/listNonExpired")
    @ResponseStatus(HttpStatus.OK)
    public String getNonExpiredConsumables() {
        return consumableManager.getNotExpiredConsumables();
    }

    @GetMapping("/listExpiringIn7Days")
    @ResponseStatus(HttpStatus.OK)
    public String getExpiringSevenDays() {
        return consumableManager.getExpiringSevenDaysConsumables();
    }

    @GetMapping("/load")
    @ResponseStatus(HttpStatus.OK)
    public void loadConsumables() {
        consumableManager.loadFile();
    }

    @GetMapping("exit")
    @ResponseStatus(HttpStatus.OK)
    public void saveConsumable() {
        consumableManager.writeFile();
    }
}