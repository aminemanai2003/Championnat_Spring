package tn.esprit.ds.championnat1.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.service.IPiloteService;

@RestController
@AllArgsConstructor
@RequestMapping("/pilote")
public class PiloteController {

    private final IPiloteService piloteService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public String addPilote(@RequestBody Pilote p) {
        return piloteService.addPilote(p);
    }
}