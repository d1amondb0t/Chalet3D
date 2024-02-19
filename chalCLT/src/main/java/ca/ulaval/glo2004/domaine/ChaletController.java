package ca.ulaval.glo2004.domaine;


import ca.ulaval.glo2004.domaine.DTO.*;
import ca.ulaval.glo2004.domaine.drawing.AfficherDessus;
import ca.ulaval.glo2004.domaine.drawing.AfficheurMur;
import ca.ulaval.glo2004.domaine.export.ExportBrut;
import ca.ulaval.glo2004.domaine.export.ExportFini;
import ca.ulaval.glo2004.domaine.export.ExportRetrait;
import ca.ulaval.glo2004.domaine.export.ExportSTL;
import ca.ulaval.glo2004.domaine.memento.Memento;
import ca.ulaval.glo2004.domaine.memento.SavedChalet;
import ca.ulaval.glo2004.domaine.utils.Imperial;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo2004.domaine.memento.Memento.deepCopy;

public class ChaletController {

    private Chalet chalet;

    private SavedChalet chaletSauvegarde ;

    private Memento memento = new Memento();

    private ExportSTL exportSTLBrut;

    private ExportSTL exportSTLFini;

    private ExportSTL exportSTLRetrait;

    public ChaletController() {
        this.chalet = new Chalet();
        this.exportSTLBrut = new ExportBrut(chalet);
        this.exportSTLFini = new ExportFini(chalet);
        this.exportSTLRetrait = new ExportRetrait(chalet);
    }

    public static float multiplicateur = 1.0f;
    public static float offSetX = 1143;
    public static float offSetY = 729;
    public static float screenX = 1631;
    public static float screenY = 1259;
    public static float positionInitialeX = (screenX - offSetX) * multiplicateur;
    public static float positionInitialeY = (screenY - offSetY) * multiplicateur;

    public static float positionInitialeZ = (screenY - offSetY) * multiplicateur;
    public static boolean isSideEnabled = true;

    public float screenToWorldX(float screenX) {
        return screenX / multiplicateur + positionInitialeX ;
    }
    public float screenToWorldY(float screenY) {
        return screenY / multiplicateur + positionInitialeY ;
    }

    public double getHauteurPanneauSuperieur() {
        if(this.chalet != null)
            return this.chalet.getHauteurToit();
        return 0;
    }

    //domaine_toit.DTO à retourner
    public List<ToitDTO> getToit() {
        ArrayList<ToitDTO> toit_dto = new ArrayList<>();
        for (Toit toit: chalet.getToit()){
            toit_dto.add(new ToitDTO(toit));
        }
        return toit_dto;
    }
    public List<MurDTO> getMurs() {
        ArrayList<MurDTO> mur_dtos = new ArrayList<>();
        for (Mur mur : chalet.getMurs()) {
            mur_dtos.add(new MurDTO(mur));
        }
        return mur_dtos;
    }
    public List<AccessoireDTO> getAccessoires() {
        ArrayList<AccessoireDTO> accessoireDTOS = new ArrayList<>();
        for (Mur mur : this.chalet.getMurs()) {
            for (Accessoire accessoire : mur.getAccessoires()) {
                accessoireDTOS.add(new AccessoireDTO(accessoire));
            }
        }
        return accessoireDTOS;
    }

    public void modifierSensToit(String sensDuToit){
        this.chalet.modifierSensToit(sensDuToit);
    }


    public void modifierAngle(float newAngle) {
    this.chalet.modifierAngle(newAngle);
    }


    public void modifierHauteur(String hauteur) {
        this.chalet.modifierHauteur(hauteur);
    }

    public void modifierEpaisseur(String epaisseur) {
        Imperial imperialEpaisseur = new Imperial(epaisseur);
        this.chalet.modifierEpaisseur(imperialEpaisseur);
    }

    public void modifierLongueurDesMursParalleles(String longueur, boolean murs) {
        this.chalet.modifierLongueurMursParalleles(longueur, murs);
    }

    public void ajouterAccessoire(String nomMur, String type, String hauteur, String longueur, String positionX, String positionY, String positionZ){
        this.chalet.ajouterAccessoire(nomMur, type, hauteur, longueur, positionX, positionY, positionZ);
    }


    public AccessoireDTO getPositionClicked(String nomMur, Point pointClicked) {
        Accessoire accessoire = chalet.getPositionClicked(nomMur, pointClicked);
        if(accessoire != null){
            AccessoireDTO accessoireDTO = new AccessoireDTO(accessoire);
            return accessoireDTO;
        }
        return null;
    }

    public void modifierLongueurAccessoire(String newLongueur, int idAccessoire) {
        this.chalet.modifierLongueurAccessoire(newLongueur, idAccessoire);
    }

