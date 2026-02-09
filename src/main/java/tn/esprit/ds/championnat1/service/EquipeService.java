package tn.esprit.ds.championnat1.service;

import tn.esprit.ds.championnat1.entities.Equipe;
import tn.esprit.ds.championnat1.repositories.EquipeRepository;
import tn.esprit.ds.championnat1.repositories.PiloteRepository;

public class EquipeService implements IEquipeService{
    private final EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }


    @Override
    public Equipe ajouterEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }
}
