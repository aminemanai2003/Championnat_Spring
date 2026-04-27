package tn.esprit.ds.championnat1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratDto {

    private Long    idContrat;
    private Float   montant;
    private String  annee;
    private Boolean archived;

    // Champs aplatis (pas de references circulaires)
    private String nomSponsor;
    private String paysSponsor;
    private String libelleEquipe;
}
