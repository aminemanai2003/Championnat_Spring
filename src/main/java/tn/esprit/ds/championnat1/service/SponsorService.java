package tn.esprit.ds.championnat1.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Sponsor;
import tn.esprit.ds.championnat1.entities.Contrat;
import tn.esprit.ds.championnat1.repositories.SponsorRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SponsorService implements ISponsorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorService.class);

    private final SponsorRepository sponsorRepository;

    @Override
    public Sponsor ajouterSponsor(Sponsor sponsor) {
        // Initialiser dateCreation à la date système
        sponsor.setDateCreation(LocalDate.now());
        // Initialiser archived et bloquerContrat à false
        sponsor.setArchived(false);
        sponsor.setBloquerContrat(false);

        return sponsorRepository.save(sponsor);
    }

    @Override
    public List<Sponsor> ajouterSponsors(List<Sponsor> sponsors) {
        // Initialiser les champs pour chaque sponsor
        sponsors.forEach(sponsor -> {
            sponsor.setDateCreation(LocalDate.now());
            sponsor.setArchived(false);
            sponsor.setBloquerContrat(false);
        });

        return sponsorRepository.saveAll(sponsors);
    }

    @Override
    public Sponsor modifierSponsor(Sponsor sponsor) {
        // Initialiser dateDerniereModification à la date système
        sponsor.setDateDerniereModification(LocalDate.now());

        return sponsorRepository.save(sponsor);
    }

    @Override
    public void supprimerSponsor(Long idSponsor) {
        sponsorRepository.deleteById(idSponsor);
    }

    @Override
    public List<Sponsor> listSponsors() {
        return sponsorRepository.findAll();
    }

    @Override
    public Sponsor recupererSponsor(Long idSponsor) {
        return sponsorRepository.findById(idSponsor).orElse(null);
    }

    @Override
    public Boolean archiverSponsor(Long idSponsor) {
        Sponsor sponsor = sponsorRepository.findById(idSponsor).orElse(null);

        if (sponsor != null) {
            sponsor.setArchived(true);
            sponsorRepository.save(sponsor);
            return true;
        }

        return false;
    }

    @Override
    public float pourcentageBudgetAnnuelConsomme(Long idSponsor) {
        Sponsor sponsor = sponsorRepository.findById(idSponsor)
                .orElseThrow(() -> new IllegalArgumentException("Sponsor introuvable: " + idSponsor));

        float budgetAnnuel = sponsor.getBudgetAnnuel() != null ? sponsor.getBudgetAnnuel() : 0f;
        if (budgetAnnuel <= 0f) {
            return 0f;
        }

        int anneeCourante = LocalDate.now().getYear();
        float totalDepenses = 0f;

        for (Contrat contrat : sponsor.getContrats()) {
            if (contrat == null) {
                continue;
            }

            if (Boolean.TRUE.equals(contrat.getArchived())) {
                continue;
            }

            if (contrat.getAnnee() == null || !contrat.getAnnee().trim().equals(String.valueOf(anneeCourante))) {
                continue;
            }

            if (contrat.getMontant() != null) {
                totalDepenses += contrat.getMontant();
            }
        }

        return (totalDepenses / budgetAnnuel) * 100f;
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void afficherPourcentageBudgetDepenseChaqueLundi() {
        List<Sponsor> sponsors = sponsorRepository.findAll();

        for (Sponsor sponsor : sponsors) {
            float pourcentage = pourcentageBudgetAnnuelConsomme(sponsor.getIdSponsor());
            LOGGER.info("sponsor: {} pourcentage : {}", sponsor.getNom(), pourcentage);

            if (pourcentage > 100f) {
                sponsor.setBloquerContrat(true);
                sponsorRepository.save(sponsor);
                LOGGER.info("budget depasse!! vous ne pouvez plus faire de contrats");
            } else if (pourcentage > 70f) {
                LOGGER.info("attention budget presque consomme : {} % !", pourcentage);
            }
        }
    }
}