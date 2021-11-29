package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsumableController {
    ConsumableManager consumableManager = ConsumableManager.getInstance();

    @GetMapping("/ping")
    public String pingServer() {
        return "System is up!";
    }

    @PostMapping("/addConsumable")
    @ResponseStatus(HttpStatus.CREATED)
    public String addConsumable(@RequestBody String gsonString) {
        Consumable consumable = ConsumableManager.deserializeConsumable(gsonString);
        consumableManager.addConsumable(consumable);
        return gsonString;
    }

    @PostMapping("/removeConsumable")
    public String removeConsumable(@RequestBody String uuid) {
        consumableManager.removeByUUID(uuid);
        System.out.println("UUID IS " + uuid);
        return ConsumableManager.serializeConsumableList(consumableManager.getAllConsumables());
    }

    @GetMapping("/listAll")
    public String getAllConsumables() {
        return ConsumableManager.serializeConsumableList(consumableManager.getAllConsumables());
    }

    @GetMapping("/listExpired")
    public String getExpiredConsumables() {
        return ConsumableManager.serializeConsumableList(consumableManager.getExpiredConsumables());
    }

    @GetMapping("/listNonExpired")
    public String getNonExpiredConsumables() {
        return ConsumableManager.serializeConsumableList(consumableManager.getNotExpiredConsumables());
    }

    @GetMapping("/listExpiringIn7Days")
    public String getExpiringSevenDays() {
        return ConsumableManager.serializeConsumableList(consumableManager.getExpiringSevenDaysConsumables());
    }

    @GetMapping("/load")
    public String loadConsumables() {
        consumableManager.loadFile();
        return "ok";
    }

    @GetMapping("exit")
    public String saveConsumable() {
        consumableManager.writeFile();
        return "ok";
    }
}