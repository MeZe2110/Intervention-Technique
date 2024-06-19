package tn.stage.Service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import tn.stage.Entity.InterventionForm;
import tn.stage.Entity.State;
import tn.stage.Repository.InterventionFormRepository;

import java.util.List;

@ApplicationScoped
public class InterventionFormService {

    @Inject
    InterventionFormRepository interventionFormRepository;
    @Inject
    SecurityIdentity securityIdentity;


    // Adds a new intervention form
    public void addIntervention(InterventionForm form) {
        // Check if the current user has the role "TECHNICIEN"
        if (securityIdentity.hasRole("TECHNICIEN")) {
            // Set the technician's name in the form to the current user's name
            form.setNomTechnicien(securityIdentity.getPrincipal().getName());
        }
        // Check if the current user has the role "CLIENT"
        if (securityIdentity.hasRole("CLIENT")) {
            // Set the status of the form to "WAITING_FOR_VALIDATION"
            form.setStatus(State.WAITING_FOR_VALIDATION);
        }
        // Set the status of the form to "IN_PROGRESS"
        form.setStatus(State.IN_PROGRESS);
        // Persist the form in the repository
        interventionFormRepository.persist(form);
    }

    // Deletes an intervention form by its ID
    public void deleteIntervention(Long id) {
        // Delete the form from the repository by its ID
        interventionFormRepository.deleteById(id);
    }

    // Retrieves the top technicians
    public List getTopTechnicien() {
        // Return the list of top technicians from the repository
        return interventionFormRepository.findTopTechnician();
    }

    // Retrieves the top intervention types
    public List getTopInterventionType() {
        // Return the list of top intervention types from the repository
        return interventionFormRepository.findTopInterventionType();
    }

    // Retrieves the most used pieces of rechange
    public List getMostUsedPieceRechange() {
        // Return the list of most used pieces of rechange from the repository
        return interventionFormRepository.findTopPieceRechange();
    }

    // Retrieves all intervention forms
    public List<InterventionForm> getAllIntervention() {
        // Return the list of all intervention forms from the repository
        return interventionFormRepository.listAll();
    }

    // Retrieves all intervention forms of the current user
    public List<InterventionForm> getAllInterventionOfCurrentUser() {
        // Return the list of intervention forms assigned to the current user
        return interventionFormRepository.list("nomTechnicien", securityIdentity.getPrincipal().getName());
    }

    // Retrieves an intervention form by its ID
    public InterventionForm getIntervention(Long id) {
        // Return the form from the repository by its ID
        return interventionFormRepository.findById(id);
    }

    // Updates an existing intervention form by its ID
    public void updateIntervention(Long id, @NotNull InterventionForm form) {
        // Find the existing form in the repository by its ID
        InterventionForm iform = interventionFormRepository.findById(id);

        // Update the form fields with the new values
        iform.setClientFirstName(form.getClientFirstName());
        iform.setClientLastName(form.getClientLastName());
        iform.setDescription(form.getDescription());
        iform.setInterventionType(form.getInterventionType());
        iform.setMatricule(form.getMatricule());

        // Persist the updated form in the repository
        interventionFormRepository.persist(iform);
    }

}
