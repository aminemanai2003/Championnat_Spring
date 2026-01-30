package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pilote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pilote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPilote;

    @Column(nullable = false)
    private String libelleP;

    private Integer nbPointsTotal;

    private Integer classementGeneral;
}
