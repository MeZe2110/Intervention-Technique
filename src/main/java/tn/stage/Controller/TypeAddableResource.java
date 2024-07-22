package tn.stage.Controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import tn.stage.Entity.FormEntity.*;
import tn.stage.Service.InterventionFormService;
import tn.stage.Service.TypeAddableService;

import java.util.List;

@Path("type")
public class TypeAddableResource {



    @Inject
    TypeAddableService typeAddableService;


    @GET
    @Path("TypeAction")
    public List<TypeAction> getActions() {
        return typeAddableService.getAllAction();
    }
    @GET
    @Path("TypePanne")
    public List<TypePanne> getPannes() {
        return typeAddableService.getAllPanne();
    }
    @GET
    @Path("TypeBoitier")
    public List<TypeBoitier> getBoitiers() {
        return typeAddableService.getAllBoitier();
    }
    @GET
    @Path("PieceRechange")
    public List<PieceRechange> getPieces() {
        return typeAddableService.getAllPiece();
    }

    @POST
    @Path("addAction")
    @Transactional
    public Response createAction(TypeAction action){
        typeAddableService.addAction(action);
        return Response.ok(action).build();
    }

    @POST
    @Path("addBoitier")
    @Transactional
    public Response createBoitier(TypeBoitier boitier){
        typeAddableService.addBoitier(boitier);
        return Response.ok(boitier).build();
    }

    @POST
    @Path("addPanne")
    @Transactional
    public Response createPanne(TypePanne panne){
        typeAddableService.addPanne(panne);
        return Response.ok(panne).build();
    }

    @POST
    @Path("addPiece")
    @Transactional
    public Response createPiece(PieceRechange piece){
        typeAddableService.addPiece(piece);
        return Response.ok(piece).build();
    }


}
