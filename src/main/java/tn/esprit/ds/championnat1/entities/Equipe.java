package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pilotes", "contrats"})
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipe;

    @Column(nullable = false)
    private String libelle;

    private Integer nbPointsTotal;

    private Integer classementGeneral;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pilote> pilotes = new HashSet<>();

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contrat> contrats = new HashSet<>();
}
