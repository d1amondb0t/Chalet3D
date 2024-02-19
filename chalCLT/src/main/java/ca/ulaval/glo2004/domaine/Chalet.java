package ca.ulaval.glo2004.domaine;


import java.awt.Point;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import ca.ulaval.glo2004.domaine.DTO.ImperialDTO;
import ca.ulaval.glo2004.domaine.memento.SavedChalet;
import ca.ulaval.glo2004.domaine.utils.*;

public class Chalet implements Serializable {
    private List<Mur> murs;
    private List<Toit> toits = new ArrayList<>();
    private UUID uuid;
    private Imperial largeur = new Imperial(15);
    private Imperial hauteur = new Imperial(12);
    private Imperial epaisseurMur = new Imperial(0, 3);
    private Imperial minDistEntreAccessoire = new Imperial(0, 3);
    private SensToit sens;

    private double multiplicateur = ChaletController.multiplicateur;

    public static Imperial largeurPourHauteurRallonge;

    public static double largeurRallonge;


    public Chalet() {
        Imperial pos0X = new Imperial(0);
        Imperial pos0Y = new Imperial(0);
        Imperial pos0Z = new Imperial(0);

        Imperial epaisWall = this.getEpaisseurMur();

        Mur wall1 = new Mur("left", hauteur, largeur, epaisWall, minDistEntreAccessoire, pos0X, pos0Y, pos0Z);

        Mur wall2 = new Mur("back", hauteur, largeur, epaisWall, minDistEntreAccessoire, epaisWall, pos0Y, pos0Z);
        Imperial longWall2 = wall2.getLongueur();
        Imperial addition = addition(epaisWall, longWall2);
        Mur wall3 = new Mur("right", hauteur, largeur, epaisseurMur, minDistEntreAccessoire, addition, pos0Y, pos0Z);

        Imperial longWall3 = wall3.getLongueur();
        Imperial soustraction = soustraction(longWall3, epaisWall);
        Mur wall4 = new Mur("front", hauteur, largeur, epaisWall, minDistEntreAccessoire, epaisWall, pos0Y, soustraction);
        largeurPourHauteurRallonge = addition(largeur, epaisWall);
        this.sens = SensToit.ARRIERE_FACADE;

       // determinerToitSelonSens();

//        Toit pignonGauche = new Toit(30, "pignonGauche", largeur, epaisWall, wall1.getPositionX(), wall1.getPositionY(), wall1.getPositionZ(), this.sens);
//        Toit rallongeVertical = new Toit(30, "rallongeVertical", largeur, epaisWall, wall2.getPositionX(), wall2.getPositionY(), wall2.getPositionZ(), this.sens);
//        Toit pignonDroite = new Toit(30, "pignonDroite", largeur, epaisseurMur, wall3.getPositionX(), wall3.getPositionY(), wall3.getPositionZ(), this.sens);
//        Toit panneauSuperieur = new Toit(30, "panneauSuperieur", largeur, epaisWall, wall4.getPositionX(), wall4.getPositionY(), wall4.getPositionZ(), this.sens);

        this.murs = List.of(wall1, wall2, wall3, wall4);
        this.uuid = UUID.randomUUID();
        this.toits = determinerToitSelonSens();
        this.calculerRainure();

    }

    public Chalet(SavedChalet chalet) {
        Imperial epaisWall = this.getEpaisseurMur();
        this.murs = chalet.chalet.getMurs();
        this.toits = chalet.chalet.getToit();
        this.uuid = chalet.chalet.getUUID();
        this.sens = chalet.chalet.getSens();
        this.epaisseurMur = chalet.chalet.getEpaisseurMur();
        this.calculerRainure();
        largeurPourHauteurRallonge = addition(largeur, epaisWall);
    }

    public UUID getUUID() {
        return uuid;
    }

    public Chalet(List<Mur> murs, List<Toit> toit, SensToit sens) {
        this.murs = murs;
        this.toits = toit;
        this.sens = sens;

    }



    public double getHauteurToit() {
        for (Toit toit : this.toits) {
            if (Objects.equals(toit.getNom(), "panneauSuperieur"))
            { return toit.getHauteur().convertirEnDouble();}
        }
        return 0;
    }

    public Imperial getLargeur() {
        return this.largeur;
    }
    public Imperial getHauteur() {
        return this.hauteur;
    }
    public Imperial getMinDistEntreAccessoire() {
        return this.minDistEntreAccessoire;
    }


    public void setEpaisseurMur(Imperial newEpais) {
        this.epaisseurMur = newEpais;
    }
    public Imperial getEpaisseurMur() {
        return this.epaisseurMur;
    }

    public SensToit getSens() {
        return this.sens;
    }

    public void setSens(SensToit sensDuToit){this.sens = sensDuToit;}


    // Getters

    public Mur getMur(String nomMur){
        for(Mur mur : murs){
            if(mur.getNom().equals(nomMur)){
                return mur;
            }
        }
        return null;
    }

    public List<Mur> getMurs() {
        return murs;
    }

    public List<Toit> getToit() {
        return toits;
    }

    public void modifierSensToit(String sensDuToit){
        if (  sensDuToit.equals("DROITE-GAUCHE")){
            this.setSens(SensToit.DROITE_GAUCHE);
        }
        else if ( sensDuToit .equals("GAUCHE-DROITE")){
            this.setSens(SensToit.GAUCHE_DROITE);
        }
        else if(sensDuToit.equals(("ARRIERE-FACADE"))) {
            this.setSens(SensToit.ARRIERE_FACADE);
        }
        else{
            this.setSens(SensToit.FACADE_ARRIERE);
        }
        determinerToitSelonSens();
    }


    public List<Toit> determinerToitSelonSens(){
        this.toits.clear();
        Mur left = this.murs.get(0);
        Mur back = this.murs.get(1);
        Mur right = this.murs.get(2);
        Mur front = this.murs.get(3);
        Imperial epaisWall = this.epaisseurMur;
            if (this.sens == SensToit.FACADE_ARRIERE || this.sens== SensToit.ARRIERE_FACADE)
            {
                Toit pignonGauche = new Toit(15, "pignonGauche", largeur, epaisWall, left.getPositionX(), left.getPositionY(), left.getPositionZ(), this.sens);
                Toit rallongeVertical = new Toit(15, "rallongeVertical", largeur, epaisWall, back.getPositionX(), back.getPositionY(), back.getPositionZ(), this.sens);
                largeurRallonge = rallongeVertical.getLongueur().convertirEnDouble();
                Toit pignonDroite = new Toit(15, "pignonDroite", largeur, epaisseurMur, right.getPositionX(), right.getPositionY(), right.getPositionZ(), this.sens);
                Toit panneauSuperieur = new Toit(15, "panneauSuperieur", largeur, epaisWall, front.getPositionX(), front.getPositionY(), front.getPositionZ(), this.sens);
                toits.add(pignonGauche);
                toits.add(rallongeVertical);
                toits.add(pignonDroite);
                toits.add(panneauSuperieur);
            }

            else if (this.sens == SensToit.DROITE_GAUCHE ||this.sens == SensToit.GAUCHE_DROITE)
            {
                Toit pignonGauche = new Toit(15, "pignonGauche", largeur, epaisWall, front.getPositionX(), front.getPositionY(), front.getPositionZ(), this.sens);
                Toit rallongeVertical = new Toit(15, "rallongeVertical", largeur, epaisWall, left.getPositionX(), left.getPositionY(), left.getPositionZ(), this.sens);
                Toit pignonDroite = new Toit(15, "pignonDroite", largeur, epaisseurMur, back.getPositionX(), back.getPositionY(), back.getPositionZ(), this.sens);
                Toit panneauSuperieur = new Toit(15, "panneauSuperieur", largeur, epaisWall, right.getPositionX(), right.getPositionY(), right.getPositionZ(), this.sens);
                toits.add(pignonGauche);
                toits.add(rallongeVertical);
                toits.add(pignonDroite);
                toits.add(panneauSuperieur);
            }
        return toits;
    }


