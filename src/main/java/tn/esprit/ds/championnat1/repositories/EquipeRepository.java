package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ds.championnat1.entities.Equipe;

import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    Optional<Equipe> findByLibelle(String libelle);
}
