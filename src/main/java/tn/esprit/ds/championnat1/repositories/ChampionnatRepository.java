package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ds.championnat1.entities.Championnat;

import java.util.List;

public interface ChampionnatRepository extends JpaRepository<Championnat, Long> {

    List<Championnat> findByAnneeGreaterThan(Integer annee);
}