    public void modifierLongueurMursParalleles(String newLongueur, boolean murs){
        Imperial longueurImperial = new Imperial(newLongueur);
        double longueurPixel = longueurImperial.convertirEnDouble();
        double ancienneLongueur;
        double rapportPositionLongueur;
        Mur mur0 = this.murs.get(0);
        Mur mur1 = this.murs.get(1);
        Mur mur2 = this.murs.get(2);
        Mur mur3 = this.murs.get(3);
        Imperial base = new Imperial(15);
        Toit toit0 =  this.toits.get(0); //Pignon Gauche
        Toit toit1 = this.toits.get(1); // rallongeVertical
        Toit toit2 = this.toits.get(2); // Pignon Droite
        Toit toit3 =  this.toits.get(3); //Panneau Supérieur




        if((this.getSens().equals(SensToit.FACADE_ARRIERE)) && murs || (this.getSens().equals((SensToit.ARRIERE_FACADE))&& murs)){
            toit0.setLongueur(longueurImperial);
            toit2.setLongueur(longueurImperial);
        }
        else if((this.getSens().equals(SensToit.FACADE_ARRIERE)) && !murs || (this.getSens().equals((SensToit.ARRIERE_FACADE))&& !murs)) {
            toit1.setLongueur(longueurImperial);
            toit3.setLongueur(longueurImperial);
            largeurRallonge = toit1.getLongueur().convertirEnDouble();

        }
        else if((this.getSens().equals(SensToit.GAUCHE_DROITE)) && murs || (this.getSens().equals((SensToit.DROITE_GAUCHE))&& murs)) {
            toit1.setLongueur(longueurImperial);
            largeurRallonge = toit1.getLongueur().convertirEnDouble();
            toit3.setLongueur(longueurImperial);
        }
        else
        {
            toit0.setLongueur(longueurImperial);
            toit2.setLongueur(longueurImperial);
        }

        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;

        if(!murs) {
            ancienneLongueur = mur1.getLongueur().convertirEnDouble();
            rapportPositionLongueur = longueurPixel/ancienneLongueur;

            mur1.setLongueur(longueurImperial);
            for(Accessoire accessoire : mur1.getAccessoires()) {
                accessoire.setPositionX(multiplication(accessoire.getPositionX(),rapportPositionLongueur));
            }

            mur2.setPositionX(addition(mur1.getPositionX(),longueurImperial));
            mur3.setLongueur(longueurImperial);
            for(Accessoire accessoire : mur3.getAccessoires()) {
                accessoire.setPositionX(multiplication(accessoire.getPositionX(),rapportPositionLongueur));
            }

            supprimerRainures();
            calculerRainure();
        } else if(murs){
            //Changer mur à droite-gauche
            ancienneLongueur = mur0.getLongueur().convertirEnDouble();
            rapportPositionLongueur = longueurPixel/ancienneLongueur;
            mur0.setLongueur(longueurImperial);

            for(Accessoire accessoire : mur0.getAccessoires()) {
               accessoire.setPositionX(multiplication(accessoire.getPositionX(), rapportPositionLongueur));
            }

            ancienneLongueur = mur2.getLongueur().convertirEnDouble();
            rapportPositionLongueur = longueurPixel/ancienneLongueur;
            mur2.setLongueur(longueurImperial);

            for(Accessoire accessoire : mur2.getAccessoires()) {
                accessoire.setPositionX(multiplication(accessoire.getPositionX(), rapportPositionLongueur));
            }

            mur3.setPositionZ(soustraction(mur2.getEpaisseur(), addition(mur2.getPositionZ(),mur2.getLongueur())));
            supprimerRainures();
            calculerRainure();
        }

        for (Accessoire accessoire: mur0.getAccessoires()) {
            mur0.setEmplacementsCorrects(accessoire);
            if (emplacementCorrect) {emplacementCorrect = mur0.getEmplacementCorrect(accessoire);}
            if (accessoireDansMur) {accessoireDansMur = this.verifierAccessoireDansMur(accessoire.getPositionX() , accessoire.getPositionY(), accessoire.getLongueur(), accessoire.getHauteur(), mur0.getNom());}
        }
        for (Accessoire accessoire: mur1.getAccessoires()) {
            mur1.setEmplacementsCorrects(accessoire);
            if (emplacementCorrect) {emplacementCorrect = mur1.getEmplacementCorrect(accessoire);}
            if (accessoireDansMur) {accessoireDansMur = this.verifierAccessoireDansMur(accessoire.getPositionX(), accessoire.getPositionY(), accessoire.getLongueur(), accessoire.getHauteur(), mur1.getNom());}
        }
        for (Accessoire accessoire: mur2.getAccessoires()) {
            mur2.setEmplacementsCorrects(accessoire);
            if (emplacementCorrect) {emplacementCorrect = mur2.getEmplacementCorrect(accessoire);}
            if (accessoireDansMur) {accessoireDansMur = this.verifierAccessoireDansMur(accessoire.getPositionX(), accessoire.getPositionY(), accessoire.getLongueur(), accessoire.getHauteur(), mur2.getNom());}
        }
        for (Accessoire accessoire: mur3.getAccessoires()) {
            mur3.setEmplacementsCorrects(accessoire);
            if (emplacementCorrect) {emplacementCorrect = mur3.getEmplacementCorrect(accessoire);}
            if (accessoireDansMur) {accessoireDansMur = this.verifierAccessoireDansMur(accessoire.getPositionX(), accessoire.getPositionY(), accessoire.getLongueur(), accessoire.getHauteur(), mur3.getNom());}
        }

    }

    public void modifierAngle(float newAngle){
        for(Toit  toit : this.toits){
            toit.setAngle(newAngle);
        }
    }

    public void modifierHauteur(String nouvelleHauteur){
        Imperial hauteurImperial = new Imperial(nouvelleHauteur) ;
        double hauteurPixel = hauteurImperial.convertirEnDouble();
        double rapportPositionHauteur;
        double ancienneHauteur;
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;

        for(Mur mur : murs){
            ancienneHauteur = mur.getHauteur().convertirEnDouble();
            rapportPositionHauteur = hauteurPixel/ancienneHauteur;
            mur.setHauteur(hauteurImperial);
            // Changer la positionY d'un accessoire porte
            for(Accessoire accessoire : mur.getAccessoires()){
                if(accessoire.getType() == "Porte"){
                    Imperial newPositionY = soustraction(hauteurImperial,accessoire.getHauteur());
                    accessoire.setPositionY(newPositionY);
                }
                else {
                    accessoire.setPositionY(multiplication(accessoire.getPositionY(),rapportPositionHauteur));
                }

                mur.getEmplacementCorrect(accessoire);
                mur.setEmplacementsCorrects(accessoire);
                if(emplacementCorrect) {
                    emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                }
                if(accessoireDansMur) {
                    accessoireDansMur = this.verifierAccessoireDansMur(accessoire.getPositionX(), accessoire.getPositionY(), accessoire.getLongueur(), accessoire.getHauteur(), mur.getNom());
                }
            }
        }
        supprimerRainures();
        calculerRainure();
    }

