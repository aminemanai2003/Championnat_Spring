package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sponsor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSponsor;

    @Column(nullable = false)
    private String nom;

    private String pays;

    private Float budgetAnnuel;

    private Boolean bloquerContrat;
}
