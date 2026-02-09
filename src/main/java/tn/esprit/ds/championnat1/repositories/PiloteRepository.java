package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.entities.Sponsor;

public interface PiloteRepository extends JpaRepository<Pilote,Long> {
}
