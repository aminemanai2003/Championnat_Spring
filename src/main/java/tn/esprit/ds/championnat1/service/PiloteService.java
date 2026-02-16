package tn.esprit.ds.championnat1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.repositories.PiloteRepository;

@Service
@AllArgsConstructor
public class PiloteService implements IPiloteService {

    private final PiloteRepository piloteRepository;

    @Override
    public String addPilote(Pilote p) {
        Pilote piloteSaved = piloteRepository.save(p);
        return "Pilote ajouté avec succès : " + piloteSaved.getLibelleP();
    }
}