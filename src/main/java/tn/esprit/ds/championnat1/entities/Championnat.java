package tn.esprit.ds.championnat1.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import tn.esprit.ds.championnat1.enums.Categorie;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "championnat")
public class Championnat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChampionnat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categorie categorie;

    @Column(nullable = false)
    private String libelleC;

    @Column(nullable = false)
    private Integer annee;

    @JsonManagedReference("championnat-detail")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_champ_id")
    private DetailChampionnat detailChampionnat;

    @JsonManagedReference("championnat-courses")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "championnat_Course",
        joinColumns = @JoinColumn(name = "championnat_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    // ===== Constructeur vide obligatoire =====
    public Championnat() {
    }

    // ===== Constructeur avec paramètres =====
    public Championnat(Categorie categorie, String libelleC, Integer annee) {
        this.categorie = categorie;
        this.libelleC = libelleC;
        this.annee = annee;
    }

    // ===== Getters & Setters =====

    public Long getIdChampionnat() {
        return idChampionnat;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getLibelleC() {
        return libelleC;
    }

    public void setLibelleC(String libelleC) {
        this.libelleC = libelleC;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public DetailChampionnat getDetailChampionnat() {
        return detailChampionnat;
    }

    public void setDetailChampionnat(DetailChampionnat detailChampionnat) {
        this.detailChampionnat = detailChampionnat;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
