package tn.esprit.ds.championnat1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Sponsor;
import tn.esprit.ds.championnat1.repositories.SponsorRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SponsorService implements ISponsorService {

    private final SponsorRepository sponsorRepository;

    @Override
    public Sponsor ajouterSponsor(Sponsor sponsor) {
        // Initialiser datecreation à la date système
        sponsor.setDatecreation(LocalDate.now());
        // Initialiser archived et bloquerContrat à false
        sponsor.setArchived(false);
        sponsor.setBloquerContrat(false);

        return sponsorRepository.save(sponsor);
    }

    @Override
    public List<Sponsor> ajouterSponsors(List<Sponsor> sponsors) {
        // Initialiser les champs pour chaque sponsor
        sponsors.forEach(sponsor -> {
            sponsor.setDatecreation(LocalDate.now());
            sponsor.setArchived(false);
            sponsor.setBloquerContrat(false);
        });

        return sponsorRepository.saveAll(sponsors);
    }

    @Override
    public Sponsor modifierSponsor(Sponsor sponsor) {
        // Initialiser dateDerniereModificiation à la date système
        sponsor.setDateDerniereModificiation(LocalDate.now());

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
}