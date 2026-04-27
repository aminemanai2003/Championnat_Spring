package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ds.championnat1.entities.DetailChampionnat;

public interface DetailChampionnatRepository extends JpaRepository<DetailChampionnat, String> {
}
