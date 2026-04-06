package tn.esprit.ds.championnat1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Contrat;
import tn.esprit.ds.championnat1.repositories.ContratRepository;

import java.time.LocalDate;
import java.util.List;
@Service
public class ContratService implements IContratService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ContratService.class);

    @Autowired
    private ContratRepository contratRepository;

    @Scheduled(fixedRate = 30000)
    //@Scheduled(cron = "*/60 * * * * *")
    @Override
    public void archiverContratsExpireesEtAffichageContratsActifsParEquipe() {

        List<Contrat> contrats = contratRepository.findAll();
        int anneeCourante = LocalDate.now().getYear();
        boolean contratActifTrouve = false;

        for (Contrat c : contrats) {
            Integer anneeContrat = parseAnnee(c.getAnnee());
            if (anneeContrat != null && anneeContrat < anneeCourante) {
                c.setArchived(true);
            } else {
                c.setArchived(false);
            }
        }
        contratRepository.saveAll(contrats);

        for (Contrat contrat : contrats) {
            boolean contratActif = contrat.getArchived() == null || Boolean.FALSE.equals(contrat.getArchived());
            if (!contratActif) {
                continue;
            }

            String nomEquipe = "N/A";
            if (contrat.getEquipe() != null && contrat.getEquipe().getLibelle() != null) {
                nomEquipe = contrat.getEquipe().getLibelle();
            }

            String nomSponsor = "N/A";
            if (contrat.getSponsor() != null && contrat.getSponsor().getNom() != null) {
                nomSponsor = contrat.getSponsor().getNom();
            }

            LOGGER.info("L'equipe {} a un contrat d'un momtant de {} avec le sponsor {}",
                    nomEquipe,
                    contrat.getMontant(),
                    nomSponsor);
            contratActifTrouve = true;
        }

        if (!contratActifTrouve) {
            LOGGER.info("Aucun contrat actif trouve.");
        }
    }

    private Integer parseAnnee(String annee) {
        if (annee == null || annee.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(annee.trim());
        } catch (NumberFormatException ex) {
            LOGGER.warn("Annee contrat invalide: {}", annee);
            return null;
        }
    }
}
