package tn.esprit.ds.championnat1.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ds.championnat1.entities.Equipe;
import tn.esprit.ds.championnat1.service.EquipeService;
import tn.esprit.ds.championnat1.service.IEquipeService;

@RestController
@AllArgsConstructor
@RequestMapping("/equipe")
public class EquipeController {

    private final IEquipeService iEquipeService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Equipe ajouterEquipe(@RequestBody Equipe equipe) {
        return iEquipeService.ajouterEquipe(equipe);
    }
}