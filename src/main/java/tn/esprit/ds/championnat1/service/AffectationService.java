package tn.esprit.ds.championnat1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.ds.championnat1.dto.ContratDto;
import tn.esprit.ds.championnat1.dto.PiloteDto;
import tn.esprit.ds.championnat1.entities.Championnat;
import tn.esprit.ds.championnat1.entities.Contrat;
import tn.esprit.ds.championnat1.entities.Course;
import tn.esprit.ds.championnat1.entities.DetailChampionnat;
import tn.esprit.ds.championnat1.entities.Equipe;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.entities.Position;
import tn.esprit.ds.championnat1.entities.Sponsor;
import tn.esprit.ds.championnat1.repositories.ChampionnatRepository;
import tn.esprit.ds.championnat1.repositories.ContratRepository;
import tn.esprit.ds.championnat1.repositories.CourseRepository;
import tn.esprit.ds.championnat1.repositories.DetailChampionnatRepository;
import tn.esprit.ds.championnat1.repositories.EquipeRepository;
import tn.esprit.ds.championnat1.repositories.PiloteRepository;
import tn.esprit.ds.championnat1.repositories.SponsorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AffectationService implements IAffectationService {

    private final ChampionnatRepository championnatRepository;
    private final CourseRepository courseRepository;
    private final DetailChampionnatRepository detailChampionnatRepository;
    private final PiloteRepository piloteRepository;
    private final EquipeRepository equipeRepository;
    private final ContratRepository contratRepository;
    private final SponsorRepository sponsorRepository;

    @Override
    public Championnat addChampAndAssociatedCourses(Championnat c) {
        Championnat championnat = championnatRepository.save(c);

        List<Course> coursesMisesAjour = new ArrayList<>();
        if (championnat.getCourses() != null) {
            coursesMisesAjour.addAll(championnat.getCourses());
        }

        if (c.getCourses() != null) {
            for (Course courseInput : c.getCourses()) {
                Course course;
                if (courseInput.getIdCourse() != null) {
                    course = courseRepository.findById(courseInput.getIdCourse()).get();
                } else {
                    course = courseRepository.save(courseInput);
                }

                if (!coursesMisesAjour.contains(course)) {
                    coursesMisesAjour.add(course);
                }
                course.getChampionnats().add(championnat);
            }
        }

        championnat.getCourses().clear();
        championnat.getCourses().addAll(coursesMisesAjour);
        championnatRepository.save(championnat);
        return championnat;
    }

    @Override
    public DetailChampionnat ajouterEtAffecterDetailChampionnatAChampionnat(DetailChampionnat detailChampionnat, long idC) {
        Championnat championnat = championnatRepository.findById(idC).get();

        championnat.setDetailChampionnat(detailChampionnat);
        detailChampionnat.setChampionnat(championnat);

        championnatRepository.save(championnat);
        return detailChampionnatRepository.save(detailChampionnat);
    }

    @Override
    public Pilote affecterPiloteAEquipe(String libP, String idEq) {
        Long equipeId = parseId(idEq, "idEq");

        Pilote pilote = piloteRepository.findByLibelleP(libP).orElse(null);
        if (pilote == null) {
            throw new NullPointerException("Pilote introuvable: " + libP);
        }

        Equipe equipe = equipeRepository.findById(equipeId).get();

        List<Pilote> pilotesMisesAjour = new ArrayList<>();
        if (equipe.getPilotes() != null) {
            pilotesMisesAjour.addAll(equipe.getPilotes());
        }

        if (!pilotesMisesAjour.contains(pilote)) {
            pilotesMisesAjour.add(pilote);
        }

        pilote.setEquipe(equipe);
        equipe.getPilotes().clear();
        equipe.getPilotes().addAll(pilotesMisesAjour);

        equipeRepository.save(equipe);
        return piloteRepository.save(pilote);
    }

    @Override
    public String affecterCourseAChampionnat(long idCourse, long idChampionnat) {
        Course course = courseRepository.findById(idCourse).get();
        Championnat championnat = championnatRepository.findById(idChampionnat).get();

        List<Course> coursesMisesAjour = new ArrayList<>();
        if (championnat.getCourses() != null) {
            coursesMisesAjour.addAll(championnat.getCourses());
        }

        if (coursesMisesAjour.contains(course)) {
            return "La course est deja associee a ce championnat.";
        }

        coursesMisesAjour.add(course);
        championnat.getCourses().clear();
        championnat.getCourses().addAll(coursesMisesAjour);
        course.getChampionnats().add(championnat);

        championnatRepository.save(championnat);
        courseRepository.save(course);

        return "Course affectee au championnat avec succes.";
    }

    @Override
    public Contrat ajouterContratEtAffecterASponsorEtEquipe(Contrat c, String idEQ, String nomSponsor, String pays) {
        Long equipeId = parseId(idEQ, "idEQ");

        Equipe equipe = equipeRepository.findById(equipeId).get();

        Sponsor sponsor = sponsorRepository.findByNomAndPays(nomSponsor, pays)
                .orElseGet(() -> {
                    Sponsor s = new Sponsor();
                    s.setNom(nomSponsor);
                    s.setPays(pays);
                    s.setArchived(false);
                    s.setBloquerContrat(false);
                    s.setDateCreation(LocalDate.now());
                    return sponsorRepository.save(s);
                });

        c.setEquipe(equipe);
        c.setSponsor(sponsor);

        List<Contrat> contratsEquipeMisAJour = new ArrayList<>();
        if (equipe.getContrats() != null) {
            contratsEquipeMisAJour.addAll(equipe.getContrats());
        }
        contratsEquipeMisAJour.add(c);
        equipe.getContrats().clear();
        equipe.getContrats().addAll(contratsEquipeMisAJour);

        List<Contrat> contratsSponsorMisAJour = new ArrayList<>();
        if (sponsor.getContrats() != null) {
            contratsSponsorMisAJour.addAll(sponsor.getContrats());
        }
        contratsSponsorMisAJour.add(c);
        sponsor.getContrats().clear();
        sponsor.getContrats().addAll(contratsSponsorMisAJour);

        return contratRepository.save(c);
    }

    // ============================================================
    // 1.1 — Historique des contrats d'une equipe
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public HashMap<String, Float> historiqueContratsEquipe(String libelleEquipe) {
        Equipe equipe = equipeRepository.findByLibelle(libelleEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe introuvable : " + libelleEquipe));

        HashMap<String, Float> historique = new HashMap<>();
        if (equipe.getContrats() != null) {
            for (Contrat contrat : equipe.getContrats()) {
                String annee = contrat.getAnnee() != null ? contrat.getAnnee() : "N/A";
                float montant = contrat.getMontant() != null ? contrat.getMontant() : 0f;
                historique.merge(annee, montant, Float::sum);
            }
        }
        return historique;
    }

    // ============================================================
    // 1.2 — Nombre de points des pilotes d'une equipe pour un championnat et une annee
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public Integer nbPointsParPilotesUneEquipeChampionnatPourUneAnne(Long idEquipe,
                                                                      Long idChampionnat,
                                                                      String annee) {
        int anneeInt;
        try {
            anneeInt = Integer.parseInt(annee);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Annee invalide : " + annee);
        }

        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe introuvable : " + idEquipe));
        Championnat championnat = championnatRepository.findById(idChampionnat)
                .orElseThrow(() -> new RuntimeException("Championnat introuvable : " + idChampionnat));

        Set<Long> coursesDuChampionnatCetteAnnee = championnat.getCourses().stream()
                .filter(c -> c.getDateCourse() != null && c.getDateCourse().getYear() == anneeInt)
                .map(Course::getIdCourse)
                .collect(Collectors.toSet());

        int totalPoints = 0;
        for (Pilote pilote : equipe.getPilotes()) {
            for (Position position : pilote.getPositions()) {
                if (position.getCourse() != null
                        && coursesDuChampionnatCetteAnnee.contains(position.getCourse().getIdCourse())) {
                    totalPoints += position.getNbPoints() != null ? position.getNbPoints() : 0;
                }
            }
        }
        return totalPoints;
    }

    // ============================================================
    // 1.3 — Moyenne des positions d'un pilote entre deux dates
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public Float moyennePositionsEntreDeuxDate(LocalDate startDate, LocalDate endDate, String libelleP) {
        Pilote pilote = piloteRepository.findByLibelleP(libelleP)
                .orElseThrow(() -> new RuntimeException("Pilote introuvable : " + libelleP));

        List<Integer> classements = new ArrayList<>();
        for (Position position : pilote.getPositions()) {
            if (position.getCourse() == null || position.getCourse().getDateCourse() == null) {
                continue;
            }
            LocalDate dateCourse = position.getCourse().getDateCourse();
            if (!dateCourse.isBefore(startDate) && !dateCourse.isAfter(endDate)) {
                if (position.getClassement() != null) {
                    classements.add(position.getClassement());
                }
            }
        }

        if (classements.isEmpty()) return 0f;
        return (float) classements.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    // ============================================================
    // 2.1 — listeWinners : gagnant (PiloteDto) de chaque championnat
    //        dont l'annee est strictement superieure au parametre
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<PiloteDto> listeWinners(Integer annee) {
        List<Championnat> championnats = championnatRepository.findByAnneeGreaterThan(annee);
        List<PiloteDto> winners = new ArrayList<>();

        for (Championnat championnat : championnats) {

            // Calcul des points par pilote sur toutes les courses du championnat
            Map<Long, Integer> pointsParPilote = new HashMap<>();
            Map<Long, Pilote>  piloteMap       = new HashMap<>();

            for (Course course : championnat.getCourses()) {
                for (Position position : course.getPositions()) {
                    Pilote pilote = position.getPilote();
                    if (pilote == null) continue;
                    int pts = position.getNbPoints() != null ? position.getNbPoints() : 0;
                    pointsParPilote.merge(pilote.getIdPilote(), pts, Integer::sum);
                    piloteMap.put(pilote.getIdPilote(), pilote);
                }
            }

            if (pointsParPilote.isEmpty()) continue;

            // Pilote ayant le plus de points = gagnant
            Long winnerId = pointsParPilote.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey();

            Pilote winner = piloteMap.get(winnerId);

            // Mapping manuel -> PiloteDto
            PiloteDto dto = new PiloteDto();
            dto.setIdPilote(winner.getIdPilote());
            dto.setLibelleP(winner.getLibelleP());
            dto.setCategorie(winner.getCategorie());
            dto.setLibelleEquipe(winner.getEquipe() != null ? winner.getEquipe().getLibelle() : "N/A");
            dto.setLibelleChampionnat(championnat.getLibelleC());
            dto.setAnneeChampionnat(championnat.getAnnee());
            dto.setTotalPoints(pointsParPilote.get(winnerId));

            winners.add(dto);
        }

        return winners;
    }

    // ============================================================
    // 2.2 — ajoutContratEtAffecterASponsorEtEquipe avec ContratDto
    //        (libelle equipe au lieu de l'ID + reponse sans references circulaires)
    // ============================================================

    @Override
    public ContratDto ajoutContratEtAffecterASponsorEtEquipe(Contrat contrat,
                                                              String libelleEquipe,
                                                              String nomSponsor,
                                                              String pays) {
        Equipe equipe = equipeRepository.findByLibelle(libelleEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe introuvable : " + libelleEquipe));

        Sponsor sponsor = sponsorRepository.findByNomAndPays(nomSponsor, pays)
                .orElseGet(() -> {
                    Sponsor s = new Sponsor();
                    s.setNom(nomSponsor);
                    s.setPays(pays);
                    s.setArchived(false);
                    s.setBloquerContrat(false);
                    s.setDateCreation(LocalDate.now());
                    return sponsorRepository.save(s);
                });

        contrat.setEquipe(equipe);
        contrat.setSponsor(sponsor);
        Contrat saved = contratRepository.save(contrat);

        // Mapping manuel Contrat -> ContratDto (sans references circulaires)
        ContratDto dto = new ContratDto();
        dto.setIdContrat(saved.getIdContrat());
        dto.setMontant(saved.getMontant());
        dto.setAnnee(saved.getAnnee());
        dto.setArchived(saved.getArchived());
        dto.setNomSponsor(sponsor.getNom());
        dto.setPaysSponsor(sponsor.getPays());
        dto.setLibelleEquipe(equipe.getLibelle());
        return dto;
    }

    // ============================================================
    // Helpers
    // ============================================================

    private Long parseId(String value, String fieldName) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " doit etre un nombre valide.");
        }
    }
}
