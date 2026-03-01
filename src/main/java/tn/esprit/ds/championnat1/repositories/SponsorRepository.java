package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.ds.championnat1.entities.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor,Long> {

}
