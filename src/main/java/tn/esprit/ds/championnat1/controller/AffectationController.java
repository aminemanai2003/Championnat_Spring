package tn.esprit.ds.championnat1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ds.championnat1.dto.ContratDto;
import tn.esprit.ds.championnat1.dto.PiloteDto;
import tn.esprit.ds.championnat1.entities.Championnat;
import tn.esprit.ds.championnat1.entities.Contrat;
import tn.esprit.ds.championnat1.entities.DetailChampionnat;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.service.IAffectationService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/affectation")
public class AffectationController {

    private final IAffectationService affectationService;

    @PostMapping(value = "/championnat/add-and-associate-courses", consumes = "application/json", produces = "application/json")
    public Championnat addChampAndAssociatedCourses(@RequestBody Championnat championnat) {
        return affectationService.addChampAndAssociatedCourses(championnat);
    }

    @PostMapping(value = "/detail-championnat/add-and-affect/{idC}", consumes = "application/json", produces = "application/json")
    public DetailChampionnat ajouterEtAffecterDetailChampionnatAChampionnat(
            @RequestBody DetailChampionnat detailChampionnat,
            @PathVariable("idC") long idC) {
        return affectationService.ajouterEtAffecterDetailChampionnatAChampionnat(detailChampionnat, idC);
    }

    @PutMapping(value = "/pilote/affecter/{libP}/{idEq}", produces = "application/json")
    public Pilote affecterPiloteAEquipe(
            @PathVariable("libP") String libP,
            @PathVariable("idEq") String idEq) {
        return affectationService.affecterPiloteAEquipe(libP, idEq);
    }

    @PutMapping(value = "/course/affecter/{idCourse}/{idChampionnat}", produces = "text/plain")
    public String affecterCourseAChampionnat(
            @PathVariable("idCourse") long idCourse,
            @PathVariable("idChampionnat") long idChampionnat) {
        return affectationService.affecterCourseAChampionnat(idCourse, idChampionnat);
    }

    @PostMapping(value = "/contrat/add-and-affect/{idEQ}/{nomSponsor}/{pays}", consumes = "application/json", produces = "application/json")
    public Contrat ajouterContratEtAffecterASponsorEtEquipe(
            @RequestBody Contrat contrat,
            @PathVariable("idEQ") String idEQ,
            @PathVariable("nomSponsor") String nomSponsor,
            @PathVariable("pays") String pays) {
        return affectationService.ajouterContratEtAffecterASponsorEtEquipe(contrat, idEQ, nomSponsor, pays);
    }

    // ============================================================
    // Services avances (TP 1.1 / 1.2 / 1.3)
    // ============================================================

    /**
     * 1.1 — GET /affectation/historique-contrats/{libelleEquipe}
     * Retourne la somme des montants de contrats par annee pour une equipe.
     * Exemple : { "2024": 50000.0, "2025": 70000.0 }
     */
    @GetMapping(value = "/historique-contrats/{libelleEquipe}", produces = "application/json")
    public HashMap<String, Float> historiqueContratsEquipe(
            @PathVariable("libelleEquipe") String libelleEquipe) {
        return affectationService.historiqueContratsEquipe(libelleEquipe);
    }

    /**
     * 1.2 — GET /affectation/points-pilotes/{idEquipe}/{idChampionnat}/{annee}
     * Retourne le total de points marques par tous les pilotes d'une equipe
     * pour un championnat et une annee donnes.
     */
    @GetMapping(value = "/points-pilotes/{idEquipe}/{idChampionnat}/{annee}", produces = "application/json")
    public Integer nbPointsParPilotesUneEquipeChampionnatPourUneAnne(
            @PathVariable("idEquipe") Long idEquipe,
            @PathVariable("idChampionnat") Long idChampionnat,
            @PathVariable("annee") String annee) {
        return affectationService.nbPointsParPilotesUneEquipeChampionnatPourUneAnne(idEquipe, idChampionnat, annee);
    }

    /**
     * 1.3 — GET /affectation/moyenne-positions/{libelleP}?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
     * Retourne la moyenne des classements d'un pilote entre deux dates.
     */
    @GetMapping(value = "/moyenne-positions/{libelleP}", produces = "application/json")
    public Float moyennePositionsEntreDeuxDate(
            @PathVariable("libelleP") String libelleP,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return affectationService.moyennePositionsEntreDeuxDate(startDate, endDate, libelleP);
    }

    // ============================================================
    // TP DTO (2.1 / 2.2)
    // ============================================================

    /**
     * 2.1 — GET /affectation/winners?annee=2024
     * Retourne le gagnant (PiloteDto) de chaque championnat
     * dont l'annee est strictement superieure au parametre.
     */
    @GetMapping(value = "/winners", produces = "application/json")
    public List<PiloteDto> listeWinners(@RequestParam("annee") Integer annee) {
        return affectationService.listeWinners(annee);
    }

    /**
     * 2.2 — POST /affectation/contrat/add-dto/{libelleEquipe}/{nomSponsor}/{pays}
     * Ajoute un contrat et retourne un ContratDto (sans references circulaires).
     * Utilise le libelle de l'equipe au lieu de son ID numerique.
     */
    @PostMapping(value = "/contrat/add-dto/{libelleEquipe}/{nomSponsor}/{pays}",
                 consumes = "application/json",
                 produces = "application/json")
    public ContratDto ajoutContratEtAffecterASponsorEtEquipe(
            @RequestBody Contrat contrat,
            @PathVariable("libelleEquipe") String libelleEquipe,
            @PathVariable("nomSponsor")    String nomSponsor,
            @PathVariable("pays")          String pays) {
        return affectationService.ajoutContratEtAffecterASponsorEtEquipe(contrat, libelleEquipe, nomSponsor, pays);
    }
}
