package ca.ulaval.glo2004.domaine.DTO;


import ca.ulaval.glo2004.domaine.SensToit;
import ca.ulaval.glo2004.domaine.Toit;
import ca.ulaval.glo2004.domaine.utils.Dimension;
import ca.ulaval.glo2004.domaine.utils.Imperial;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class ToitDTO {

    private String nom;
    private Dimension dimension;
    private ImperialDTO hauteur;

    private ImperialDTO hauteurRallonge;
    private ImperialDTO longueur;
    private ImperialDTO epaisseurMur;
    private ImperialDTO positionX;
    private ImperialDTO positionY;
    private ImperialDTO positionZ;
    private ImperialDTO distanceDeRetrait;

    private List<Vector<Integer>> rainures;
    private UUID uuid;
    private float angle;
    private SensToit sens;

    public ToitDTO(Toit toit) {
        this.angle = toit.getAngle();

        this.uuid = UUID.randomUUID();

        this.nom = toit.getNom();

        this.hauteur = new ImperialDTO(toit.getHauteur());
        this.hauteurRallonge = new ImperialDTO(toit.getHauteurRallonge());
        this.longueur = new ImperialDTO(toit.getLongueur());
        this.epaisseurMur = new ImperialDTO(toit.getEpaisseur());
        this.positionX = new ImperialDTO(toit.getPositionX());
        this.positionY = new ImperialDTO(toit.getPositionY());
        this.positionZ = new ImperialDTO(toit.getPositionZ());

        this.uuid = UUID.randomUUID();

        this.rainures = new ArrayList<Vector<Integer>>();

    }

    public float getAngle() {
        return this.angle;
    }


    public UUID getUUID() {
        return uuid;
    }
    public List<Vector<Integer>> getRainures() {
        return rainures;
    }
    public String getNom() {
        return nom;
    }

    public ImperialDTO getHauteur() {
        return hauteur;
    }

    public ImperialDTO getHauteurRallonge() {
        return this.hauteurRallonge;
    }

    public ImperialDTO getLongueur() {
        return this.longueur;
    }

    public ImperialDTO getEpaisseurMur() {
        return this.epaisseurMur;
    }

    //************************************Hover**************************************************
    public Float getPositionXFloat() {
        return (float)convertirImperialEnPixelAccessoireDTO(this.positionX);
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
        String proprietes = "<html>Panneau : " + nom + "<br> Hauteur: " + hauteur.obtenirPieds() + " pieds et " + hauteur.obtenirPouces() + " pouces "+ "<br>Longueur: " + longueur.obtenirPieds() + " pieds et " + longueur.obtenirPouces() + " pouces "+  "<br>Épaisseur: " + epaisseurMur.obtenirPouces() + " pouces.";
        return proprietes;
    }
//**************************************************************************************



}