    public void modifierLongueur(String  newLongueur){
        Imperial longueurImperial = new Imperial(newLongueur);
        double longueurPixel = longueurImperial.convertirEnDouble();

        // À revoir la logique
        Mur mur0 = this.murs.get(0);
        Mur mur1 = this.murs.get(1);
        Mur mur2 = this.murs.get(2);
        Mur mur3 = this.murs.get(3);

        mur0.setLongueur(longueurImperial);
        mur2.setLongueur(longueurImperial);

        mur1.setPositionX(addition(mur0.getPositionX(),mur0.getLongueur()));
        mur3.setPositionX(soustraction(mur2.getPositionX(),mur1.getLongueur()));
        mur3.setPositionY(soustraction(addition(mur0.getPositionX(), mur0.getEpaisseur()),mur0.getEpaisseur()));
        supprimerRainures();
        calculerRainure();
    }



    public Imperial addition(Imperial premier, Imperial deuxieme){
        double pieds;
        double pouces;
        double numerateur;
        double denominateur;

        pieds = premier.obtenirPieds() + deuxieme.obtenirPieds();
        pouces = premier.obtenirPouces() + deuxieme.obtenirPouces();

        if (pouces >= 12.0){

            pieds =  (double) (int) pieds + (int) pouces / 12;
            pouces = pouces % 12.0;
        }
        if (premier.obtenirNumerateur() > 0 && deuxieme.obtenirNumerateur() > 0) {
            if(premier.obtenirDenominateur() == deuxieme.obtenirDenominateur()){
                numerateur = premier.obtenirNumerateur()+ deuxieme.obtenirNumerateur();
                denominateur = premier.obtenirDenominateur();
            }
            else{
                // A peaufiner... ca on devrait peut-être mettre sur 128 tout simplement...
                numerateur = premier.obtenirNumerateur() * deuxieme.obtenirDenominateur() + deuxieme.obtenirNumerateur() * premier.obtenirDenominateur();
                denominateur = premier.obtenirDenominateur() * deuxieme.obtenirDenominateur();
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
            if (numerateur >= denominateur){
                pouces = pouces + (numerateur / denominateur);
                numerateur = numerateur % denominateur;
            }

            return new Imperial(pieds,pouces,numerateur,denominateur);
        }
        else if (premier.obtenirNumerateur() > 0){
            numerateur = premier.obtenirNumerateur();
            denominateur = premier.obtenirDenominateur();
            return new Imperial(pieds,pouces,numerateur,denominateur);
        }
else if(deuxieme.obtenirDenominateur() > 0){
            numerateur = deuxieme.obtenirNumerateur();
            denominateur = deuxieme.obtenirDenominateur();
            return new Imperial(pieds,pouces,numerateur,denominateur);
        }
        else{
            return new Imperial(pieds,pouces);
        }
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

    public Imperial multiplication(Imperial premier, double deuxieme){
        double pieds;
        double pouces = 0 ;
        double numerateur;
        double denominateur;

        pieds = premier.obtenirPieds() * deuxieme;
        double decimal = pieds - Math.floor(pieds);
        pouces = premier.obtenirPouces() * deuxieme;
        if(decimal != 0){
            pouces = pouces + 12 * decimal;
        }
        decimal = pouces - Math.floor(pouces);
        if (pouces >= 12.0){
            pieds =  pieds + pouces / 12.0;
            pouces = pouces % 12.0;
        }
        if (premier.obtenirNumerateur() > 0 || decimal != 0) {
            numerateur = premier.obtenirNumerateur() * deuxieme;
            denominateur = premier.obtenirDenominateur();
            if (decimal!=0){
                numerateur = Math.floor(numerateur + decimal * denominateur);
            }
            if (numerateur >= denominateur){
                pouces = pouces + (numerateur / denominateur);
                numerateur = numerateur % denominateur;
            }
            return new Imperial(pieds,pouces,numerateur,denominateur);
        }
        else{
            return new Imperial(pieds,pouces);
        }
    }

    public void modifierEpaisseur(Imperial newThickness){
        setEpaisseurMur(newThickness);
        for(Mur mur : this.murs){
            mur.setEpaisseur(newThickness);
            supprimerRainures();
            calculerRainure();
        }
    }

    public void modifierDistMinimaleAccessoire(String valeurDistMin) {
        Imperial nouvelleDist = new Imperial(valeurDistMin);
        Imperial plusPetiteDistPossible = new Imperial(0, 3);
        double nouvelleDistDouble = nouvelleDist.convertirEnDouble();
        double plusPetiteDistancePossibleDouble = plusPetiteDistPossible.convertirEnDouble();
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;
        for(Mur mur :  this.getMurs()) {
            if (plusPetiteDistancePossibleDouble >= nouvelleDistDouble) {
                mur.setMinDistEntreAccessoire(plusPetiteDistPossible);
            } else {
                mur.setMinDistEntreAccessoire(nouvelleDist);
            }
            for (Accessoire accessoire : mur.getAccessoires()) {
                if (emplacementCorrect) {
                    emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                }
                if (accessoireDansMur) {
                    accessoireDansMur = this.verifierAccessoireDansMur(accessoire.getPositionX() , accessoire.getPositionY(), accessoire.getLongueur(), accessoire.getHauteur(), mur.getNom());
                }

            }
        }
    }

    public boolean verifierAccessoireDansMur(Imperial positionX, Imperial positionY, Imperial longueurAccessoire,Imperial hauteurAccessoire, String nomMur){
        Mur mur = this.getMur(nomMur);
        Imperial accessoirePositionX = addition(positionX, mur.getPositionX());
        Imperial accessoirePositionY = addition(positionY, mur.getPositionY());
        double longMur = mur.getLongueur().convertirEnDouble();
        double hauteurMur = mur.getHauteur().convertirEnDouble();
        double longAccessoire = longueurAccessoire.convertirEnDouble();
        double hautAccessoire = hauteurAccessoire.convertirEnDouble();

        if (accessoirePositionX.convertirEnDouble()  >= mur.getPositionX().convertirEnDouble()  &&
               accessoirePositionY.convertirEnDouble() >= mur.getPositionY().convertirEnDouble()&&
                accessoirePositionX.convertirEnDouble() + longAccessoire <= mur.getPositionX().convertirEnDouble() + longMur &&
                accessoirePositionY.convertirEnDouble() + hautAccessoire <= mur.getPositionY().convertirEnDouble() + hauteurMur
       ) {
            return true;
        }
        return false;
    }
    public void calculerRainure() {
        double backLong = 0;
        double rightLong = 0;
        for (Mur mur : this.murs) {
            int ajustementRetrait = (int)(mur.getDistanceRetrait().convertirEnDouble() /2 * ChaletController.multiplicateur);
            double epaisMur = mur.getEpaisseur().convertirEnDouble() * ChaletController.multiplicateur;
            String nomMur = mur.getNom();
            //in = (int)mur.distanceRetraitPixel();
            if (this.getSens() == SensToit.ARRIERE_FACADE || this.getSens() == SensToit.FACADE_ARRIERE) {
                if (nomMur.equals("left")) {
                    double longMur = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;

                    mur.ajouterRainure(nouvelleRainure((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ- (int)epaisMur/2));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int)ChaletController.positionInitialeZ - (int)epaisMur/2));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2- (int)epaisMur/2 + ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2- (int)epaisMur/2 + ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur - epaisMur / 2 - ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX  + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur - epaisMur / 2 - ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur)));
                    mur.ajouterRainure(nouvelleRainure((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur)));

                } else if (mur.getNom().equals("back")) {
                    backLong = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    double posX = ChaletController.positionInitialeX + epaisMur;

                    mur.ajouterRainure(nouvelleRainure((int) posX+ ajustementRetrait+ (int)epaisMur/2 - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ ));
                    mur.ajouterRainure(nouvelleRainure((int) posX + ajustementRetrait + (int)epaisMur/2 - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ ));
                    mur.ajouterRainure(nouvelleRainure((int) posX +ajustementRetrait+ (int)epaisMur/2 - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ  + (int) epaisMur / 2));
                    mur.ajouterRainure(nouvelleRainure((int) posX - ajustementRetrait - (int)epaisMur/2  + (int) backLong - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ  + (int) epaisMur / 2)));

