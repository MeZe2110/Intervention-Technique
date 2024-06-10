package tn.stage.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import tn.stage.Entity.InterventionForm;
import tn.stage.Repository.InterventionFormRepository;

import java.util.List;

@ApplicationScoped
public class InterventionFormService {

    @Inject
    InterventionFormRepository interventionFormRepository;
    @Inject
    JsonWebToken jwt;

    public void addIntervention(InterventionForm form) {
        form.setNomTechnicien(jwt.getName());
        interventionFormRepository.persist(form);
    }
    public void deleteIntervention(Long id) {
        interventionFormRepository.deleteById(id);
    }

    public List getTopTechnicien() {
        return interventionFormRepository.findTopTechnician();
    }

    public List getTopInterventionType() {
        return interventionFormRepository.findTopInterventionType();
    }

    public  List getMostUsedPieceRechange(){
        return interventionFormRepository.findTopPieceRechange();
    }

    public List<InterventionForm> getAllIntervention() {
        return interventionFormRepository.listAll();
    }

    public InterventionForm getIntervention(Long id) {
        return interventionFormRepository.findById(id);
    }

    public void updateIntervention(Long id,InterventionForm form) {
        InterventionForm iform = interventionFormRepository.findById(id);

        iform.setClientFirstName(form.getClientFirstName());
        iform.setClientLastName(form.getClientLastName());
        iform.setDescription(form.getDescription());
        iform.setInterventionType(form.getInterventionType());
        iform.setMatricule(form.getMatricule());

        interventionFormRepository.persist(iform);

    }



}
