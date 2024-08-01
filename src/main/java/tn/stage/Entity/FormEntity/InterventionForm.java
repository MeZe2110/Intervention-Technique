package tn.stage.Entity.FormEntity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterventionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idForm;

    String nomTechnicien;
    String clientName;
    String lieu;

    //Changed it to entity
    @ManyToOne()
    TypeBoitier boitier ;

    //if gps matricule else spacename
    Long matricule;
    String spaceName;

    Long idBoitier;

    @Enumerated(EnumType.STRING)
    State status;

    @ManyToOne()
    TypePanne panne;

    @ManyToOne()
    TypeAction action;


    String filesAvant;

    //if intervention is changer boitier
    Long idNouveauBoitier;
    //if intervention is changer boitier

    //if intervention is deplacer
    String newSpaceName;

    @ManyToOne()
    PieceRechange piece; // change to entity

   String filesApres;

    String commentary;

    Date date;
}
