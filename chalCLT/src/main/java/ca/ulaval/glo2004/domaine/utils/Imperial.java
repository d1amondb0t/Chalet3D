package ca.ulaval.glo2004.domaine.utils;

import java.io.Serializable;
import java.util.Arrays;


public class Imperial implements Serializable {

    private double pieds;
    private double pouces;
    private double numerateur;
    private double denominateur;




    public Imperial(double pieds, double pouces, double numerateur, double denominateur){
        this.pieds = pieds;
        this.pouces = pouces;
        this.numerateur = numerateur;
        this.denominateur = denominateur;
    }
    public Imperial(double pieds, double pouces){
        this.pieds = pieds;
        this.pouces = pouces;

    }

    public Imperial(double pieds){
        this.pieds = pieds;
    }

    public Imperial(String chaineMesure){
        String[] chaines = chaineMesure.split(" ");
        for (String chaine : chaines) {
            if (chaine.contains("'")){
                String sousChaine = chaine.substring(0, chaine.indexOf("'"));
                this.pieds = Double.parseDouble(sousChaine);
            }
            if(chaine.contains("\"")){
                String poucesStr = chaine.substring(0, chaine.indexOf("\""));
                if (!poucesStr.isEmpty()){
                    this.pouces = Double.parseDouble(poucesStr);
                if (this.pouces >= 12){
                    this.pieds = this.pieds + Math.floor(this.pouces / 12);
                    this.pouces = this.pouces % 12;
                }
            }}
            if(chaine.contains("/")){
                String numerateur = chaine.substring(0,chaine.indexOf("/"));
                String denominateur = chaine.substring(chaine.indexOf("/")+1);
                this.numerateur = Double.parseDouble(numerateur);
                this.denominateur = Double.parseDouble(denominateur);
                double variable = this.numerateur / this.denominateur;
                this.pouces = this.pouces + variable;
                if (this.pouces >= 12){
                    this.pieds = this.pieds + Math.floor(this.pouces / 12);
                    this.pouces = this.pouces % 12;
                }
            }
        }
    }

    public double obtenirPieds(){
        return this.pieds;
    }

    public void modifierPieds(int pieds){
        this.pieds = pieds;
    }

    public double obtenirPouces(){
        return this.pouces;
    }

    public void modifierPouces(int pouces){
        this.pouces = pouces;
        if (this.pouces >= 12.0){
            this.pieds =  this.pouces / 12.0;
            this.pouces = this.pouces % 12.0;
        }

    }

    public double obtenirNumerateur(){
        return this.numerateur;
    }

    public void modifierNumerateur(int numerateur){
        this.numerateur = numerateur;
        if (this.numerateur >= this.denominateur){
            this.pouces = this.pouces + (this.numerateur / this.denominateur);
            if (this.pouces >= 12.0){
                this.pieds =  this.pieds + this.pouces / 12.0;
                this.pouces = this.pouces % 12.0;
            }
            this.numerateur = this.numerateur % this.denominateur;
        }

    }
    public double obtenirDenominateur(){
        return this.denominateur;
    }

    public void modifierDenominateur(int Denominateur){
        this.denominateur = Denominateur;
    }

    public Imperial multiplication(double i){
        // Code à bonifier pour lorsque chiffre plus complexe
        pieds = this.obtenirPieds() * i;
        pouces = this.obtenirPouces() * i;
        return new Imperial(pieds,pouces);
    }

    public Imperial division(Imperial i){
        // Code à bonifier pour lorsque chiffre plus complexe et est-ce nécessaire comme fonction?
        pieds = this.obtenirPieds() / i.obtenirPieds();
        pouces = this.obtenirPouces() / i.obtenirPouces();
        return new Imperial(pieds,pouces);
    }

    public double convertirEnDouble(){
        if (this.obtenirNumerateur() > 0) {
            return (this.obtenirPieds() * 12 +  this.obtenirPouces() + this.obtenirNumerateur() / this.obtenirDenominateur()) * 4;
        }
        else {  return (this.obtenirPieds() * 12 + this.obtenirPouces()) * 4;
        }
    }
}

