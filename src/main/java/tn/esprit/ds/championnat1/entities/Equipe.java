package tn.esprit.ds.championnat1.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference("equipe-pilotes")
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pilote> pilotes = new HashSet<>();

    @JsonManagedReference("equipe-contrats")
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contrat> contrats = new HashSet<>();
}
