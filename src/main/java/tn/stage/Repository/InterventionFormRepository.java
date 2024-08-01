package tn.stage.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tn.stage.Entity.FormEntity.InterventionForm;


import java.util.*;

@ApplicationScoped
public class InterventionFormRepository implements PanacheRepository<InterventionForm> {


    public List<InterventionForm> listByNomTechnicien(String nomTechnicien) {
        return list("nomTechnicien", nomTechnicien);
    }




}
