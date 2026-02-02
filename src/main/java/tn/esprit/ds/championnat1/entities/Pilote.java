package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pilote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"positions", "equipe"})
public class Pilote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPilote;

    @Column(nullable = false)
    private String libelleP;

    private Integer nbPointsTotal;

    private Integer classementGeneral;

    @OneToMany(mappedBy = "pilote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Position> positions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
}
