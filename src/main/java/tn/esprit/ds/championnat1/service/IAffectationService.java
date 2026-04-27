package tn.esprit.ds.championnat1.service;

import tn.esprit.ds.championnat1.dto.ContratDto;
import tn.esprit.ds.championnat1.dto.PiloteDto;
import tn.esprit.ds.championnat1.entities.Championnat;
import tn.esprit.ds.championnat1.entities.Contrat;
import tn.esprit.ds.championnat1.entities.DetailChampionnat;
import tn.esprit.ds.championnat1.entities.Pilote;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface IAffectationService {

    Championnat addChampAndAssociatedCourses(Championnat c);

    DetailChampionnat ajouterEtAffecterDetailChampionnatAChampionnat(DetailChampionnat detailChampionnat, long idC);

    Pilote affecterPiloteAEquipe(String libP, String idEq);

    String affecterCourseAChampionnat(long idCourse, long idChampionnat);

    Contrat ajouterContratEtAffecterASponsorEtEquipe(Contrat c, String idEQ, String nomSponsor, String pays);

    // ============================================================
    // Services avances — TP precedent (1.1 / 1.2 / 1.3)
    // ============================================================

    HashMap<String, Float> historiqueContratsEquipe(String libelleEquipe);

    Integer nbPointsParPilotesUneEquipeChampionnatPourUneAnne(Long idEquipe,
                                                               Long idChampionnat,
                                                               String annee);

    Float moyennePositionsEntreDeuxDate(LocalDate startDate, LocalDate endDate, String libelleP);

    // ============================================================
    // TP DTO (2.1 / 2.2)
    // ============================================================

    /**
     * 2.1 — Retourne le gagnant (PiloteDto) de chaque championnat
     * dont l'annee est strictement superieure a annee.
     * Le gagnant = pilote ayant accumule le plus de points
     * sur l'ensemble des courses du championnat.
     */
    List<PiloteDto> listeWinners(Integer annee);

    /**
     * 2.2 — Ajoute un contrat et l'affecte a un sponsor et une equipe
     * en utilisant le libelle de l'equipe (plus lisible que l'ID).
     * Retourne un ContratDto sans references circulaires.
     */
    ContratDto ajoutContratEtAffecterASponsorEtEquipe(Contrat contrat,
                                                       String libelleEquipe,
                                                       String nomSponsor,
                                                       String pays);
}
