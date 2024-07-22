package tn.stage.Controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import tn.stage.Entity.FormEntity.InterventionForm;

import tn.stage.Service.InterventionFormService;


import java.io.IOException;

import java.util.List;

@Path("form")
public class InterventionFormResource {



    @Inject
    InterventionFormService interventionFormService;



    @GET
    public List<InterventionForm> getForms() {
        return interventionFormService.getAllIntervention();
    }



    @GET
    @Path("{id}")
    public InterventionForm getForm(@PathParam("id") Long id) {
        return interventionFormService.getIntervention(id);
    }


    @GET
    @Path("mine/{mine}")
    public List<InterventionForm> getInterventionsForCurrentUser(@PathParam("mine") String mine) {
        return interventionFormService.getAllInterventionOfCurrentUser(mine);
    }

    @POST
    @Path("add")
    @Transactional
    public Response create( InterventionForm form, @MultipartForm InputPart fileAvant, @MultipartForm InputPart fileApres) throws IOException {
        if (fileAvant != null) {
            String fileAvantPath = interventionFormService.saveFile(fileAvant);
            form.setFilesAvant(fileAvantPath);
        }
        if (fileApres != null) {
            String fileApresPath = interventionFormService.saveFile(fileApres);
            form.setFilesApres(fileApresPath);
        }
        interventionFormService.addIntervention(form);
        return Response.ok(form).build();
    }



    @DELETE
        @Path("delete/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
    interventionFormService.deleteIntervention(id);
    }





}



