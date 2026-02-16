package tn.esprit.ds.championnat1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"sponsor", "equipe"})
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    private Float montant;

    private String annee;

    private Boolean archived;

    @JsonBackReference("sponsor-contrats")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @JsonBackReference("equipe-contrats")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
}
