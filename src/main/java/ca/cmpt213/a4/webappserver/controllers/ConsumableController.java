package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The ConsumableController is responsible for responding to curl commands.
 * It adds/removes items, returns consumable lists based on expiry criteria, and loads/writes to data.json.
 */
@RestController
public class ConsumableController {
    ConsumableManager consumableManager = ConsumableManager.getInstance();

    /**
     * Returns a string greeting the user with "System is up!"
     * @return the aforementioned greeting message
     */
    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public String pingServer() {
        return "System is up!";
    }

    /**
     * Adds a new item to the consumable manager, with the body being a serialized consumable item
     * @param gsonString the json format string of one item, to be deserialized and added
     */
    @PostMapping("/addItem")
    @ResponseStatus(HttpStatus.CREATED)
    public void addConsumable(@RequestBody String gsonString) {
        Consumable consumable = ConsumableManager.deserializeConsumable(gsonString);
        consumableManager.addConsumable(consumable);
    }

    /**
     * Removes an item, given an id of the consumable to be removed
     * @param uuid the id of the item to be removed
     */
    @PostMapping("/removeItem")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeConsumable(@RequestBody String uuid) {
        consumableManager.removeByUUID(uuid);
    }

    /**
     * Responds with a serialized json string representing all consumables
     * @return a serialized json string representing all consumables
     */
    @GetMapping("/listAll")
    @ResponseStatus(HttpStatus.OK)
    public String getAllConsumables() {
        return consumableManager.getAllConsumables();
    }

    /**
     * Responds with a serialized json string representing all expired consumables
     * @return a serialized json string representing all expired consumables
     */
    @GetMapping("/listExpired")
    @ResponseStatus(HttpStatus.OK)
    public String getExpiredConsumables() {
        return consumableManager.getExpiredConsumables();
    }

    /**
     * Responds with a serialized json string representing all non-expired consumables
     * @return a serialized json string representing all non-expired consumables
     */
    @GetMapping("/listNonExpired")
    @ResponseStatus(HttpStatus.OK)
    public String getNonExpiredConsumables() {
        return consumableManager.getNotExpiredConsumables();
    }

    /**
     * Responds with a serialized json string representing all consumables expiring within seven days
     * @return a serialized json string representing all consumables expiring within seven days
     */
    @GetMapping("/listExpiringIn7Days")
    @ResponseStatus(HttpStatus.OK)
    public String getExpiringSevenDays() {
        return consumableManager.getExpiringSevenDaysConsumables();
    }

    /**
     * Loads the list from data.json; this is called upon startup of the client app
     */
    @GetMapping("/load")
    @ResponseStatus(HttpStatus.OK)
    public void loadConsumables() {
        consumableManager.loadFile();
    }

    /**
     * Saves the list of consumables to data.json; this is called upon closing of the client app
     */
    @GetMapping("exit")
    @ResponseStatus(HttpStatus.OK)
    public void saveConsumable() {
        consumableManager.writeFile();
    }
}