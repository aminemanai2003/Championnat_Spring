package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ds.championnat1.entities.Contrat;

public interface ContratRepository extends JpaRepository<Contrat, Long> {
}
