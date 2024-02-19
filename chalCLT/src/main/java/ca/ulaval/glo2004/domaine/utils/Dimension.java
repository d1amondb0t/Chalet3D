package ca.ulaval.glo2004.domaine.utils;

import java.io.Serializable;

public class Dimension implements Serializable {
   private Imperial hauteur;
   private Imperial longueur;
   private Imperial profondeur;

   public Dimension(Imperial hauteur, Imperial longueur){
      this.hauteur = hauteur;
      this.longueur = longueur;
   }
   public Dimension(Imperial hauteur, Imperial longueur, Imperial profondeur){
   this.hauteur = hauteur;
   this.longueur = longueur;
   this.profondeur = profondeur;
   }

   public Imperial getHauteur(){
      return this.hauteur;
   }

   public void setHauteur(Imperial hauteur){
      this.hauteur = hauteur;
   }
   public Imperial getLongueur(){
      return this.longueur;
   }

   public void setLongueur(Imperial longueur){
      this.longueur = longueur;
   }
   public Imperial getEpaisseur(){
      return this.profondeur;
   }

   public void setEpaisseur(Imperial profondeur){
      this.profondeur = profondeur;
   }


}
