package tn.esprit.ds.championnat1.service;

import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.repositories.PiloteRepository;

@Service
public class PiloteService implements IPiloteService {

    private final PiloteRepository piloteRepository;

    // Injection par constructeur
    public PiloteService(PiloteRepository piloteRepository) {
        this.piloteRepository = piloteRepository;
    }

    @Override
    public String addPilote(Pilote p) {
        Pilote piloteSaved = piloteRepository.save(p);
        return "Pilote ajouté avec succès : " + piloteSaved.getLibelleP();
    }
}