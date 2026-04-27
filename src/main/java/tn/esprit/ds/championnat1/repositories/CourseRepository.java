package tn.esprit.ds.championnat1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ds.championnat1.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
