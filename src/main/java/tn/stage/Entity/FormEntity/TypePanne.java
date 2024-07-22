package tn.stage.Entity.FormEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypePanne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPanne;
    @Column(unique = true)
    String name;


}