                    mur.ajouterRainure(nouvelleRainure((int) posX - ajustementRetrait- (int)epaisMur/2 + (int) backLong - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ) ));
                    mur.ajouterRainure(nouvelleRainure((int)posX-ajustementRetrait- (int)epaisMur/2 + (int) backLong, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ) ));
                    mur.ajouterRainure(nouvelleRainure((int) posX-ajustementRetrait- (int)epaisMur/2 + (int) backLong, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ - (int) epaisMur / 2) ));
                    mur.ajouterRainure(nouvelleRainure((int) posX  +ajustementRetrait+(int)epaisMur/2 - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ - (int) epaisMur / 2) ));

                }else if (nomMur.equals("right")) {
                    rightLong = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    double posX = ChaletController.positionInitialeX + epaisMur + backLong;

                    mur.ajouterRainure(nouvelleRainure((int) posX , (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ- (int)epaisMur/2));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2) , (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ- (int)epaisMur/2));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2) , (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2- (int)epaisMur/2 +ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur) , (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ+ ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur) , (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong - epaisMur / 2 - ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2) , (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong - epaisMur / 2 -ajustementRetrait)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2 ) , (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX) , (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong)));
                }  else if (mur.getNom().equals("front")) {
                    double longMur = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    double posZ = ChaletController.positionInitialeZ + rightLong - epaisMur;
                    double posX = ChaletController.positionInitialeX + epaisMur;

                    mur.ajouterRainure(nouvelleRainure((int) posX  + ajustementRetrait + (int)epaisMur/2 - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) posZ + (int) epaisMur/2 ));
                    mur.ajouterRainure(nouvelleRainure((int) posX + ajustementRetrait  + (int)epaisMur/2 - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) posZ + (int) epaisMur/2 ));
                    mur.ajouterRainure(nouvelleRainure((int) posX + ajustementRetrait  + (int)epaisMur/2 - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) posZ  ));
                    mur.ajouterRainure(nouvelleRainure((int) posX - ajustementRetrait- (int)epaisMur/2 + (int) longMur - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (posZ) ));

                    mur.ajouterRainure(nouvelleRainure((int) posX - ajustementRetrait - (int)epaisMur/2+ (int) longMur - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur/2 )));
                    mur.ajouterRainure(nouvelleRainure((int) posX- ajustementRetrait - (int)epaisMur/2 + (int) longMur, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur/2 )));
                    mur.ajouterRainure(nouvelleRainure((int) posX- ajustementRetrait - (int)epaisMur/2+ (int) longMur, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur )));
                    mur.ajouterRainure(nouvelleRainure((int) posX+ ajustementRetrait  + (int)epaisMur/2 - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur )));
                }
            }
            else{
                if (nomMur.equals("left")) {
                    double longMur = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    mur.ajouterRainure(nouvelleRainure((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur - epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur - epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (ChaletController.positionInitialeX + epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur)));
                    mur.ajouterRainure(nouvelleRainure((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + longMur)));
                } else if (mur.getNom().equals("back")) {
                    backLong = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    double posX = ChaletController.positionInitialeX + epaisMur;

                    mur.ajouterRainure(nouvelleRainure((int) posX - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ));
                    mur.ajouterRainure(nouvelleRainure((int) posX - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ));
                    mur.ajouterRainure(nouvelleRainure((int) posX - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ + (int) epaisMur / 2));
                    mur.ajouterRainure(nouvelleRainure((int) posX + (int) backLong - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + (int) epaisMur / 2)));

                    mur.ajouterRainure(nouvelleRainure((int) posX+ (int) backLong - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ)));
                    mur.ajouterRainure(nouvelleRainure((int) posX + (int) backLong, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ)));
                    mur.ajouterRainure(nouvelleRainure((int) posX + (int) backLong, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ - (int) epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) posX - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ - (int) epaisMur / 2)));

                } else if (nomMur.equals("right")) {
                    rightLong = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    double posX = ChaletController.positionInitialeX + epaisMur + backLong;

                    mur.ajouterRainure(nouvelleRainure((int) (posX), (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2), (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeZ));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong - epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong - epaisMur / 2)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX - epaisMur / 2), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong)));
                    mur.ajouterRainure(nouvelleRainure((int) (posX), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeZ + rightLong)));
                } else if (mur.getNom().equals("front")) {
                    double longMur = mur.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                    double posZ = ChaletController.positionInitialeZ + rightLong - epaisMur;
                    double posX = ChaletController.positionInitialeX + epaisMur;

                    mur.ajouterRainure(nouvelleRainure((int) posX - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) posZ + (int) epaisMur));
                    mur.ajouterRainure(nouvelleRainure((int) posX + ajustementRetrait - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) posZ + (int) epaisMur));
                    mur.ajouterRainure(nouvelleRainure((int) posX + ajustementRetrait - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) posZ + (int) epaisMur / 2));
                    mur.ajouterRainure(nouvelleRainure((int) posX - ajustementRetrait + (int) longMur - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur / 2)));

                    mur.ajouterRainure(nouvelleRainure((int) posX - ajustementRetrait + (int) longMur - (int) epaisMur / 2, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur)));
                    mur.ajouterRainure(nouvelleRainure((int) posX + (int) longMur, (int) ChaletController.positionInitialeY, (int) (posZ + (int) epaisMur)));
                    mur.ajouterRainure(nouvelleRainure((int) posX + (int) longMur, (int) ChaletController.positionInitialeY, (int) (posZ  + 3 * (int) epaisMur/2)));
                    mur.ajouterRainure(nouvelleRainure((int) posX - (int) epaisMur, (int) ChaletController.positionInitialeY, (int) (posZ + 3 * (int) epaisMur/2)));

                }
            }
        }
    }


    public void supprimerRainures(){
        for (Mur mur : this.murs) {
            mur.getRainures().clear();
        }
    }

    private Vector<Integer> nouvelleRainure(int positionX, int positionY, int positionZ) {
        Vector<Integer> rainure = new Vector<>();
        rainure.add(positionX);
        rainure.add(positionY);
        rainure.add(positionZ);
        return rainure;
    }

    public double convertirImperialEnPixelDTO(ImperialDTO dimension){
        if (dimension.obtenirNumerateur() > 0) {
            return (dimension.obtenirPieds() * 12 +  dimension.obtenirPouces() +
                    dimension.obtenirNumerateur() / dimension.obtenirDenominateur()) * 4;
        }
        else {  return (dimension.obtenirPieds() * 12 + dimension.obtenirPouces()) * 4;
        }
    }

    public boolean ajouterAccessoire(String nomMur, String type, String hauteur, String longueur, String positionX, String positionY, String positionZ) {
        Imperial imperialHauteur = new Imperial(hauteur);
        Imperial imperialLongueur = new Imperial(longueur);
        Imperial imperialPositionX = new Imperial(positionX);
        Imperial imperialPositionZ = new Imperial(0);
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;

        for (Mur mur : this.getMurs()) {
            if (Objects.equals(mur.getNom(), nomMur))
            {
                if (Objects.equals(type, "Porte")) {
                    Imperial imperialPositionY = new Imperial(String.valueOf(mur.getMinDistEntreAccessoire()));
                    imperialPositionY = soustraction(soustraction( mur.getHauteur(), imperialHauteur),mur.getMinDistEntreAccessoire());
                    Accessoire accessoire = new Accessoire(type, imperialHauteur, imperialLongueur, imperialPositionX, imperialPositionY, imperialPositionZ);
                    mur.addAccessoire(accessoire);
                    mur.setEmplacementsCorrects(accessoire);


                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (mur.getNom().equals(nomMur)) {
                        accessoireDansMur = this.verifierAccessoireDansMur(imperialPositionX, imperialPositionY, imperialLongueur, imperialHauteur, nomMur);
                    }
                }
                else {
                    Imperial imperialPositionY = new Imperial(positionY);
                    Accessoire accessoire = new Accessoire(type, imperialHauteur, imperialLongueur, imperialPositionX, imperialPositionY, imperialPositionZ);
                    mur.addAccessoire(accessoire);
                    mur.setEmplacementsCorrects(accessoire);


                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (mur.getNom().equals(nomMur)) {
                        accessoireDansMur = this.verifierAccessoireDansMur(imperialPositionX, imperialPositionY, imperialLongueur, imperialHauteur, nomMur);
                    }
                }

            }}

        this.calculePositionAccessoires();
        return accessoireDansMur && emplacementCorrect;
    }
    public Accessoire getPositionClicked(String nomMur, Point pointClicked) {
        Mur mur = this.getMur(nomMur);
        if (mur != null) {
            for (Accessoire accessoire : mur.getAccessoires()) {
                double longAccessoire = accessoire.getLongueur().convertirEnDouble() * ChaletController.multiplicateur;
                double hautAccessoire = accessoire.getHauteur().convertirEnDouble() * ChaletController.multiplicateur;

                if (pointClicked.getX() >= accessoire.getPositionX().convertirEnDouble() * ChaletController.multiplicateur + ChaletController.positionInitialeX &&
                        pointClicked.getX() <= accessoire.getPositionX().convertirEnDouble()* ChaletController.multiplicateur + ChaletController.positionInitialeX + longAccessoire &&
                        pointClicked.getY() >= accessoire.getPositionY().convertirEnDouble()* ChaletController.multiplicateur + ChaletController.positionInitialeY &&
                        pointClicked.getY() <= accessoire.getPositionY().convertirEnDouble()* ChaletController.multiplicateur + ChaletController.positionInitialeY + hautAccessoire) {
                    return accessoire;
                }
            }
        }
        return null;
    }

    public boolean modifierLongueurAccessoire(String newLongueur, int idAccessoire) {
        Imperial imperialLongueur = new Imperial(newLongueur);
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;

        double longDouble = imperialLongueur.convertirEnDouble();
        for (Mur mur : this.getMurs()) {
            double longMur = mur.getLongueur().convertirEnDouble();
            for (Accessoire accessoire : mur.getAccessoires()) {
                if (accessoire.getIdAccessoire() == idAccessoire)
//                    && accessoire.getPositionX().convertirEnDouble() + ChaletController.positionInitialeX + longDouble <= mur.getPositionX().convertirEnDouble() + longMur
                 {
                    mur.modifierLongueurAccessoires(imperialLongueur, idAccessoire);
                    mur.setEmplacementsCorrects(accessoire);

                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (accessoire.getIdAccessoire() == idAccessoire) {
                        accessoireDansMur = (accessoire.getPositionX().convertirEnDouble() + ChaletController.positionInitialeX + longDouble <= mur.getPositionX().convertirEnDouble() + longMur);
                    }
                }
            }
        }
        return accessoireDansMur && emplacementCorrect;
    }

    public void modifierHauteurAccessoire(String newHauteur, int idAccessoire) {
        Imperial imperialHauteur = new Imperial(newHauteur);
        double hauteurDouble = imperialHauteur.convertirEnDouble();
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;
        for (Mur mur : this.getMurs()) {
            double hautMur = mur.getHauteur().convertirEnDouble();
            for (Accessoire accessoire : mur.getAccessoires()) {
                if (accessoire.getIdAccessoire() == idAccessoire) {
                    mur.modifierHauteurAccessoires(imperialHauteur, idAccessoire);
                    mur.setEmplacementsCorrects(accessoire);
                    if (accessoire.getType().equals("Porte")) {
                        mur.replacerPorte(accessoire);
                        mur.setEmplacementsCorrects(accessoire);
                    }

                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (accessoire.getIdAccessoire() == idAccessoire) {
                        accessoireDansMur = (accessoire.getPositionY().convertirEnDouble() + ChaletController.positionInitialeX + hauteurDouble <= mur.getPositionY().convertirEnDouble() + hautMur);
                    }
                }
            }
        }
    }

    public void modifierPositionXAccessoire(String newPositionX, int nomAccessoire) {
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;
        Imperial positionXImperial = new Imperial(newPositionX);
        for (Mur mur : this.getMurs()) {

            double longMur = mur.getLongueur().convertirEnDouble();

            for (Accessoire accessoire : mur.getAccessoires()) {
                double longAccessoire = accessoire.getLongueur().convertirEnDouble();

                if (accessoire.getIdAccessoire() == nomAccessoire) {
                    mur.modifierPositionXAccessoire(positionXImperial, nomAccessoire);
                    mur.setEmplacementsCorrects(accessoire);

                    for(Accessoire autreAccessoire : mur.getAccessoires()) {
                        if(autreAccessoire.getIdAccessoire() != accessoire.getIdAccessoire()) {
                            mur.setEmplacementsCorrects(autreAccessoire);
                        }
                    }

                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (accessoire.getIdAccessoire() == nomAccessoire) {
                        accessoireDansMur = (accessoire.getPositionX().convertirEnDouble() + ChaletController.positionInitialeX + longAccessoire <= mur.getPositionX().convertirEnDouble() + longMur);
                    }
                }
            }
        }
    }

    public void modifierPositionYAccessoire(String newPositionY, int nomAccessoire) {
        Imperial positionImperialY = new Imperial(newPositionY);
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;
        for (Mur mur : this.getMurs()) {
            double hautMur = mur.getHauteur().convertirEnDouble();
            for (Accessoire accessoire : mur.getAccessoires()) {
                double hautAccessoire = accessoire.getHauteur().convertirEnDouble();
                if (accessoire.getIdAccessoire() == nomAccessoire && accessoire.getType().equals("fenetre")) {
                    mur.modifierPositionYAccessoire(positionImperialY, nomAccessoire);
                    mur.setEmplacementsCorrects(accessoire);
                    for(Accessoire autreAccessoire : mur.getAccessoires()) {
                        if(autreAccessoire.getIdAccessoire() != accessoire.getIdAccessoire()) {
                            mur.setEmplacementsCorrects(autreAccessoire);
                        }
                    }

                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (accessoire.getIdAccessoire() == nomAccessoire) {
                        accessoireDansMur = (accessoire.getPositionY().convertirEnDouble() + ChaletController.positionInitialeY + hautAccessoire <= mur.getPositionY().convertirEnDouble() + hautMur);
                    }
                }
            }
        }
    }

    public void supprimerAccessoire(int idAccessoire) {
        for (Mur mur : this.getMurs()) {
            mur.supprimerAccessoire(idAccessoire);
        }
    }

    public void calculePositionAccessoires() {
        for (Mur mur : this.getMurs()) {
            //if (mur.getNom().equals("back")) {
            mur.emptyVerticesAccessoire();
            for (Accessoire accessoire : mur.getAccessoires()) {
                double murLongueur1 = mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble();
                double murHauteur1 = mur.getPositionY().convertirEnDouble() + mur.getHauteur().convertirEnDouble();
                double murEpais = mur.getPositionZ().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble();
                double accessoireLongueur = accessoire.getLongueur().convertirEnDouble();
                double accessoireHauteur = accessoire.getHauteur().convertirEnDouble();
                double accessoireEpaisseur = mur.getEpaisseur().convertirEnDouble();
                double murLongueur = mur.getLongueur().convertirEnDouble();
                double murHauteur = mur.getHauteur().convertirEnDouble();
                if ( mur.getNom().equals("front")) {
                    //position 1
                    Vector position = new Vector<Double>();
                    position.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position.add(mur.getPositionZ());
                    mur.ajouterVerticesAccessoire(position);

                    Vector nouveauSommet = new Vector<>(position);
                    nouveauSommet.set(0, mur.getPositionX());
                    mur.ajouterPositionMur(nouveauSommet);
                    Vector nouveauSommet2 = new Vector<>(position);
                    nouveauSommet2.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet2);
//------------------------------------------------------------------
                    //position 2
                    Vector position1 = new Vector<Float>();
                    position1.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble());
                    position1.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position1.add(mur.getPositionZ());
                    mur.ajouterVerticesAccessoire(position1);

                    Vector nouveauSommet3 = new Vector<Float>(position1);
                    nouveauSommet3.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet3);
                    Vector nouveauSommet4 = new Vector<>(position1);
                    nouveauSommet4.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet4);
