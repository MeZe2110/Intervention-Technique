package tn.stage.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tn.stage.Entity.FormEntity.InterventionForm;

import java.util.List;

@ApplicationScoped
public class InterventionFormRepository implements PanacheRepository<InterventionForm> {

    // Finds the top 5 technicians based on the number of interventions per month
    public List findTopTechnician() {
        // Create and execute a query to find the top technicians
        return getEntityManager().createQuery(
                        "SELECT monthname(f.date), f.nomTechnicien, COUNT(f.nomTechnicien) as interventionCount " +
                                "FROM InterventionForm f " +
                                "GROUP BY f.nomTechnicien, month(f.date) " +
                                "ORDER BY interventionCount DESC")
                .setMaxResults(5) // Limit the results to the top 5
                .getResultList(); // Get the result list
    }

    // Finds the top 2 intervention types based on the number of interventions per month
    public List findTopInterventionType() {
        // Create and execute a query to find the top intervention types
        return getEntityManager().createQuery(
                        "SELECT monthname(f.date), f.interventionType, COUNT(f.interventionType) as typeCount " +
                                "FROM InterventionForm f " +
                                "GROUP BY f.interventionType, month(f.date) " +
                                "ORDER BY typeCount DESC")
                .setMaxResults(2) // Limit the results to the top 2
                .getResultList(); // Get the result list
    }

    // Finds the top 5 most used pieces of rechange based on the number of interventions per month
    public List findTopPieceRechange() {
        // Create and execute a query to find the top pieces of rechange
        return getEntityManager().createQuery(
                        "SELECT monthname(f.date), f.pieceRechange, COUNT(f.pieceRechange) as pieceCount " +
                                "FROM InterventionForm f " +
                                "GROUP BY f.pieceRechange, month(f.date) " +
                                "ORDER BY pieceCount DESC")
                .setMaxResults(5) // Limit the results to the top 5
                .getResultList(); // Get the result list
    }

    public List<InterventionForm> listByNomTechnicien(String nomTechnicien) {
        return list("nomTechnicien", nomTechnicien);
    }


}
