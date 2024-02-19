package ca.ulaval.glo2004.domaine.DTO;


import ca.ulaval.glo2004.domaine.Accessoire;
import ca.ulaval.glo2004.domaine.utils.Imperial;


import java.util.UUID;

public class AccessoireDTO {
    private String type;
    private int idAcessoire;

    ImperialDTO hauteur;
    ImperialDTO longueur;
    //Imperial epaisseur;

    private ImperialDTO positionX;
    private ImperialDTO positionY;
    private ImperialDTO positionZ;
    private boolean emplacementCorrectDTO;
    public UUID uuid;

    public AccessoireDTO(Accessoire accessoire){
        type = accessoire.getType();
        idAcessoire = accessoire.getIdAccessoire();
        hauteur = new ImperialDTO(accessoire.getHauteur());
        longueur= new ImperialDTO(accessoire.getLongueur());
        positionX = new ImperialDTO(accessoire.getPositionX());
        positionY = new ImperialDTO(accessoire.getPositionY());
        positionZ = new ImperialDTO(accessoire.getPositionZ());
        emplacementCorrectDTO = accessoire.getEmplacementCorrect();
        uuid = accessoire.getUUID();
    }

    public String getType() {
        return type;
    }

    public int getIdAccessoire() {
        return idAcessoire;
    }
    public boolean getEmplacementCorrect() {return emplacementCorrectDTO;}

    public ImperialDTO getHauteur() {
        return hauteur;
    }

    public ImperialDTO getLongueur() {
        return longueur;
    }



    public ImperialDTO getPositionX() {
        return positionX;
    }

    public ImperialDTO getPositionY() {
        return positionY;
    }


    public ImperialDTO getPositionZ() {
        return positionZ;
    }

    public UUID getUuid() {
        return uuid;
    }

    public float getPositionXFloat() {
        return (float)convertirImperialEnPixelAccessoireDTO(this.positionX);
    }

    public float getPositionYFloat() {
        return (float)convertirImperialEnPixelAccessoireDTO(this.positionY);
    }
    public void setEmplacementCorrect(boolean valeur) {this.emplacementCorrectDTO = valeur;}

    public double convertirImperialEnPixelAccessoireDTO(ImperialDTO dimension){
        if (dimension.obtenirNumerateur() > 0) {
            return (dimension.obtenirPieds() * 12 +  dimension.obtenirPouces() +
                    dimension.obtenirNumerateur() / dimension.obtenirDenominateur()) * 4;
        }
        else {  return (dimension.obtenirPieds() * 12 + dimension.obtenirPouces()) * 4;
        }
    }
}



