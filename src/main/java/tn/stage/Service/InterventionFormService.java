package tn.stage.Service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import tn.stage.Entity.FormEntity.*;
import tn.stage.Entity.MultipartBody;
import tn.stage.Repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class InterventionFormService {

    @Inject
    InterventionFormRepository interventionFormRepository;



    // Adds a new intervention form
    public void addIntervention(InterventionForm form) {
        // Check if the addable types exists
        MultipartBody multipartBody = new MultipartBody();
        form.setDate(new Date());
        // Persist the form in the repository
        interventionFormRepository.persist(form);
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
