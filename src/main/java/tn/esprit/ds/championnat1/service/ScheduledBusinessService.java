package tn.esprit.ds.championnat1.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Course;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.entities.Position;
import tn.esprit.ds.championnat1.enums.Categorie;
import tn.esprit.ds.championnat1.repositories.PiloteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduledBusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledBusinessService.class);

    private final PiloteRepository piloteRepository;

    private static final Categorie CATEGORIE_CIBLE = Categorie.FORMULA1;

    @Scheduled(cron = "0 15 11 31 12 *")
    public void mettreAJourPointsEtClassementPilotesFinAnnee() {
        int anneeCourante = LocalDate.now().getYear();

        List<Pilote> pilotes = piloteRepository.findAll();

        List<Pilote> pilotesCategorie = new ArrayList<>();
        for (Pilote pilote : pilotes) {
            if (pilote.getCategorie() != CATEGORIE_CIBLE) {
                pilote.setNbPointsTotal(0);
                pilote.setClassementGeneral(0);
                continue;
            }

            int pointsAnneeCourante = calculPointsPilotePourAnnee(pilote, anneeCourante);
            pilote.setNbPointsTotal(pointsAnneeCourante);
            pilotesCategorie.add(pilote);
        }

        for (Pilote piloteCourant : pilotesCategorie) {
            int pointsCourants = piloteCourant.getNbPointsTotal() != null ? piloteCourant.getNbPointsTotal() : 0;
            int rang = 1;

            for (Pilote autrePilote : pilotesCategorie) {
                if (autrePilote == null || autrePilote.getIdPilote() == null || piloteCourant.getIdPilote() == null) {
                    continue;
                }
                if (autrePilote.getIdPilote().equals(piloteCourant.getIdPilote())) {
                    continue;
                }

                int pointsAutre = autrePilote.getNbPointsTotal() != null ? autrePilote.getNbPointsTotal() : 0;
                if (pointsAutre > pointsCourants) {
                    rang++;
                }
            }

            piloteCourant.setClassementGeneral(rang);
        }

        piloteRepository.saveAll(pilotes);
        LOGGER.info("Mise a jour points/classement terminee pour {} pilotes de categorie {}.", pilotesCategorie.size(), CATEGORIE_CIBLE);
    }

    private int calculPointsPilotePourAnnee(Pilote pilote, int anneeCourante) {
        Set<Position> positions = pilote.getPositions();
        if (positions == null || positions.isEmpty()) {
            return 0;
        }

        int sommePoints = 0;
        for (Position position : positions) {
            if (position == null || position.getNbPoints() == null) {
                continue;
            }

            Course course = position.getCourse();
            if (course == null || course.getDateCourse() == null) {
                continue;
            }

            if (course.getDateCourse().getYear() == anneeCourante) {
                sommePoints += position.getNbPoints();
            }
        }

        return sommePoints;
    }
}
