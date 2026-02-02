package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "position")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"course", "pilote"})
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPosition;

    private Integer classement;

    private Integer nbPoints;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pilote_id")
    private Pilote pilote;
}
