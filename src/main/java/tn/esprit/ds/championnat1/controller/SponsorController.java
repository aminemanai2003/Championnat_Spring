package tn.esprit.ds.championnat1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ds.championnat1.entities.Sponsor;
import tn.esprit.ds.championnat1.service.ISponsorService;

import java.util.List;

@Tag(name = "Sponsors", description = "Gestion des sponsors du championnat")
@RestController
@AllArgsConstructor
@RequestMapping("/sponsor")
public class SponsorController {

    private final ISponsorService sponsorService;

    // ------------------------------------------------------------------ CREATE

    @Operation(
            summary = "Ajouter un sponsor",
            description = "Crée un nouveau sponsor et le persiste en base de données."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor créé avec succès",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Sponsor.class))),
            @ApiResponse(responseCode = "400", description = "Corps de la requête invalide",
                    content = @Content)
    })
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Sponsor ajouterSponsor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données du sponsor à créer", required = true,
                    content = @Content(schema = @Schema(implementation = Sponsor.class)))
            @RequestBody Sponsor sponsor) {
        return sponsorService.ajouterSponsor(sponsor);
    }

    @Operation(
            summary = "Ajouter plusieurs sponsors",
            description = "Insère une liste de sponsors en une seule requête."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsors créés avec succès",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Sponsor.class)))),
            @ApiResponse(responseCode = "400", description = "Liste invalide",
                    content = @Content)
    })
    @PostMapping(value = "/addAll", consumes = "application/json", produces = "application/json")
    public List<Sponsor> ajouterSponsors(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Liste de sponsors à créer", required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sponsor.class))))
            @RequestBody List<Sponsor> sponsors) {
        return sponsorService.ajouterSponsors(sponsors);
    }

    // ------------------------------------------------------------------ READ

    @Operation(
            summary = "Lister tous les sponsors",
            description = "Retourne la liste complète des sponsors enregistrés en base de données."
    )
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Sponsor.class))))
    @GetMapping("/all")
    public List<Sponsor> listSponsors() {
        return sponsorService.listSponsors();
    }

    @Operation(
            summary = "Récupérer un sponsor par identifiant",
            description = "Retourne le sponsor correspondant à l'identifiant fourni."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor trouvé",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Sponsor.class))),
            @ApiResponse(responseCode = "404", description = "Sponsor introuvable",
                    content = @Content)
    })
    @GetMapping("/get/{id}")
    public Sponsor recupererSponsor(
            @Parameter(description = "Identifiant unique du sponsor", required = true, example = "1")
            @PathVariable("id") Long idSponsor) {
        return sponsorService.recupererSponsor(idSponsor);
    }

    // ------------------------------------------------------------------ UPDATE

    @Operation(
            summary = "Modifier un sponsor",
            description = "Met à jour les informations d'un sponsor existant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor mis à jour",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Sponsor.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Sponsor introuvable",
                    content = @Content)
    })
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public Sponsor modifierSponsor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouvelles données du sponsor (l'ID doit être renseigné)", required = true,
                    content = @Content(schema = @Schema(implementation = Sponsor.class)))
            @RequestBody Sponsor sponsor) {
        return sponsorService.modifierSponsor(sponsor);
    }

    @Operation(
            summary = "Archiver un sponsor",
            description = "Positionne le flag 'archived' du sponsor à true. Retourne true si l'opération a réussi."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Résultat de l'archivage",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Sponsor introuvable",
                    content = @Content)
    })
    @PutMapping("/archive/{id}")
    public Boolean archiverSponsor(
            @Parameter(description = "Identifiant unique du sponsor à archiver", required = true, example = "1")
            @PathVariable("id") Long idSponsor) {
        return sponsorService.archiverSponsor(idSponsor);
    }

    // ------------------------------------------------------------------ DELETE

    @Operation(
            summary = "Supprimer un sponsor",
            description = "Supprime définitivement le sponsor identifié par l'ID fourni."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Sponsor introuvable",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public void supprimerSponsor(
            @Parameter(description = "Identifiant unique du sponsor à supprimer", required = true, example = "1")
            @PathVariable("id") Long idSponsor) {
        sponsorService.supprimerSponsor(idSponsor);
    }
}