//------------------------------------------------------------------
                    //position 3
                    Vector position2 = new Vector<Float>();
                    position2.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble());
                    position2.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position2.add(mur.getPositionZ());
                    mur.ajouterVerticesAccessoire(position2);

                    Vector nouveauSommet5 = new Vector<>(position2);
                    nouveauSommet5.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet5);
                    Vector nouveauSommet6 = new Vector<>(position2);
                    nouveauSommet6.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet6);

//------------------------------------------------------------------
                    //position 4
                    Vector position3 = new Vector<Float>();
                    position3.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position3.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + +mur.getPositionY().convertirEnDouble()); // Revisiter y ajout ou soustraction
                    position3.add(mur.getPositionZ());
                    mur.ajouterVerticesAccessoire(position3);

                    Vector nouveauSommet7 = new Vector<>(position2);
                    nouveauSommet7.set(0, mur.getPositionX());
                    mur.ajouterPositionMur(nouveauSommet7);
                    Vector nouveauSommet8 = new Vector<>(position);
                    nouveauSommet8.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet8);
//------------------------------------------------------------------
                    //position 5
                    Vector position4 = new Vector<Float>();
                    position4.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position4.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position4.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position4);

                    Vector nouveauSommet9 = new Vector<>(nouveauSommet);
                    nouveauSommet9.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet9.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet9);
                    Vector nouveauSommet10 = new Vector<>(nouveauSommet2);
                    nouveauSommet10.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet10);
