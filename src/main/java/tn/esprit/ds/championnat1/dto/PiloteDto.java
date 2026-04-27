package tn.esprit.ds.championnat1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.ds.championnat1.enums.Categorie;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PiloteDto {

    private Long      idPilote;
    private String    libelleP;
    private Categorie categorie;
    private String    libelleEquipe;

    // Informations sur le championnat gagne
    private String  libelleChampionnat;
    private Integer anneeChampionnat;
    private Integer totalPoints;
}
