package tn.stage.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import tn.stage.Entity.FormEntity.PieceRechange;
import tn.stage.Entity.FormEntity.TypeAction;
import tn.stage.Entity.FormEntity.TypeBoitier;
import tn.stage.Entity.FormEntity.TypePanne;
import tn.stage.Repository.PieceRechangeRepository;
import tn.stage.Repository.TypeActionRepository;
import tn.stage.Repository.TypeBoitierRepository;
import tn.stage.Repository.TypePanneRepository;

import java.util.List;

@ApplicationScoped
public class TypeAddableService {

    /*
    this service is for all addable types such as TypeBoitier or TypeAction we will also get
    the list of all addable types individually
    */


    @Inject
    TypePanneRepository typePanneRepository;
    @Inject
    TypeActionRepository typeActionRepository;
    @Inject
    TypeBoitierRepository typeBoitierRepository;
    @Inject
    PieceRechangeRepository pieceRechangeRepository;

    public void addPanne(TypePanne panne){
        typePanneRepository.persist(panne);
    }

    public List<TypePanne> getAllPanne(){
        return typePanneRepository.listAll();
    }

    public void addAction(TypeAction action){
        typeActionRepository.persist(action);
    }

    public List<TypeAction> getAllAction(){
        return typeActionRepository.listAll();
    }
    public void addBoitier(TypeBoitier boitier){
        typeBoitierRepository.persist(boitier);
    }

    public List<TypeBoitier> getAllBoitier(){
        return typeBoitierRepository.listAll();
    }
    public void addPiece(PieceRechange piece){
        pieceRechangeRepository.persist(piece);
    }

    public List<PieceRechange> getAllPiece(){
        return pieceRechangeRepository.listAll();
    }


}


