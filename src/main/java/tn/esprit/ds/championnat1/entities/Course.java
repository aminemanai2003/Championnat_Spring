package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"championnats", "positions"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;

    @Column(nullable = false)
    private String emplacement;

    private LocalDate dateCourse;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Championnat> championnats = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Position> positions = new HashSet<>();
}
