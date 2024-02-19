package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.utils.*;
import ca.ulaval.glo2004.domaine.utils.Dimension;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public class Accessoire implements Serializable {
    private String type;
    private int idAccessoire;
    Dimension dimension;
    private Imperial positionX;
    private Imperial positionY;
    private Imperial positionZ;
    private UUID uuid;
    private static int nextIdAccessoire = 0;
    private boolean emplacementCorrect;

    public Accessoire(String type, Imperial hauteur, Imperial longueur,  Imperial positionX, Imperial positionY, Imperial positionZ) {
        this.type = type;
        this.idAccessoire = nextIdAccessoire; // à changer
        nextIdAccessoire++;
        //this.dimension = new Dimension(hauteur, longueur, epaisseur);
        this.dimension = new Dimension(hauteur, longueur);
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.uuid = UUID.randomUUID();
        this.emplacementCorrect = true;
    }

    public String getType() {
        return type;
    }
    public void setEmplacementCorrect(boolean valeur) {this.emplacementCorrect = valeur;}
    public boolean getEmplacementCorrect() {return this.emplacementCorrect;}

    public int getIdAccessoire() {
        return idAccessoire;
    }

    public static int getNextIdAccessoire() {
        return nextIdAccessoire;
    }

    public Imperial getHauteur() {
        return this.dimension.getHauteur();
    }

    public Imperial getLongueur() {
        return this.dimension.getLongueur();
    }

    public Imperial getEpaisseur() {
        return this.dimension.getEpaisseur();
    }

    public Imperial getPositionX() {
        return positionX;
    }

    public Imperial getPositionY() {
        return positionY;
    }

    public Imperial getPositionZ() {
        return positionZ;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdAcessoire(int idAcessoire) {
        this.idAccessoire = idAcessoire;
    }

    public void setHauteur(Imperial hauteur) {
        this.dimension.setHauteur(hauteur);
    }

    public void setLongueur(Imperial longueur) {
        this.dimension.setLongueur(longueur);
    }

    public void setEpaisseur(Imperial epaisseur) {
        this.dimension.setEpaisseur(epaisseur);
    }

    public void setPositionX(Imperial positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(Imperial positionY) {
        this.positionY = positionY;
    }

    public void setPositionZ(Imperial positionZ) {
        this.positionZ = positionZ;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

}

