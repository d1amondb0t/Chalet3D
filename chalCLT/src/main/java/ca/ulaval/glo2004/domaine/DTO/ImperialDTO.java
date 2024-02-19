package ca.ulaval.glo2004.domaine.DTO;

import java.util.Arrays;
import java.util.UUID;

import ca.ulaval.glo2004.domaine.utils.Imperial;

public class ImperialDTO {

    private double pieds;
    private double pouces;
    private double numerateur;
    private double denominateur;

    public UUID uuid;


    public ImperialDTO(Imperial imperial) {

        pieds = imperial.obtenirPieds();
        pouces = imperial.obtenirPouces();
        numerateur = imperial.obtenirNumerateur();
        denominateur = imperial.obtenirDenominateur();
    }

    public double obtenirPieds() {
        return pieds;
    }

    public double obtenirPouces() {
        return pouces;
    }

    public double obtenirNumerateur() {
        return numerateur;
    }

    public double obtenirDenominateur() {
        return denominateur;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double convertirEnDoubleDTO(){
        if (this.obtenirNumerateur() > 0) {
            return (this.obtenirPieds() * 12 +  this.obtenirPouces() + this.obtenirNumerateur() / this.obtenirDenominateur()) * 4;
        }
        else {  return (this.obtenirPieds() * 12 + this.obtenirPouces()) * 4;
        }
    }

}