//------------------------------------------------------------------
                    //position 6 -- problématique
                    Vector position5 = new Vector<Float>();
                    position5.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble());
                    position5.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position5.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position5);

                    Vector nouveauSommet11 = new Vector<>(nouveauSommet3);
                    nouveauSommet11.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet11.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet11);
                    Vector nouveauSommet12 = new Vector<>(nouveauSommet4);
                    nouveauSommet12.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet12);

//------------------------------------------------------------------
                    //position 7
                    Vector position6 = new Vector<Float>();
                    position6.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble());
                    position6.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position6.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position6);

                    Vector nouveauSommet13 = new Vector<>(nouveauSommet5);
                    nouveauSommet13.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet13.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet13);
                    Vector nouveauSommet14 = new Vector<>(nouveauSommet6);
                    nouveauSommet14.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet14);

                    //position 8 -- problématique
                    Vector position7 = new Vector<Float>();
                    position7.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position7.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble()); // Revisiter y ajout ou soustraction
                    position7.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position7);

                    Vector nouveauSommet15 = new Vector<>(nouveauSommet7);
                    nouveauSommet15.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet15.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet15);
                    Vector nouveauSommet16 = new Vector<>(nouveauSommet8);
                    nouveauSommet16.set(2, murEpais);
                    mur.ajouterPositionMur(nouveauSommet16);

                    mur.ajouterPositionMur(position);
                    mur.ajouterPositionMur(position1);
                    mur.ajouterPositionMur(position2);
                    mur.ajouterPositionMur(position3);
                    mur.ajouterPositionMur(position4);
                    mur.ajouterPositionMur(position5);
                    mur.ajouterPositionMur(position6);
                    mur.ajouterPositionMur(position7);
                }else if(mur.getNom().equals("back")) {
                    //position 1
                    Vector position = new Vector<Double>();
                    position.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position.add(mur.getPositionZ().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble()/2);
                    mur.ajouterVerticesAccessoire(position);

                    Vector nouveauSommet = new Vector<>(position);
                    nouveauSommet.set(0, mur.getPositionX());
                    mur.ajouterPositionMur(nouveauSommet);
                    Vector nouveauSommet2 = new Vector<>(position);
                    nouveauSommet2.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet2);
//------------------------------------------------------------------
                    //position 2
                    Vector position1 = new Vector<Float>();
                    position1.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble());
                    position1.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position1.add(mur.getPositionZ().convertirEnDouble()- mur.getEpaisseur().convertirEnDouble()/2);
                    mur.ajouterVerticesAccessoire(position1);

                    Vector nouveauSommet3 = new Vector<Float>(position1);
                    nouveauSommet3.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet3);
                    Vector nouveauSommet4 = new Vector<>(position1);
                    nouveauSommet4.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet4);
//------------------------------------------------------------------
                    //position 3
                    Vector position2 = new Vector<Float>();
                    position2.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble());
                    position2.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position2.add(mur.getPositionZ().convertirEnDouble()- mur.getEpaisseur().convertirEnDouble()/2);
                    mur.ajouterVerticesAccessoire(position2);

                    Vector nouveauSommet5 = new Vector<>(position2);
                    nouveauSommet5.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet5);
                    Vector nouveauSommet6 = new Vector<>(position2);
                    nouveauSommet6.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet6);

//------------------------------------------------------------------
                    //position 4
                    Vector position3 = new Vector<Float>();
                    position3.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position3.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble()); // Revisiter y ajout ou soustraction
                    position3.add(mur.getPositionZ().convertirEnDouble()- mur.getEpaisseur().convertirEnDouble()/2);
                    mur.ajouterVerticesAccessoire(position3);

                    Vector nouveauSommet7 = new Vector<>(position2);
                    nouveauSommet7.set(0, mur.getPositionX());
                    mur.ajouterPositionMur(nouveauSommet7);
                    Vector nouveauSommet8 = new Vector<>(position);
                    nouveauSommet8.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet8);
