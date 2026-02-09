package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ds.championnat1.entities.Pilote;

@Repository
public interface PiloteRepository extends JpaRepository<Pilote, Long> {
}