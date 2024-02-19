package ca.ulaval.glo2004.domaine;


import ca.ulaval.glo2004.domaine.utils.Dimension;
import ca.ulaval.glo2004.domaine.utils.Imperial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class Toit implements Serializable {

    private String nom;
    private Dimension dimension;
    private Imperial positionX;
    private Imperial positionY;
    private Imperial positionZ;
    private Imperial distanceDeRetrait;
    private List<Vector<Double>> rainures;
    private UUID uuid;
    private List<Vector<Double>> positionMur;
    private float angle;
    private SensToit sens;
    private Imperial hauteur;
    private Imperial hauteurRallonge;

    public Toit(float angle, String nom, Imperial longueur, Imperial epaisseur, Imperial positionX, Imperial positionY, Imperial positionZ, SensToit sens) {
        this.angle = angle;
        this.uuid = UUID.randomUUID();
        this.nom = nom;
        hauteur = new Imperial((calculerHauteurToit(angle, longueur.convertirEnDouble())));
        hauteurRallonge = calculerHauteurRallonge();

        this.dimension = new Dimension(hauteur, longueur, epaisseur);

        this.positionX = positionX;

        this.positionY = positionY;

        this.positionZ = positionZ;

        this.uuid = UUID.randomUUID();

        this.rainures = new ArrayList<Vector<Double>>();

        this.positionMur = new ArrayList<Vector<Double>>();

        this.sens = sens;

        this.calculerPositions(this.nom, this.dimension, this.positionX, this.positionY, this.positionZ);

    }

    public String getNom() {
        return nom;
    }

    public Imperial calculerHauteurRallonge() {
        if (Chalet.largeurPourHauteurRallonge != null)
            return new Imperial((calculerHauteurToit(angle, Chalet.largeurPourHauteurRallonge.convertirEnDouble())));
        return null;
    }

    public Imperial getHauteur() {
        return this.dimension.getHauteur();
    }

    public Imperial getHauteurRallonge() {
        return this.hauteurRallonge;
    }

    public Imperial getEpaisseur() {
        return this.dimension.getEpaisseur();
    }

    public Imperial getLongueur() {
        return this.dimension.getLongueur();
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

    public float getAngle() {
        return this.angle;
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<Vector<Double>> getRainures() {
        return rainures;
    }

    public void setLongueur(Imperial newLongueur) {
        this.dimension.setLongueur(newLongueur);
        this.rainures.clear();
        calculerPositions(this.getNom(), this.dimension, this.getPositionX(), this.getPositionY(), this.getPositionZ());
    }

    public void setAngle(float newAngle) {
        if (newAngle > 0 && newAngle < 90) {
            this.angle = newAngle;
        }
        recalculerToit();
        hauteurRallonge = calculerHauteurRallonge();
        this.rainures.clear();
        calculerPositions(this.getNom(), this.dimension, this.getPositionX(), this.getPositionY(), this.getPositionZ());
    }

    public void setEpaisseur(Imperial nouvelleEpaisseur) {
        this.dimension.setEpaisseur(nouvelleEpaisseur);
        supprimerRainures();
        calculerPositions(this.nom, this.dimension, this.positionX, this.positionY, this.positionZ);

    }

    public void setPositionX(Imperial newX) {
        this.positionX = newX;
        supprimerRainures();
        calculerPositions(this.nom, this.dimension, this.positionX, this.positionY, this.positionZ);
    }

    public void setPositionY(Imperial newY) {
        this.positionY = newY;
        supprimerRainures();
        calculerPositions(this.nom, this.dimension, this.positionX, this.positionY, this.positionZ);
    }

    public void setPositionZ(Imperial nouvellePositionZ) {
        this.positionZ = nouvellePositionZ;
        supprimerRainures();
        calculerPositions(this.nom, this.dimension, this.positionX, this.positionY, this.positionZ);
    }

    public void setSens(SensToit sens) {
        this.sens = sens;
    }

    public void ajouterRainure(Vector<Double> rainure) {
        this.rainures.add(rainure);
    }

    public void modifierDimensionsSelonMurs() {
    }

    public void recalculerToit() {
        Imperial hauteur = new Imperial(calculerHauteurToit(this.getAngle(), this.getLongueur().convertirEnDouble()));
        this.dimension.setHauteur(hauteur);
    }

    private double calculerHauteurToit(float angleToit, double longueurMur) {
        
        return (double) Math.round(Math.tan(Math.toRadians(angleToit)) * longueurMur)/100;

    }

    private double calculerLongueurToit(float angleToit, double hauteurMur) {
        return (double) Math.round(((hauteurMur / Math.tan(Math.toRadians(angleToit))) * 100)) / 100;
    }

    private Vector<Double> nouvelleRainure(double positionX, double positionY, double positionZ) {
        Vector<Double> rainure = new Vector<>();
        rainure.add(positionX);
        rainure.add(positionY);
        rainure.add(positionZ);
        return rainure;
    }

    private void calculerPositions(String nomMur, Dimension dimension, Imperial posX, Imperial posY, Imperial posZ) {
        double positionToitX = posX.convertirEnDouble();//////
        double positionToitY = posY.convertirEnDouble();/////
        double positionToitZ = posZ.convertirEnDouble();////
        double longueurToit = dimension.getLongueur().convertirEnDouble();
        double hauteurToit = dimension.getHauteur().convertirEnDouble(); ///////
        double epaisseurToit = dimension.getEpaisseur().convertirEnDouble();//////
        double longueurMur = dimension.getLongueur().convertirEnDouble();




        //Sens == Facade-Arrière || Arrière-Facade
//        System.out.println(this.sens);
        if (this.sens == SensToit.FACADE_ARRIERE|| this.sens ==SensToit.ARRIERE_FACADE) {
            if (nomMur.equals("pignonGauche")) {

                //Externe
                positionToitX = positionToitX+epaisseurToit;
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit, positionToitZ));
                //Rainure Intenre
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit / 2));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY + epaisseurToit / 2, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
            } else if (nomMur.equals("pignonDroite")) {

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ-epaisseurToit/2.18));
                this.ajouterRainure((nouvelleRainure(positionToitX + epaisseurToit, positionToitY + epaisseurToit / 2, positionToitZ-epaisseurToit/2.18)));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY + epaisseurToit / 2, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY + hauteurToit, positionToitZ-epaisseurToit/2.18));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ-epaisseurToit/2.18));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ-epaisseurToit/2.18));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ + longueurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit, positionToitZ-epaisseurToit/2.18));


                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit / 2));

                this.ajouterRainure(nouvelleRainure(positionToitX - epaisseurToit, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ + longueurToit - epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
            } else if (nomMur.equals("rallongeVertical")) {
                positionToitZ = positionToitZ-epaisseurToit/2.18;
                double hauteurAngle1 = positionToitY + hauteurToit - calculerHauteurToit(angle, epaisseurToit / 2);
                double hauteurRainure = hauteurAngle1 - epaisseurToit / 2;
                double hauteurAngle2 = hauteurRainure - calculerHauteurToit(angle, epaisseurToit / 2);
                //Face derrière
                this.ajouterRainure(nouvelleRainure(positionToitX , positionToitY, positionToitZ)); //point 0
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit +epaisseurToit/2 , positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ)); //point 2 en bas a gauche/////
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit+epaisseurToit/2, positionToitY + hauteurToit, positionToitZ));

                //Face interne
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit / 2));//point 4 en haut a droite
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit+epaisseurToit/2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, hauteurAngle1, positionToitZ + epaisseurToit / 2)); //point en bas a droite
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit+epaisseurToit/2, hauteurAngle1, positionToitZ + epaisseurToit / 2));

                //Face interne petit prisme rectangulaire
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, hauteurRainure, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, hauteurRainure, positionToitZ + epaisseurToit / 2));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, hauteurAngle2, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, hauteurAngle2, positionToitZ + epaisseurToit));