//------------------------------------------------------------------
                    //position 5
                    Vector position4 = new Vector<Float>();
                    position4.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position4.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position4.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble()- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position4);

                    Vector nouveauSommet9 = new Vector<>(nouveauSommet);
                    nouveauSommet9.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet9.set(2, murEpais- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet9);
                    Vector nouveauSommet10 = new Vector<>(nouveauSommet2);
                    nouveauSommet10.set(2, murEpais- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet10);
//------------------------------------------------------------------
                    //position 6 -- problématique
                    Vector position5 = new Vector<Float>();
                    position5.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble());
                    position5.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position5.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position5);

                    Vector nouveauSommet11 = new Vector<>(nouveauSommet3);
                    nouveauSommet11.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet11.set(2, murEpais- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet11);
                    Vector nouveauSommet12 = new Vector<>(nouveauSommet4);
                    nouveauSommet12.set(2, murEpais- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet12);

//------------------------------------------------------------------
                    //position 7
                    Vector position6 = new Vector<Float>();
                    position6.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble());
                    position6.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position6.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble()- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position6);

                    Vector nouveauSommet13 = new Vector<>(nouveauSommet5);
                    nouveauSommet13.set(0, murLongueur1 - mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet13.set(2, murEpais - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet13);
                    Vector nouveauSommet14 = new Vector<>(nouveauSommet6);
                    nouveauSommet14.set(2, murEpais- mur.getEpaisseur().convertirEnDouble() );
                    mur.ajouterPositionMur(nouveauSommet14);

                    //position 8 -- problématique
                    Vector position7 = new Vector<Float>();
                    position7.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    position7.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble()); // Revisiter y ajout ou soustraction
                    position7.add(accessoireEpaisseur + mur.getPositionZ().convertirEnDouble()- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position7);

                    Vector nouveauSommet15 = new Vector<>(nouveauSommet7);
                    nouveauSommet15.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2);
                    nouveauSommet15.set(2, murEpais- mur.getEpaisseur().convertirEnDouble() );
                    mur.ajouterPositionMur(nouveauSommet15);
                    Vector nouveauSommet16 = new Vector<>(nouveauSommet8);
                    nouveauSommet16.set(2, murEpais- mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet16);

                    mur.ajouterPositionMur(position);
                    mur.ajouterPositionMur(position1);
                    mur.ajouterPositionMur(position2);
                    mur.ajouterPositionMur(position3);
                    mur.ajouterPositionMur(position4);
                    mur.ajouterPositionMur(position5);
                    mur.ajouterPositionMur(position6);
                    mur.ajouterPositionMur(position7);

                }else if (mur.getNom().equals("left")) {
                    //position 1
                    Vector position = new Vector<Double>();
                    position.add(mur.getPositionX());
                    position.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position);

                    Vector nouveauSommet = new Vector<>(position);
                    nouveauSommet.set(2, mur.getPositionX());
                    mur.ajouterPositionMur(nouveauSommet);
                    Vector nouveauSommet2 = new Vector<>(position);
                    nouveauSommet2.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet2);
//------------------------------------------------------------------
                    //position 2
                    Vector position1 = new Vector<Float>();
                    position1.add(mur.getPositionX());
                    position1.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position1.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position1);

                    Vector nouveauSommet3 = new Vector<Float>(position1);
                    nouveauSommet3.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet3);
                    Vector nouveauSommet4 = new Vector<>(position1);
                    nouveauSommet4.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet4);
//------------------------------------------------------------------
                    //position 3
                    Vector position2 = new Vector<Float>();
                    position2.add(mur.getPositionX());
                    position2.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position2.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position2);

                    Vector nouveauSommet5 = new Vector<>(position2);
                    nouveauSommet5.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble());
                    //nouveauSommet5.set(0, murLongueur1);
                    mur.ajouterPositionMur(nouveauSommet5);
                    Vector nouveauSommet6 = new Vector<>(position1);
                    nouveauSommet6.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet6);

//------------------------------------------------------------------
                    //position 4
                    Vector position3 = new Vector<Float>();
                    position3.add(mur.getPositionX());
                    position3.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble()); // Revisiter y ajout ou soustraction
                    position3.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position3);

                    Vector nouveauSommet7 = new Vector<>(position2);
                    nouveauSommet7.set(2, mur.getPositionX());
                    mur.ajouterPositionMur(nouveauSommet7);
                    Vector nouveauSommet8 = new Vector<>(position);
                    nouveauSommet8.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet8);
//------------------------------------------------------------------
                    //position 5
                    Vector position4 = new Vector<Float>();
                    position4.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble());
                    position4.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position4.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());

                    mur.ajouterVerticesAccessoire(position4);

                    Vector nouveauSommet9 = new Vector<>(nouveauSommet);
                    nouveauSommet9.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet9.set(2, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2);
                    mur.ajouterPositionMur(nouveauSommet9);
                    Vector nouveauSommet10 = new Vector<>(nouveauSommet2);
                    nouveauSommet10.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet10);
//------------------------------------------------------------------
                    //position 6 -- problématique
                    Vector position5 = new Vector<Float>();
                    position5.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble());
                    position5.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position5.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position5);

                    Vector nouveauSommet11 = new Vector<>(nouveauSommet3);
                    nouveauSommet11.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet11.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble() / 2);
                    mur.ajouterPositionMur(nouveauSommet11);
                    Vector nouveauSommet12 = new Vector<>(nouveauSommet4);
                    nouveauSommet12.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet12);

//------------------------------------------------------------------
                    //position 7
                    Vector position6 = new Vector<Float>();
                    position6.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble());
                    position6.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position6.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position6);

                    Vector nouveauSommet13 = new Vector<>(nouveauSommet5);
                    nouveauSommet13.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet13.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble() / 2);
                    mur.ajouterPositionMur(nouveauSommet13);
                    Vector nouveauSommet14 = new Vector<>(nouveauSommet6);
                    nouveauSommet14.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet14);
//------------------------------------------------------------------
                    //position 8 -- problématique
                    Vector position7 = new Vector<Float>();
                    position7.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble());
                    position7.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position7.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position7);

                    Vector nouveauSommet15 = new Vector<>(nouveauSommet7);
                    nouveauSommet15.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet15.set(2, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2);
                    mur.ajouterPositionMur(nouveauSommet15);
                    Vector nouveauSommet16 = new Vector<>(nouveauSommet8);
                    nouveauSommet16.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet16);

                    mur.ajouterPositionMur(position);
                    mur.ajouterPositionMur(position1);
                    mur.ajouterPositionMur(position2);
                    mur.ajouterPositionMur(position3);
                    mur.ajouterPositionMur(position4);
                    mur.ajouterPositionMur(position5);
                    mur.ajouterPositionMur(position6);
                    mur.ajouterPositionMur(position7);
                } else if (mur.getNom().equals("right")) {
                    //position 1
                    Vector position = new Vector<Double>();
                    position.add(mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position);

                    Vector nouveauSommet = new Vector<>(position);
                    nouveauSommet.set(2, mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet);
                    Vector nouveauSommet2 = new Vector<>(position);
                    nouveauSommet2.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet2);
//------------------------------------------------------------------
                    //position 2
                    Vector position1 = new Vector<Float>();
                    position1.add(mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position1.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position1.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position1);

                    Vector nouveauSommet3 = new Vector<Float>(position1);
                    nouveauSommet3.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet3);
                    Vector nouveauSommet4 = new Vector<>(position1);
                    nouveauSommet4.set(1, mur.getPositionY());
                    mur.ajouterPositionMur(nouveauSommet4);
