package tn.stage.Controller;



import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import tn.stage.Entity.UserEntity.User;
import tn.stage.Service.InterventionFormService;
import tn.stage.Service.KeycloakService;

import java.util.List;


@Path("Admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {



    @Inject
    KeycloakService keycloakService;
    @Inject
    InterventionFormService interventionFormService;


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
    @Path("allUsers")
    public List<User> getAllUsers() {
        return keycloakService.getAllUsers();
    }

    @GET
    @Path("user/{id}")
    public User getUser(@PathParam("id") String id) {
        return keycloakService.getUser(id);
    }


    @POST
    @Path("/addUser")
    public Response addUser(User user) {
        return Response.ok(keycloakService.createUser(user)).build();
    }

    @POST
    @Path("/users/{id}/roles/{roleName}")
    public Response addRole(@PathParam("id") String id, @PathParam("roleName") String roleName) {
        keycloakService.assignRoleToUser(id,roleName);
        return Response.ok().build();
    }

    @PUT
    @Path("/updateUser")
    public Response updateUser(User user) {
        return Response.ok(keycloakService.updateUser(user)).build();
    }

    @DELETE
    @Path("/deleteUser/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        keycloakService.deleteUser(id);
        return Response.ok().build();
    }

}
