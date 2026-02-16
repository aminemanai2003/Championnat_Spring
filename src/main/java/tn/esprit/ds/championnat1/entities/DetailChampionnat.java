package tn.esprit.ds.championnat1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detail_championnat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "championnat")
public class DetailChampionnat {

    @Id
    private String idDetailChamp;

    @Column(nullable = false)
    private String code;

    private String description;

    @JsonBackReference("championnat-detail")
    @OneToOne(mappedBy = "detailChampionnat")
    private Championnat championnat;
}
