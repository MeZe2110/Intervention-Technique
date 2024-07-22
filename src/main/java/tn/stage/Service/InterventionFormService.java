package tn.stage.Service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import tn.stage.Entity.FormEntity.*;
import tn.stage.Entity.MultipartBody;
import tn.stage.Repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class InterventionFormService {

    @Inject
    InterventionFormRepository interventionFormRepository;
    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    TypePanneRepository typePanneRepository;
    @Inject
    TypeBoitierRepository typeBoitierRepository;
    @Inject
    TypeActionRepository typeActionRepository;
    @Inject
    PieceRechangeRepository  pieceRechangeRepository;


    // Adds a new intervention form
    public void addIntervention(InterventionForm form) {
        // Check if the addable types exists
        MultipartBody multipartBody = new MultipartBody();
        String nameAction = form.getAction().getName();
        TypeAction typeAction = typeActionRepository.find("name", nameAction).firstResult();
        if (typeAction == null) {
            typeAction = new TypeAction();
            typeAction.setName(nameAction);
            typeActionRepository.persist(typeAction);
        }
        String namePanne = form.getPanne().getName();
        TypePanne typePanne = typePanneRepository.find("name", namePanne).firstResult();
        if (typePanne == null) {
            typePanne = new TypePanne();
            typePanne.setName(namePanne);
            typePanneRepository.persist(typePanne);
        }
        String nameBoitier = form.getBoitier().getName();
        TypeBoitier typeBoitier = typeBoitierRepository.find("name", nameBoitier).firstResult();
        if (typeBoitier == null) {
            typeBoitier = new TypeBoitier();
            typeBoitier.setName(nameBoitier);
            typeBoitierRepository.persist(typeBoitier);
        }
        String namePiece = form.getPiece().getName();
        PieceRechange pieceRechange = pieceRechangeRepository.find("name", namePiece).firstResult();
        if (pieceRechange == null) {
            pieceRechange = new PieceRechange();
            pieceRechange.setName(namePiece);
            pieceRechangeRepository.persist(pieceRechange);
        }
        form.setAction(typeAction);
        form.setBoitier(typeBoitier);
        form.setPanne(typePanne);
        form.setPiece(pieceRechange);
        form.setDate(new Date());

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

    public List<InterventionForm> getAllInterventionOfCurrentUser(String currentUserName) {
        return interventionFormRepository.listByNomTechnicien(currentUserName);
    }

    // Retrieves an intervention form by its ID
    public InterventionForm getIntervention(Long id) {
        // Return the form from the repository by its ID
        return interventionFormRepository.findById(id);
    }


    public static final String UPLOAD_DIR = "uploads/";

    public String saveFile(InputPart inputPart) throws IOException {
        String fileName = getFileName(inputPart.getHeaders().getFirst("Content-Disposition"));
        String filePath = UPLOAD_DIR + fileName;
        byte[] bytes = inputPart.getBody(byte[].class, null);
        Files.write(Paths.get(filePath), bytes);
        return filePath;
    }

    public String getFileName(String contentDisposition) {
        for (String part : contentDisposition.split(";")) {
            if (part.trim().startsWith("filename")) {
                return part.split("=")[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

}