//------------------------------------------------------------------
                    //position 3
                    Vector position2 = new Vector<Float>();
                    position2.add(mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position2.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position2.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position2);

                    Vector nouveauSommet5 = new Vector<>(position2);
                    nouveauSommet5.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    //nouveauSommet5.set(0, murLongueur1);
                    mur.ajouterPositionMur(nouveauSommet5);
                    Vector nouveauSommet6 = new Vector<>(position1);
                    nouveauSommet6.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet6);

//------------------------------------------------------------------
                    //position 4
                    Vector position3 = new Vector<Float>();
                    position3.add(mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position3.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble()); // Revisiter y ajout ou soustraction
                    position3.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position3);

                    Vector nouveauSommet7 = new Vector<>(position2);
                    nouveauSommet7.set(2, mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet7);
                    Vector nouveauSommet8 = new Vector<>(position);
                    nouveauSommet8.set(1, murHauteur1);
                    mur.ajouterPositionMur(nouveauSommet8);
//------------------------------------------------------------------
                    //position 5
                    Vector position4 = new Vector<Float>();
                    position4.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position4.add(accessoire.getPositionY().convertirEnDouble() + mur.getPositionY().convertirEnDouble());
                    position4.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());

                    mur.ajouterVerticesAccessoire(position4);

                    Vector nouveauSommet9 = new Vector<>(nouveauSommet);
                    nouveauSommet9.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet9.set(2, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2 - mur.getLongueur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet9);
                    Vector nouveauSommet10 = new Vector<>(nouveauSommet2);
                    nouveauSommet10.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet10);
//------------------------------------------------------------------
                    //position 6 -- problématique
                    Vector position5 = new Vector<Float>();
                    position5.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position5.add(accessoire.getPositionY() .convertirEnDouble()+ mur.getPositionY().convertirEnDouble());
                    position5.add((accessoire.getPositionX().convertirEnDouble() + accessoireLongueur) + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position5);

                    Vector nouveauSommet11 = new Vector<>(nouveauSommet3);
                    nouveauSommet11.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet11.set(2, mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble() / 2);
                    mur.ajouterPositionMur(nouveauSommet11);
                    Vector nouveauSommet12 = new Vector<>(nouveauSommet4);
                    nouveauSommet12.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet12);

//------------------------------------------------------------------
                    //position 7
                    Vector position6 = new Vector<Float>();
                    position6.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position6.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position6.add(accessoire.getPositionX().convertirEnDouble() + accessoireLongueur + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position6);

                    Vector nouveauSommet13 = new Vector<>(nouveauSommet5);
                    nouveauSommet13.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet13.set(2, mur.getPositionX().convertirEnDouble() + mur.getLongueur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble() / 2);
                    mur.ajouterPositionMur(nouveauSommet13);
                    Vector nouveauSommet14 = new Vector<>(nouveauSommet6);
                    nouveauSommet14.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet14);
//------------------------------------------------------------------
                    //position 8 -- problématique
                    Vector position7 = new Vector<Float>();
                    position7.add(accessoireEpaisseur + mur.getPositionX().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    position7.add(accessoire.getPositionY().convertirEnDouble() + accessoireHauteur + mur.getPositionY().convertirEnDouble());
                    position7.add(accessoire.getPositionX().convertirEnDouble() + mur.getPositionX().convertirEnDouble() - mur.getLongueur().convertirEnDouble());
                    mur.ajouterVerticesAccessoire(position7);

                    Vector nouveauSommet15 = new Vector<>(nouveauSommet7);
                    nouveauSommet15.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    nouveauSommet15.set(2, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() / 2 - mur.getLongueur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet15);
                    Vector nouveauSommet16 = new Vector<>(nouveauSommet8);
                    nouveauSommet16.set(0, mur.getPositionX().convertirEnDouble() + mur.getEpaisseur().convertirEnDouble() - mur.getEpaisseur().convertirEnDouble());
                    mur.ajouterPositionMur(nouveauSommet16);

                    mur.ajouterPositionMur(position);
                    mur.ajouterPositionMur(position1);
                    mur.ajouterPositionMur(position2);
                    mur.ajouterPositionMur(position3);
                    mur.ajouterPositionMur(position4);
                    mur.ajouterPositionMur(position5);
                    mur.ajouterPositionMur(position6);
                    mur.ajouterPositionMur(position7);
                }
            }
        }
    }

    public void modifierPositionXIntAccessoire(int newPositionX, int nomAccessoire) {
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;
        Imperial positionXImperial = new Imperial(0,((newPositionX- ChaletController.positionInitialeX)/4)/ChaletController.multiplicateur);

        for (Mur mur : this.getMurs()) {

            double longMur = mur.getLongueur().convertirEnDouble();

            for (Accessoire accessoire : mur.getAccessoires()) {
                double longAccessoire = accessoire.getLongueur().convertirEnDouble();

                if (accessoire.getIdAccessoire() == nomAccessoire) {
                    mur.modifierPositionXAccessoire(positionXImperial, nomAccessoire);
                    mur.setEmplacementsCorrects(accessoire);

                    for(Accessoire autreAccessoire : mur.getAccessoires()) {
                        if(autreAccessoire.getIdAccessoire() != accessoire.getIdAccessoire()) {
                            mur.setEmplacementsCorrects(autreAccessoire);
                        }
                    }

                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (accessoire.getIdAccessoire() == nomAccessoire) {
                        accessoireDansMur = (accessoire.getPositionX().convertirEnDouble() + ChaletController.positionInitialeX + longAccessoire <= mur.getPositionX().convertirEnDouble() + longMur);
                    }
                }
            }
        }
    }
    public void modifierPositionYIntAccessoire(int newPositionY, int nomAccessoire) {
        Imperial positionImperialY = new Imperial(0, ((newPositionY- ChaletController.positionInitialeY)/4)/ChaletController.multiplicateur);
        boolean accessoireDansMur = true;
        boolean emplacementCorrect = true;
        for (Mur mur : this.getMurs()) {
            double hautMur = mur.getHauteur().convertirEnDouble();
            for (Accessoire accessoire : mur.getAccessoires()) {
                double hautAccessoire = accessoire.getHauteur().convertirEnDouble();
                if (accessoire.getIdAccessoire() == nomAccessoire && accessoire.getType().equals("fenetre")) {
                    mur.modifierPositionYAccessoire(positionImperialY, nomAccessoire);
                    mur.setEmplacementsCorrects(accessoire);
                    for(Accessoire autreAccessoire : mur.getAccessoires()) {
                        if(autreAccessoire.getIdAccessoire() != accessoire.getIdAccessoire()) {
                            mur.setEmplacementsCorrects(autreAccessoire);
                        }
                    }

                    if (emplacementCorrect) {
                        emplacementCorrect = mur.getEmplacementCorrect(accessoire);
                    } else if (accessoire.getIdAccessoire() == nomAccessoire) {
                        accessoireDansMur = (accessoire.getPositionY().convertirEnDouble() + ChaletController.positionInitialeY + hautAccessoire <= mur.getPositionY().convertirEnDouble() + hautMur);
                    }
                }
            }
        }
    }

    public void modifierDistanceRetrait(Imperial nouvelleDistanceRetrait) {
        for (Mur mur : this.murs) {
            mur.setDistanceRetrait(nouvelleDistanceRetrait);
            supprimerRainures();
            calculerRainure();
        }
    }

    @Override
    public String toString() {
        return "Chalet [largeur=" + largeur + ", hauteur=" + hauteur + "]";
    }
}