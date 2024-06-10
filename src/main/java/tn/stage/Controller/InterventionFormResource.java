package tn.stage.Controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import tn.stage.Entity.InterventionForm;
import tn.stage.Service.InterventionFormService;


import java.util.List;

@Path("form")
@SecurityRequirement(name = "Keycloak")
public class InterventionFormResource {



    @Inject
    InterventionFormService interventionFormService;


    @GET
    public List<InterventionForm> getForms() {
        return interventionFormService.getAllIntervention();
    }

    @GET
    @Path("stats/bestTechnicienOfTheMonth")
    public List getBestTechnicien(){
        return interventionFormService.getTopTechnicien();
    }

    @GET
    @Path("stats/MostUsedChangePiece")
    public List getMostUsedChangePiece(){
        return interventionFormService.getMostUsedPieceRechange();
    }

    @GET
    @Path("stats/MostRequestedTypeOfIntervention")
    public List getMostRequestedTypeOfIntervention(){
        return interventionFormService.getTopInterventionType();
    }

    @GET
    @Path("{id}")
    public InterventionForm getForm(@PathParam("id") Long id) {
        return interventionFormService.getIntervention(id);
    }

    @POST
    @Path("add")
    @Transactional
    public Response create(InterventionForm form){
        interventionFormService.addIntervention(form);
        return Response.ok(form).build();
    }

    @POST
    @Path("update/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, InterventionForm form){
        interventionFormService.updateIntervention(id,form);
        return Response.ok(form).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
    interventionFormService.deleteIntervention(id);
    }




}
