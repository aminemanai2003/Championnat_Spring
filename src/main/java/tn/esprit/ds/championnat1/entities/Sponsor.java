package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sponsor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "contrats")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSponsor;

    @Column(nullable = false)
    private String nom;

    private String pays;

    private Float budgetAnnuel;

    private Boolean bloquerContrat;
    private Boolean archived;
    LocalDate datecreation;
    LocalDate dateDerniereModificiation;


    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contrat> contrats = new HashSet<>();
}
