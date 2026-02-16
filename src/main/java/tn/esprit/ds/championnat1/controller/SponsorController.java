package tn.esprit.ds.championnat1.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ds.championnat1.entities.Sponsor;
import tn.esprit.ds.championnat1.service.ISponsorService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sponsor")
public class SponsorController {

    private final ISponsorService sponsorService;

    // Ajouter un sponsor
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Sponsor ajouterSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.ajouterSponsor(sponsor);
    }

    // Ajouter plusieurs sponsors
    @PostMapping(value = "/addAll", consumes = "application/json", produces = "application/json")
    public List<Sponsor> ajouterSponsors(@RequestBody List<Sponsor> sponsors) {
        return sponsorService.ajouterSponsors(sponsors);
    }

    // Modifier sponsor
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public Sponsor modifierSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.modifierSponsor(sponsor);
    }

    // Supprimer sponsor
    @DeleteMapping("/delete/{id}")
    public void supprimerSponsor(@PathVariable("id") Long idSponsor) {
        sponsorService.supprimerSponsor(idSponsor);
    }

    // Afficher tous les sponsors
    @GetMapping("/all")
    public List<Sponsor> listSponsors() {
        return sponsorService.listSponsors();
    }

    // Récupérer sponsor par id
    @GetMapping("/get/{id}")
    public Sponsor recupererSponsor(@PathVariable("id") Long idSponsor) {
        return sponsorService.recupererSponsor(idSponsor);
    }

    // Archiver sponsor
    @PutMapping("/archive/{id}")
    public Boolean archiverSponsor(@PathVariable("id") Long idSponsor) {
        return sponsorService.archiverSponsor(idSponsor);
    }
}