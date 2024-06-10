package tn.stage.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tn.stage.Entity.InterventionForm;

import java.util.List;

@ApplicationScoped
public class InterventionFormRepository implements PanacheRepository<InterventionForm> {

    public List findTopTechnician() {
        return getEntityManager().createQuery(
                        "SELECT monthname(f.date),f.nomTechnicien, COUNT(f.nomTechnicien) as interventionCount " +
                                "FROM InterventionForm f " +
                                "GROUP BY f.nomTechnicien ,month (f.date)" +
                                "ORDER BY interventionCount DESC")
                .setMaxResults(5)
                .getResultList();
    }

    public List findTopInterventionType() {
        return getEntityManager().createQuery(
                        "SELECT monthname(f.date),f.interventionType, COUNT(f.interventionType) as typeCount " +
                                "FROM InterventionForm f " +
                                "GROUP BY f.interventionType,month(f.date) " +
                                "ORDER BY typeCount DESC")
                .setMaxResults(2)
                .getResultList();
    }

    public List findTopPieceRechange() {
        return getEntityManager().createQuery(
                        "SELECT monthname(f.date),f.pieceRechange, COUNT(f.pieceRechange) as pieceCount " +
                                "FROM InterventionForm f " +
                                "GROUP BY f.pieceRechange,month(f.date) " +
                                "ORDER BY pieceCount DESC")
                .setMaxResults(5)
                .getResultList();
    }

}