//                this.ajouterRainure(nouvelleRainure(positionToitX - epaisseurToit, positionToitY + hauteurToit, positionToitZ - positionToitZ));
//                this.ajouterRainure(nouvelleRainure(positionToitX - epaisseurToit, positionToitY + hauteurToit + epaisseurToit / 2, positionToitZ - positionToitZ));
//                this.ajouterRainure(nouvelleRainure(positionToitX - epaisseurToit, positionToitY, positionToitZ));


            } else if (nomMur.equals("panneauSuperieur")) {
                double depth = calculerLongueurToit(angle, hauteurToit);
                this.ajouterRainure(nouvelleRainure(positionToitX  , positionToitY + hauteurToit, positionToitZ - positionToitZ - epaisseurToit/2.18));
                this.ajouterRainure(nouvelleRainure(positionToitX , positionToitY + hauteurToit + epaisseurToit / 2, positionToitZ - positionToitZ- epaisseurToit/2.18));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX , positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit));

                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit + epaisseurToit, positionToitY + hauteurToit, positionToitZ - positionToitZ- epaisseurToit/2.18));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit + epaisseurToit, positionToitY + hauteurToit + epaisseurToit / 2, positionToitZ - positionToitZ- epaisseurToit/2.18));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit + epaisseurToit, positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit + epaisseurToit, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit + epaisseurToit, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit));
                //Rainure Extenne à rajouter

            }
        } else if (this.sens == SensToit.DROITE_GAUCHE|| this.sens == SensToit.GAUCHE_DROITE) {
            if (nomMur.equals("pignonGauche")) {
                // À revoir les Z
                //Externe
                //positionToitX = positionToitX-epaisseurToit/2;
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ + epaisseurToit));

                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ + epaisseurToit / 2));
                //Rainure Intenre
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit / 2));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit/2, positionToitY, positionToitZ ));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit/2, positionToitY, positionToitZ ));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY, positionToitZ ));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ ));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit/2, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ ));
                System.out.println(nomMur);

            } else if (nomMur.equals("pignonDroite")) {
                positionToitZ = positionToitZ- epaisseurToit/2.18;
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ));
                this.ajouterRainure((nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ)));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + epaisseurToit / 2, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ));

                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ + epaisseurToit / 2));

                ///
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + epaisseurToit /2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit / 2));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + epaisseurToit/2, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit - epaisseurToit / 2, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY + hauteurToit - epaisseurToit / 2, positionToitZ + epaisseurToit));



            } else if (nomMur.equals("rallongeVertical")) {
                positionToitX = positionToitX+1*epaisseurToit;
                double hauteurAngle1 = positionToitY + hauteurToit - calculerHauteurToit(angle, epaisseurToit / 2);//
                double hauteurRainure = hauteurAngle1 - epaisseurToit / 2;
                double hauteurAngle2 = hauteurRainure - calculerHauteurToit(angle, epaisseurToit / 2);

                //Face derrière
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + longueurToit)); //point 0
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ-epaisseurToit/2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ + longueurToit)); //point 2 en bas a gauche/////
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ-epaisseurToit/2));

                //Face interne
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurToit));//point 4 en haut a droite
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ-epaisseurToit/2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, hauteurAngle1, positionToitZ + longueurToit)); //point en bas a droite
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, hauteurAngle1, positionToitZ -epaisseurToit/2));

                //Face interne petit prisme rectangulaire
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + longueurMur -epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, hauteurRainure, positionToitZ + longueurMur -epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit / 2, hauteurRainure, positionToitZ + epaisseurToit / 2));

                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ  + longueurMur -epaisseurToit / 2 ));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ + epaisseurToit / 2 ));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, hauteurAngle2, positionToitZ  + longueurMur -epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, hauteurAngle2, positionToitZ + epaisseurToit / 2));



            } else if (nomMur.equals("panneauSuperieur")) {

                //fixer bogue avec le mur embas
                //positionToitX = positionToitX  epaisseurToit/2;
                double depth = calculerLongueurToit(angle, hauteurToit);
                this.ajouterRainure(nouvelleRainure(0+epaisseurToit, positionToitY + hauteurToit, positionToitZ+ longueurMur));
                this.ajouterRainure(nouvelleRainure(0+epaisseurToit, positionToitY + hauteurToit+ epaisseurToit/2, positionToitZ+ longueurMur));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ+ longueurMur));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ+ longueurMur));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY + epaisseurToit/2, positionToitZ+ longueurMur));


                this.ajouterRainure(nouvelleRainure(0+epaisseurToit, positionToitY + hauteurToit, positionToitZ - epaisseurToit/2));
                this.ajouterRainure(nouvelleRainure(0+epaisseurToit, positionToitY + hauteurToit+ epaisseurToit/2, positionToitZ - epaisseurToit/2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ-epaisseurToit/2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY, positionToitZ- epaisseurToit/2));
                this.ajouterRainure(nouvelleRainure(positionToitX + epaisseurToit, positionToitY+ epaisseurToit/2, positionToitZ- epaisseurToit/2));

            }


        } else if (this.sens == SensToit.GAUCHE_DROITE) {
            if (nomMur.equals("pignonGauche")) {


                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ));


                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + epaisseurToit / 2, positionToitZ));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + hauteurToit, positionToitZ));

                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + epaisseurToit / 2, positionToitZ + epaisseurToit / 2));
                this.ajouterRainure(nouvelleRainure(positionToitX + longueurToit, positionToitY + hauteurToit, positionToitZ + epaisseurToit / 2));
                } else if (nomMur.equals("pignonDroite")) {
//
            } else if (nomMur.equals("rallongeVertical")) {
//
            } else if (nomMur.equals("panneauSuperieur")) {

            }
        }


    }

    public void supprimerRainures() {
        rainures.clear();

    }

}

