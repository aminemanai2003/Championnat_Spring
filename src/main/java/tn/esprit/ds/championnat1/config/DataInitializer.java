package tn.esprit.ds.championnat1.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.ds.championnat1.entities.Championnat;
import tn.esprit.ds.championnat1.entities.Contrat;
import tn.esprit.ds.championnat1.entities.Course;
import tn.esprit.ds.championnat1.entities.DetailChampionnat;
import tn.esprit.ds.championnat1.entities.Equipe;
import tn.esprit.ds.championnat1.entities.Pilote;
import tn.esprit.ds.championnat1.entities.Position;
import tn.esprit.ds.championnat1.entities.Sponsor;
import tn.esprit.ds.championnat1.enums.Categorie;
import tn.esprit.ds.championnat1.repositories.ChampionnatRepository;
import tn.esprit.ds.championnat1.repositories.ContratRepository;
import tn.esprit.ds.championnat1.repositories.CourseRepository;
import tn.esprit.ds.championnat1.repositories.EquipeRepository;
import tn.esprit.ds.championnat1.repositories.PiloteRepository;
import tn.esprit.ds.championnat1.repositories.SponsorRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    private final EquipeRepository equipeRepository;
    private final SponsorRepository sponsorRepository;
    private final ContratRepository contratRepository;
    private final PiloteRepository piloteRepository;
    private final CourseRepository courseRepository;
    private final ChampionnatRepository championnatRepository;

    @Override
    public void run(String... args) {
        if (contratRepository.count() > 0) {
            LOGGER.info("DataInitializer: contrats deja presents, initialisation ignoree.");
            return;
        }

        int anneeCourante = LocalDate.now().getYear();

        Equipe redBull = new Equipe();
        redBull.setLibelle("Red Bull");
        redBull.setNbPointsTotal(0);
        redBull.setClassementGeneral(0);

        Equipe ferrari = new Equipe();
        ferrari.setLibelle("Ferrari");
        ferrari.setNbPointsTotal(0);
        ferrari.setClassementGeneral(0);

        equipeRepository.saveAll(List.of(redBull, ferrari));

        Sponsor oracle = new Sponsor();
        oracle.setNom("Oracle");
        oracle.setPays("USA");
        oracle.setBudgetAnnuel(70_000_000f);
        oracle.setBloquerContrat(false);
        oracle.setArchived(false);
        oracle.setDateCreation(LocalDate.now());

        Sponsor shell = new Sponsor();
        shell.setNom("Shell");
        shell.setPays("UK");
        shell.setBudgetAnnuel(15_000_000f);
        shell.setBloquerContrat(false);
        shell.setArchived(false);
        shell.setDateCreation(LocalDate.now());

        sponsorRepository.saveAll(List.of(oracle, shell));

        Contrat contratActifRB = new Contrat();
        contratActifRB.setMontant(55_000_000f);
        contratActifRB.setAnnee(String.valueOf(anneeCourante));
        contratActifRB.setArchived(false);
        contratActifRB.setEquipe(redBull);
        contratActifRB.setSponsor(oracle);

        Contrat contratActifFerrari = new Contrat();
        contratActifFerrari.setMontant(20_000_000f);
        contratActifFerrari.setAnnee(String.valueOf(anneeCourante));
        contratActifFerrari.setArchived(false);
        contratActifFerrari.setEquipe(ferrari);
        contratActifFerrari.setSponsor(shell);

        Contrat contratExpire = new Contrat();
        contratExpire.setMontant(15_000_000f);
        contratExpire.setAnnee(String.valueOf(anneeCourante - 1));
        contratExpire.setArchived(false);
        contratExpire.setEquipe(redBull);
        contratExpire.setSponsor(oracle);

        contratRepository.saveAll(List.of(contratActifRB, contratActifFerrari, contratExpire));

        redBull.getContrats().add(contratActifRB);
        redBull.getContrats().add(contratExpire);
        ferrari.getContrats().add(contratActifFerrari);
        oracle.getContrats().add(contratActifRB);
        oracle.getContrats().add(contratExpire);
        shell.getContrats().add(contratActifFerrari);

        Equipe motogpTeam = new Equipe();
        motogpTeam.setLibelle("MotoGP Team");
        motogpTeam.setNbPointsTotal(0);
        motogpTeam.setClassementGeneral(0);
        equipeRepository.save(motogpTeam);

        Pilote max = new Pilote();
        max.setLibelleP("Max Verstappen");
        max.setNbPointsTotal(65);
        max.setClassementGeneral(1);
        max.setCategorie(Categorie.FORMULA1);
        max.setEquipe(redBull);

        Pilote lewis = new Pilote();
        lewis.setLibelleP("Lewis Hamilton");
        lewis.setNbPointsTotal(61);
        lewis.setClassementGeneral(2);
        lewis.setCategorie(Categorie.FORMULA1);
        lewis.setEquipe(ferrari);

        Pilote lando = new Pilote();
        lando.setLibelleP("Lando Norris");
        lando.setNbPointsTotal(48);
        lando.setClassementGeneral(3);
        lando.setCategorie(Categorie.FORMULA1);
        lando.setEquipe(redBull);

        Pilote bagnaia = new Pilote();
        bagnaia.setLibelleP("Francesco Bagnaia");
        bagnaia.setNbPointsTotal(0);
        bagnaia.setClassementGeneral(0);
        bagnaia.setCategorie(Categorie.MOTOGP);
        bagnaia.setEquipe(motogpTeam);

        Pilote martin = new Pilote();
        martin.setLibelleP("Jorge Martin");
        martin.setNbPointsTotal(0);
        martin.setClassementGeneral(0);
        martin.setCategorie(Categorie.MOTOGP);
        martin.setEquipe(motogpTeam);

        Pilote marquez = new Pilote();
        marquez.setLibelleP("Marc Marquez");
        marquez.setNbPointsTotal(0);
        marquez.setClassementGeneral(0);
        marquez.setCategorie(Categorie.MOTOGP);
        marquez.setEquipe(motogpTeam);

        piloteRepository.saveAll(List.of(max, lewis, lando, bagnaia, martin, marquez));

        redBull.getPilotes().add(max);
        redBull.getPilotes().add(lando);
        ferrari.getPilotes().add(lewis);
        motogpTeam.getPilotes().add(bagnaia);
        motogpTeam.getPilotes().add(martin);
        motogpTeam.getPilotes().add(marquez);

        Course bahrein = new Course();
        bahrein.setEmplacement("Bahrein");
        bahrein.setDateCourse(LocalDate.of(anneeCourante, 3, 2));

        Course saudi = new Course();
        saudi.setEmplacement("Jeddah");
        saudi.setDateCourse(LocalDate.of(anneeCourante, 3, 16));

        Course melbourne = new Course();
        melbourne.setEmplacement("Melbourne");
        melbourne.setDateCourse(LocalDate.of(anneeCourante, 4, 1));

        courseRepository.saveAll(List.of(bahrein, saudi, melbourne));

        Position pos1 = new Position();
        pos1.setClassement(1);
        pos1.setNbPoints(25);
        pos1.setCourse(bahrein);
        pos1.setPilote(max);

        Position pos2 = new Position();
        pos2.setClassement(2);
        pos2.setNbPoints(18);
        pos2.setCourse(bahrein);
        pos2.setPilote(lewis);

        Position pos3 = new Position();
        pos3.setClassement(3);
        pos3.setNbPoints(15);
        pos3.setCourse(bahrein);
        pos3.setPilote(lando);

        Position pos4 = new Position();
        pos4.setClassement(1);
        pos4.setNbPoints(25);
        pos4.setCourse(saudi);
        pos4.setPilote(lewis);

        Position pos5 = new Position();
        pos5.setClassement(2);
        pos5.setNbPoints(18);
        pos5.setCourse(saudi);
        pos5.setPilote(max);

        Position pos6 = new Position();
        pos6.setClassement(3);
        pos6.setNbPoints(15);
        pos6.setCourse(saudi);
        pos6.setPilote(lando);

        Position pos7 = new Position();
        pos7.setClassement(1);
        pos7.setNbPoints(22);
        pos7.setCourse(melbourne);
        pos7.setPilote(max);

        Position pos8 = new Position();
        pos8.setClassement(2);
        pos8.setNbPoints(18);
        pos8.setCourse(melbourne);
        pos8.setPilote(lewis);

        Position pos9 = new Position();
        pos9.setClassement(3);
        pos9.setNbPoints(18);
        pos9.setCourse(melbourne);
        pos9.setPilote(lando);

        max.getPositions().add(pos1);
        max.getPositions().add(pos5);
        max.getPositions().add(pos7);
        lewis.getPositions().add(pos2);
        lewis.getPositions().add(pos4);
        lewis.getPositions().add(pos8);
        lando.getPositions().add(pos3);
        lando.getPositions().add(pos6);
        lando.getPositions().add(pos9);

        if (bahrein.getPositions() == null) {
            bahrein.setPositions(new HashSet<>());
        }
        if (saudi.getPositions() == null) {
            saudi.setPositions(new HashSet<>());
        }
        if (melbourne.getPositions() == null) {
            melbourne.setPositions(new HashSet<>());
        }
        bahrein.getPositions().add(pos1);
        bahrein.getPositions().add(pos2);
        bahrein.getPositions().add(pos3);
        saudi.getPositions().add(pos4);
        saudi.getPositions().add(pos5);
        saudi.getPositions().add(pos6);
        melbourne.getPositions().add(pos7);
        melbourne.getPositions().add(pos8);
        melbourne.getPositions().add(pos9);

        piloteRepository.saveAll(List.of(max, lewis, lando, bagnaia, martin, marquez));

        DetailChampionnat detail = new DetailChampionnat();
        detail.setIdDetailChamp("DCH-F1-" + anneeCourante);
        detail.setCode("F1-" + anneeCourante);
        detail.setDescription("Championnat F1 de demonstration");

        Championnat championnat = new Championnat();
        championnat.setCategorie(Categorie.FORMULA1);
        championnat.setLibelleC("Championnat F1");
        championnat.setAnnee(anneeCourante);
        championnat.setDetailChampionnat(detail);

        if (championnat.getCourses() == null) {
            championnat.setCourses(new HashSet<>());
        }
        championnat.getCourses().add(bahrein);
        championnat.getCourses().add(saudi);
        championnat.getCourses().add(melbourne);

        championnatRepository.save(championnat);

        LOGGER.info("DataInitializer: jeu de donnees de demonstration insere avec succes.");
    }
}
