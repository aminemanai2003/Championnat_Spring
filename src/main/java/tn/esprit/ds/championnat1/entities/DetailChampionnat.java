package tn.esprit.ds.championnat1.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detail_championnat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailChampionnat {

    @Id
    private String idDetailChamp;

    @Column(nullable = false)
    private String code;

    private String description;
}
