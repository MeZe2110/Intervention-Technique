package tn.stage.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tn.stage.Entity.FormEntity.InterventionForm;
import tn.stage.Entity.FormEntity.TypeBoitier;

import java.util.List;

@ApplicationScoped
public class TypeBoitierRepository implements PanacheRepository<TypeBoitier> {
}