    public void modifierHauteurAccessoire(String newHauteur, int idAccessoire) {
        this.chalet.modifierHauteurAccessoire(newHauteur, idAccessoire);
    }

    public void modifierPositionXAccessoire(String newPositionX, int nomAccessoire) {
        this.chalet.modifierPositionXAccessoire(newPositionX, nomAccessoire);
    }

    public void modifierPositionYAccessoire(String newPositionY, int nomAccessoire) {
        this.chalet.modifierPositionYAccessoire(newPositionY, nomAccessoire);
    }

    public void supprimerAccessoire(int idAccessoire) {
        this.chalet.supprimerAccessoire(idAccessoire);
    }

    public float getMultiplicateur(){
        return this.multiplicateur;
    }

    public void setMultiplicateur(float multiplicateur){
       this.multiplicateur = multiplicateur;
    }

    public void setSideEnable(boolean etat) {
        isSideEnabled = etat;
    }

    public AfficheurMur creerDrawerAfficheurMur(ChaletController controller) {
        AfficheurMur drawer = new AfficheurMur(controller);
        return drawer;
    }


    public AfficherDessus creerDrawerAfficherDessus(ChaletController controller) {
        AfficherDessus drawer = new AfficherDessus(controller);
        return drawer;
    }


    public double convertirImperialEnPixelDTO(ImperialDTO nombreImperial) {
        return this.chalet.convertirImperialEnPixelDTO(nombreImperial);
    }

    public void creerNouveauChalet() {
        this.chalet = new Chalet();
        memento.clearStack();
    }


    public void modifierDistMinimaleAccessoire(String valeurDistMin) {
        this.chalet.modifierDistMinimaleAccessoire(valeurDistMin);
    }

    public void modifierPositionXIntAccessoire(int newPositionX, int nomAccessoire) {
        this.chalet.modifierPositionXIntAccessoire(newPositionX, nomAccessoire);
    }
    public void modifierPositionYIntAccessoire(int newPositionY, int nomAccessoire) {

        this.chalet.modifierPositionYIntAccessoire(newPositionY, nomAccessoire);
    }

    public void setOffset(float offset_x, float offset_y){
        offSetX += offset_x;
        offSetY += offset_y;
        positionInitialeX = (screenX - offSetX) * multiplicateur;
        positionInitialeY = (screenY - offSetY) * multiplicateur;
        positionInitialeZ = (screenY - offSetY) * multiplicateur;
    }

    public void exporterMurBrut(String nomMur){
        this.exportSTLBrut.exporterMur(nomMur);
    }

    public void exporterMursBrut(){this.exportSTLBrut.exporterMurs();};

    public void exporterMurFini(String nomMur){this.exportSTLFini.exporterMur(nomMur);}

    public void exporterMursFini(){this.exportSTLFini.exporterMurs();};

    public void exporterMurRetrait(String nomMur){this.exportSTLRetrait.exporterMur(nomMur);}

    public void exporterMursRetrait(){this.exportSTLRetrait.exporterMurs();};

    public void calculerRainure() {
        this.chalet.calculerRainure();
    }

    public void supprimerRainures(){
        for (Mur mur : this.chalet.getMurs()) {
            mur.getRainures().clear();
        }
    }

    public void modifierDistanceRetrait(String nouvelleDistanceRetrait){
        Imperial imperial = new Imperial(nouvelleDistanceRetrait);
        this.chalet.modifierDistanceRetrait(imperial);
    }

    public SensToit getSens() {
        return this.chalet.getSens();
    }


    public void saveState() {
        memento.pushUndo(deepCopy(chalet));
    }

    public void undo() {
        Object previousState = memento.popUndo();
        if (previousState != null) {
            chalet = (Chalet) deepCopy(previousState);
            memento.pushRedo(deepCopy(chalet));
        } else {
            System.out.println("Undo stack est vide ! ");
        }
    }

    public void redo() {
        Object nextState = memento.popRedo();
        if (nextState != null) {
            chalet = (Chalet) deepCopy(nextState);
            memento.pushUndo(deepCopy(chalet));
        }
    }

    public void openChalet(File fichier) throws IOException, ClassNotFoundException {
        chaletSauvegarde = new SavedChalet(new Chalet());
        if(fichier.exists()){
            chalet = new Chalet((SavedChalet) chaletSauvegarde.readFile(fichier));
            memento = new Memento();
        };
    }

    public void saveChalet(String repertoire, String nomFichier) throws IOException {
        saveState();
        Object chaletSauvegarder = memento.popUndo();
        chaletSauvegarde = new SavedChalet((Chalet)deepCopy(chaletSauvegarder));
        chaletSauvegarde.createFile(repertoire, nomFichier,  chaletSauvegarde );
    }

}