package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.manager.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.FoodItem;
import ca.cmpt213.a4.webappserver.model.Pledge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ConsumableController {
    ConsumableManager consumableManager = ConsumableManager.getInstance();

    @GetMapping("/ping")
    public String pingServer() {
        return "System is up!";
    }

//    @PostMapping("/pledges")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Pledge createNewPledge(@RequestBody Pledge pledge) {
//        // set pledge to have next id
//        pledge.setId(nextID.incrementAndGet());
//
//        pledges.add(pledge);
//        return pledge;
//    }

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

    @GetMapping("/getFirst")
    public String getFirst() {
        return ConsumableManager.serializeConsumable(consumableManager.getAllConsumables().get(0));
    }

    @PostMapping("/dummy")
    @ResponseStatus(HttpStatus.CREATED)
    public String dummy() {
        Consumable dummy = ConsumableManager.deserializeConsumable("{\"weight\":1.0,\"name\":\"Food\",\"notes\":\"This is a food!\",\"price\":1.0,\"expDate\":\"2021-11-25T01:30\",\"daysUntilExp\":-1,\"isExpired\":true,\"type\":\"food\",\"uuid\":\"93896c59-e419-4bf0-aa7a-73be212160f7\"}");
        consumableManager.addConsumable(dummy);
        //consumableManager.removeByUUID("93896c59-e419-4bf0-aa7a-73be212160f7");
        return "yes";
    }
//
//    @GetMapping("/pledges")
//    public List<Pledge> getAllPledges() {
//        return pledges;
//    }

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
//
//    @GetMapping("/pledges/{id}")
//    public Pledge getOnePledge(@PathVariable("id") long pledgeId) {
//        for (Pledge pledge : pledges) {
//            if (pledge.getId() == pledgeId) {
//                return pledge;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
//    @PostMapping("/pledges/{id}")
//    @GetMapping("/pledges/{id}")
//    public Pledge editOnePledge(
//            @PathVariable("id") long pledgeId,
//            @RequestBody Pledge newPledge
//    ) {
//        for (Pledge pledge : pledges) {
//            if (pledge.getId() == pledgeId) {
//                pledges.remove(pledge);
//                newPledge.setId(pledgeId);
//                pledges.add(newPledge);
//                return newPledge;
//            }
//        }
//        throw new IllegalArgumentException();
//    }

    // Create Exception Handler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        //nothing to do
    }
}