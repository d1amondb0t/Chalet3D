package ca.ulaval.glo2004.domaine.DTO;

import ca.ulaval.glo2004.domaine.Chalet;
import ca.ulaval.glo2004.domaine.utils.Imperial;
import ca.ulaval.glo2004.domaine.Accessoire;
import ca.ulaval.glo2004.domaine.Mur;

import java.awt.*;
import java.util.*;
import java.util.List;


public class MurDTO {
    private String nom;
    private ImperialDTO hauteur;
    private ImperialDTO longueur;
    private ImperialDTO epaisseurMur;
    private ImperialDTO minDistAccessoire;

    private ImperialDTO distanceDeRetrait;

    private ImperialDTO positionX;
    private ImperialDTO positionY;
    private ImperialDTO positionZ;
    private List<AccessoireDTO> accessoiresDTO;
    public UUID uuid;

    private List<Vector<Integer>> rainures;



    public MurDTO(Mur mur) {
        this.nom = mur.getNom();
        this.hauteur = new ImperialDTO(mur.getHauteur());
        this.longueur = new ImperialDTO(mur.getLongueur());
        this.epaisseurMur = new ImperialDTO(mur.getEpaisseur());
        this.minDistAccessoire = new ImperialDTO(mur.getMinDistEntreAccessoire());
        this.positionX = new ImperialDTO(mur.getPositionX());
        this.positionY = new ImperialDTO(mur.getPositionY());
        this.positionZ = new ImperialDTO(mur.getPositionZ());
        this.uuid = mur.getUUID();
        this.accessoiresDTO = new ArrayList<AccessoireDTO>();
        for (Accessoire accessoire : mur.getAccessoires()) {
            AccessoireDTO accessoireDTO = new AccessoireDTO(accessoire);
            this.accessoiresDTO.add(accessoireDTO);
        }
        this.rainures = new ArrayList<>(mur.getRainures());
        this.distanceDeRetrait = new ImperialDTO(mur.getDistanceRetrait());

    }

    public String getNom() {
        return nom;
    }

    public ImperialDTO getHauteur() {
        return hauteur;
    }

  public ImperialDTO getLongueur() {
        return this.longueur;
    }

    public ImperialDTO getEpaisseurMur() {
        return this.epaisseurMur;
    }
    public ImperialDTO getMinDistAccessoire() {return this.minDistAccessoire;}


    public ImperialDTO getPositionX() {
        return positionX;
    }

    public ImperialDTO getPositionY() {
        return positionY;
    }

    public ImperialDTO getPositionZ() {
        return positionZ;
    }
    public List<Vector<Integer>> getRainures() {
        return rainures;
    }

    public List<AccessoireDTO> getAccessoiresDTO() {
        return accessoiresDTO;
    }

    public UUID getUuid() {
        return uuid;
    }

    /*
    public void addAccesoireDTO(AccessoireDTO accessoire) {
        this.accessoires.add(accessoire);
    } */

//    public List<Accessoire> getAccessoires(String nomMur) {
//        if (this.getNom().equals(nomMur)) {
//            return this.getAccessoiresDTO();
//        }
//        return null;
//    }
    public void ajouterRainure(Vector<Integer> rainure){
        this.rainures.add(rainure);
    }

    public boolean getEmplacementCorrectDTO(AccessoireDTO accessoireDeplacer) {
        return accessoireDeplacer.getEmplacementCorrect();
        }

        public ImperialDTO getDistanceRetrait() {
        return distanceDeRetrait;
        }

    //************************************Hover**************************************************
    public Float getPositionXFloat() {
        return (float)this.positionX.convertirEnDoubleDTO();
    }
    public Float getPositionYFloat() {
        return (float)convertirImperialEnPixelAccessoireDTO(this.positionY);
    }

    public double convertirImperialEnPixelAccessoireDTO(ImperialDTO dimension){
        if (dimension.obtenirNumerateur() > 0) {
            return (dimension.obtenirPieds() * 12 +  dimension.obtenirPouces() +
                    dimension.obtenirNumerateur() / dimension.obtenirDenominateur()) * 4;
        }
        else {  return (dimension.obtenirPieds() * 12 + dimension.obtenirPouces()) * 4;
        }
    }

    public String getProprietesDTO() {
        String proprietes = "<html>Dimensions du mur : " + nom + "<br> Hauteur: " + hauteur.obtenirPieds() + " pieds et " + hauteur.obtenirPouces() + " pouces "+ "<br>Longueur: " + longueur.obtenirPieds() + " pieds et " + longueur.obtenirPouces() + " pouces "+  "<br>Épaisseur: " + epaisseurMur.obtenirPouces() + " pouces.";
        return proprietes;

    }
//**************************************************************************************

}



