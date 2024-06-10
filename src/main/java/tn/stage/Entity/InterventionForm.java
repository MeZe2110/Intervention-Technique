package tn.stage.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="intervention")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterventionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idForm;

    String nomTechnicien;
    String lieu;
    String type;
    String state;
    String garantie;
    String idAncien;
    String pieceRechange;
    String idNouveau;
    String clientFirstName;
    String clientLastName;
    String description;

    Long matricule;

    Date date;

    @Enumerated(EnumType.STRING)
    TypeIntervention interventionType;


}
