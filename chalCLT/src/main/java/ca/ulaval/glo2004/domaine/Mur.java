package ca.ulaval.glo2004.domaine;
import ca.ulaval.glo2004.domaine.utils.*;
import ca.ulaval.glo2004.domaine.utils.Dimension;

import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Mur implements Serializable {
    private String nom;
    private Dimension dimension;
    private Imperial minDistEntreAccessoire;

    private Imperial distanceDeRetrait;
    private Imperial positionX;
    private Imperial positionY;
    private Imperial positionZ;
    private List<Accessoire> accessoires;
    private UUID uuid;
    private List<Vector<Integer>> rainures;
    private List<Vector<Double>> positionMur;
    private List<Vector<Double>> verticesAccessoires;

    public Mur(String nom, Imperial hauteur, Imperial longueur, Imperial epaisseur, Imperial minDistEntreAccessoire, Imperial positionX, Imperial positionY, Imperial positionZ) {

        this.nom = nom;
        this.dimension = new Dimension(hauteur, longueur, epaisseur);
        this.minDistEntreAccessoire = minDistEntreAccessoire;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.accessoires = new ArrayList<Accessoire>();
        this.uuid = UUID.randomUUID();
        this.rainures = new ArrayList<Vector<Integer>>();
        this.positionMur = new ArrayList<Vector<Double>>();
        this.verticesAccessoires = new ArrayList<Vector<Double>>();
        this.distanceDeRetrait = new Imperial(0, 0, 1,4);
    }

    public UUID getUUID() {
        return uuid;
    }
    public String getNom() {
        return nom;
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
    public Imperial getPositionZ(){
        return positionZ;
    }
    public List<Accessoire> getAccessoires() {
        return accessoires;
    }
    public List<Vector<Integer>> getRainures() {
        return rainures;
    }
    public List<Vector<Double>> getPositionMur() {
        return positionMur;
    }
    public List<Vector<Double>> getVerticesAccessoires() {
        return verticesAccessoires;
    }
    public boolean getEmplacementCorrect(Accessoire accessoire) {

        return this.emplacementCorrect(accessoire);
    }

    public void setHauteur(Imperial newHeight) {
        this.dimension.setHauteur(newHeight);

    }
    public void setLongueur(Imperial newLongueur) {
        this.dimension.setLongueur(newLongueur);
    }
    public void setEpaisseur(Imperial nouvelleEpaisseur) {
        this.dimension.setEpaisseur(nouvelleEpaisseur);
    }
    public void setPositionX(Imperial newX) {
        this.positionX = newX;
    }
    public void setPositionY(Imperial newY) {
        this.positionY = newY;
    }
    public void setPositionZ(Imperial nouvellePositionZ){
        this.positionZ = nouvellePositionZ;
    }
    public void ajouterRainure(Vector<Integer> rainure){
        this.rainures.add(rainure);
    }
    public void ajouterPositionMur(Vector<Double> rainure){
        this.positionMur.add(rainure);
    }
    public void ajouterVerticesAccessoire(Vector<Double> verticesAccessoire ){
        this.verticesAccessoires.add(verticesAccessoire);
    }
    public void emptyVerticesAccessoire(){
        this.verticesAccessoires.clear();
    }

    public void modifierLongueurAccessoires(Imperial newLongueur, int idAccessoire) {
        for(Accessoire accessoire : this.getAccessoires()){
            if(accessoire.getIdAccessoire() == idAccessoire){
                accessoire.setLongueur(newLongueur);
            }
        }
    }
    public void modifierHauteurAccessoires(Imperial newHauteur, int idAccessoire) {
        for(Accessoire accessoire : this.getAccessoires()){
            if(accessoire.getIdAccessoire() == idAccessoire){
                accessoire.setHauteur(newHauteur);
            }
        }
    }
    public void modifierPositionXAccessoire(Imperial newPositionX, int nomAccessoire) {
        for(Accessoire accessoire : this.getAccessoires()){
            if(accessoire.getIdAccessoire() == nomAccessoire){
                accessoire.setPositionX(newPositionX);
            }
        }
    }
    public void modifierPositionYAccessoire(Imperial newPositionY, int nomAccessoire) {
        for(Accessoire accessoire : this.getAccessoires()){
            if(accessoire.getIdAccessoire() == nomAccessoire){
                accessoire.setPositionY(newPositionY);
            }
        }
    }
    public void addAccessoire(Accessoire accessoire) {
        this.accessoires.add(accessoire);
    }
    public void supprimerAccessoire(int idAccessoire) {
        Iterator<Accessoire> iterator = this.getAccessoires().iterator();

        while (iterator.hasNext()) {
            Accessoire accessoire = iterator.next();
            if (accessoire.getIdAccessoire() == idAccessoire) {
                iterator.remove();
            }
        }
    }
    public Imperial getMinDistEntreAccessoire() {
        return minDistEntreAccessoire;
    }

    public Imperial getDistanceRetrait() {
        return this.distanceDeRetrait;
    }

    public void setMinDistEntreAccessoire(Imperial nouvelleDist) {

        Imperial plusPetiteDistPossible = new Imperial(0, 3);

        if(nouvelleDist.convertirEnDouble() >= plusPetiteDistPossible.convertirEnDouble()) {
            this.minDistEntreAccessoire = nouvelleDist;

        } else {
            this.minDistEntreAccessoire = plusPetiteDistPossible;
        }

        for(Accessoire a : this.getAccessoires()) {
            setEmplacementsCorrects(a);
        }
    }

    public boolean emplacementCorrect(Accessoire accessoireDeplacer) {

        for (Accessoire a : this.accessoires)  {
            if(a.getIdAccessoire() != accessoireDeplacer.getIdAccessoire()) {
                if (intersection(accessoireDeplacer, a)) {
                    accessoireDeplacer.setEmplacementCorrect(false);
                    break;
                }
            }
        }
        return accessoireDeplacer.getEmplacementCorrect();
    }

    public void setDistanceRetrait(Imperial nouvelleDistanceDeRetrait){
        if(nouvelleDistanceDeRetrait.convertirEnDouble() > 0 && nouvelleDistanceDeRetrait.convertirEnDouble() < this.dimension.getEpaisseur().convertirEnDouble()) {
            this.distanceDeRetrait.modifierPouces(0);
            this.distanceDeRetrait = nouvelleDistanceDeRetrait;
            }
    }

    public void setEmplacementsCorrects(Accessoire accessoire) {
        for (Accessoire a : this.accessoires)  {
            if(a.getIdAccessoire() != accessoire.getIdAccessoire()) {
                if (intersection(accessoire, a)) {
                    accessoire.setEmplacementCorrect(false);
                } else {
                    accessoire.setEmplacementCorrect(true);
                }
            }

            double dist = minDistEntreAccessoire.convertirEnDouble();
            double longueurMur = dimension.getLongueur().convertirEnDouble() - dist;
            double hauteurMur = dimension.getHauteur().convertirEnDouble() - dist;
            double xPixel = accessoire.getPositionX().convertirEnDouble();
            double longueurAccessoire = accessoire.getLongueur().convertirEnDouble();
            double yPixel = accessoire.getPositionY().convertirEnDouble();
            double hauteurAccessoire = accessoire.getHauteur().convertirEnDouble();
            if(accessoire.getType().equals("fenetre")) {
                accessoire.setEmplacementCorrect(false);
                if(dist <= xPixel && xPixel + longueurAccessoire <= longueurMur && dist <= yPixel && yPixel + hauteurAccessoire <= hauteurMur) {
                    accessoire.setEmplacementCorrect(true);
                }
            }
            else {
                accessoire.setEmplacementCorrect(false);
                if(yPixel + hauteurAccessoire == hauteurMur && dist <= xPixel && xPixel + longueurAccessoire <= longueurMur) {
                    accessoire.setEmplacementCorrect(true);
                }
            }
        }

    }
    public boolean intersection (Accessoire a, Accessoire b) {
        double bLongueur = b.getLongueur().convertirEnDouble();
        double bHauteur = b.getHauteur().convertirEnDouble();
        double aLongueur = a.getLongueur().convertirEnDouble();
        double aHauteur = a.getHauteur().convertirEnDouble();
        double distMin = minDistEntreAccessoire.convertirEnDouble();

        return (a.getPositionX().convertirEnDouble()  < (b.getPositionX().convertirEnDouble()  + bLongueur + distMin)) && ((a.getPositionX().convertirEnDouble()  + aLongueur + distMin) > b.getPositionX().convertirEnDouble() ) && (a.getPositionY().convertirEnDouble()  < (b.getPositionY().convertirEnDouble()  + bHauteur + distMin)) && ((a.getPositionY().convertirEnDouble()  + aHauteur + distMin )> b.getPositionY().convertirEnDouble() );
    }

    public void replacerPorte(Accessoire accessoire) {
        Imperial sous1 = soustraction(dimension.getHauteur(), accessoire.getHauteur());
        sous1 = soustraction(sous1, minDistEntreAccessoire);
        accessoire.setPositionY(sous1);
    }

    public Imperial soustraction(Imperial premier, Imperial deuxieme){
        double pieds;
        double pouces;
        double numerateur;
        double denominateur;

        if(premier.obtenirPieds() < deuxieme.obtenirPieds()){
        }
        pieds = premier.obtenirPieds() - deuxieme.obtenirPieds();

        if (premier.obtenirPouces() < deuxieme.obtenirPouces()) {
            pieds = pieds - 1;
            pouces = 12 + premier.obtenirPouces() - deuxieme.obtenirPouces();
        }
        else{
            pouces = premier.obtenirPouces() - deuxieme.obtenirPouces();
        }
        if (premier.obtenirNumerateur() > 0 && deuxieme.obtenirNumerateur() > 0) {
            if (premier.obtenirDenominateur() == deuxieme.obtenirDenominateur()) {
                denominateur = premier.obtenirDenominateur();
                if (premier.obtenirNumerateur() < deuxieme.obtenirNumerateur()) {
                    pouces = pouces - 1;
                    numerateur = denominateur + premier.obtenirNumerateur() - deuxieme.obtenirNumerateur();
                } else {
                    numerateur = premier.obtenirNumerateur() - deuxieme.obtenirNumerateur();
                }

            } else {
                denominateur = premier.obtenirDenominateur() * deuxieme.obtenirDenominateur();

                if (premier.obtenirNumerateur() * deuxieme.obtenirDenominateur() < deuxieme.obtenirNumerateur() * premier.obtenirDenominateur()){
                    pouces = pouces - 1;
                    numerateur = denominateur + premier.obtenirNumerateur()* deuxieme.obtenirDenominateur() - deuxieme.obtenirNumerateur()* premier.obtenirDenominateur();
                }
                else{
                    numerateur = premier.obtenirNumerateur() * deuxieme.obtenirDenominateur() - deuxieme.obtenirNumerateur() * premier.obtenirDenominateur();
                }

                // Revoir code réduction fraction
                if(numerateur%16 == 0 && denominateur%16 == 0){
                    numerateur = numerateur / 16.0;
                    denominateur = denominateur/16.0;
                }
                else if(numerateur%8 == 0 && denominateur%8 == 0){
                    numerateur = numerateur / 8.0;
                    denominateur = denominateur/8.0;
                }
                else if(numerateur%4 == 0 && denominateur%4 == 0){
                    numerateur = numerateur / 4.0;
                    denominateur = denominateur/4.0;
                }

            }
            return new Imperial(pieds, pouces, numerateur, denominateur);
        } else if (premier.obtenirNumerateur() > 0){
            numerateur = premier.obtenirNumerateur();
            denominateur = premier.obtenirDenominateur();
            return new Imperial(pieds,pouces,numerateur,denominateur);
        }
        else if(deuxieme.obtenirDenominateur() > 0){
            pouces = pouces - 1;
            numerateur = 12 - deuxieme.obtenirNumerateur();
            denominateur = deuxieme.obtenirDenominateur();
            return new Imperial(pieds,pouces,numerateur,denominateur);
        }
        else{
            return new Imperial(pieds, pouces);
        }
    }
}


