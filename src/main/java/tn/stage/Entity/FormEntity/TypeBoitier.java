package tn.stage.Entity.FormEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

//entité type boitier
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeBoitier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idBoitier;
    @Column(unique = true)
    String name;



}
