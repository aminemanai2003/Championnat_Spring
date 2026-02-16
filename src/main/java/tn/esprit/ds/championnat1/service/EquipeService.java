package tn.esprit.ds.championnat1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ds.championnat1.entities.Equipe;
import tn.esprit.ds.championnat1.repositories.EquipeRepository;

@Service
@AllArgsConstructor
public class EquipeService implements IEquipeService {
    private final EquipeRepository equipeRepository;

    @Override
    public Equipe ajouterEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }
}
