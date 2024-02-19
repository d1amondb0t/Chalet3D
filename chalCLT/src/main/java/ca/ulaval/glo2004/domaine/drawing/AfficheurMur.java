package ca.ulaval.glo2004.domaine.drawing;



import ca.ulaval.glo2004.domaine.*;
import ca.ulaval.glo2004.domaine.DTO.AccessoireDTO;
import ca.ulaval.glo2004.domaine.DTO.ImperialDTO;
import ca.ulaval.glo2004.domaine.DTO.MurDTO;
import ca.ulaval.glo2004.domaine.DTO.ToitDTO;

import java.awt.*;
import java.util.ArrayList;

public class AfficheurMur extends Drawer {

    private ChaletController controleur;
    private float multiplicateur;


    public AfficheurMur(ChaletController controleur) {
        this.controleur = controleur;
        this.multiplicateur = this.controleur.getMultiplicateur();
    }

    @Override
    void drawChalet(Graphics g) {
    }

    @Override
    public void drawFront(Graphics g) {
        for (ToitDTO toitD : this.controleur.getToit()) {
            g.setColor(new Color(0, 160, 0));
            for (MurDTO mur : this.controleur.getMurs()) {
                double longMur = this.controleur.convertirImperialEnPixelDTO(mur.getLongueur()) * multiplicateur;
                double hauteurMur = this.controleur.convertirImperialEnPixelDTO(mur.getHauteur()) * multiplicateur;
                double epaisseurMur = this.controleur.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * multiplicateur;
                double hauteurPignon = toitD.getHauteur().convertirEnDoubleDTO() * multiplicateur;
                double hauteurRallongeVerticale = toitD.getHauteurRallonge().convertirEnDoubleDTO() * multiplicateur;

                //-pignon - epaisToit
                int ajustementRetrait = (int) (controleur.convertirImperialEnPixelDTO(mur.getDistanceRetrait()) / 2 * ChaletController.multiplicateur);
                if (mur.getNom().equals("front")) {
                    g.drawRect((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) longMur, (int) hauteurMur);
                    g.setColor(Color.BLACK);
                    if (toitD.getNom().equals("pignonDroite")) {
                        if (this.controleur.getSens() == SensToit.DROITE_GAUCHE || this.controleur.getSens() == SensToit.GAUCHE_DROITE) {
                            //MUR VOISINS
                            if (ChaletController.isSideEnabled) {
                                g.drawRect((int) (ChaletController.positionInitialeX - epaisseurMur - ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                                g.drawRect((int) (ChaletController.positionInitialeX + longMur + ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                            }
                            if (this.controleur.getSens() == SensToit.DROITE_GAUCHE) {

                                if (ChaletController.isSideEnabled) {
                                    //Panneau Supérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale), (int) (ChaletController.positionInitialeX - epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur));
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur);
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur);
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale),(int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX + longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY));

                                    //Rallonge Verticale
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeX - (int) epaisseurMur, (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale));
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY - hauteurPignon));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY - hauteurPignon), (int) ChaletController.positionInitialeX - (int) epaisseurMur, (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX - epaisseurMur), (int) (ChaletController.positionInitialeY));
                                }
                                //Pignons
                                //Ligne Verticale gauche
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY, (int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon);
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon,(int) (ChaletController.positionInitialeX) + (int) longMur, (int) ChaletController.positionInitialeY);
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY));
                            } else {
                                if (ChaletController.isSideEnabled) {
                                    //PanneauSupérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur, (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeX - (int) epaisseurMur, (int) ChaletController.positionInitialeY - (int) epaisseurMur);
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur, (int) ChaletController.positionInitialeY - (int) epaisseurMur, (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX), (int) (ChaletController.positionInitialeY),(int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX - epaisseurMur), (int) (ChaletController.positionInitialeY));

                                    //Rallonge Vertical
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale));
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX + (int) longMur , (int) ChaletController.positionInitialeY, (int) ChaletController.positionInitialeX + (int) longMur , (int) (ChaletController.positionInitialeY - hauteurPignon));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY - hauteurPignon), (int) ChaletController.positionInitialeX + (int) (longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX + (int) longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY));

                                }
                                //Pignon
                                //Ligne vertical droite
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + longMur), (int) ChaletController.positionInitialeY - (int) hauteurPignon);
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY - hauteurPignon), (int) ChaletController.positionInitialeX, (int) ( ChaletController.positionInitialeY));
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY));
                            }
                        }

                    } else if (this.controleur.getSens() == SensToit.ARRIERE_FACADE) {
                        for (ToitDTO toit : this.controleur.getToit()) {
                            g.drawRect((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon, (int) longMur, (int) hauteurPignon);
                            if(ChaletController.isSideEnabled) {
                                g.drawRect((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY - (int) hauteurPignon - (int) epaisseurMur), (int) longMur, (int) epaisseurMur);
                            }}

                    }

                    for (AccessoireDTO accessoire : mur.getAccessoiresDTO()) {
                        double longAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getLongueur()) * multiplicateur;
                        double hautAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getHauteur()) * multiplicateur;
                        g.drawRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                (int) longAccessoire,
                                (int) hautAccessoire);
                        if (accessoire.getType().equals("Porte")) {
                            if (mur.getEmplacementCorrectDTO(accessoire)) {
                                g.setColor(Color.BLACK);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                        } else {
                            if (mur.getEmplacementCorrectDTO(accessoire)) {
                                g.setColor(Color.gray);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                        }
                    }

                }
            }
        }
    }


    @Override
    public void drawRight(Graphics g) {
        for (MurDTO mur : this.controleur.getMurs()) {
            for (ToitDTO toitD : this.controleur.getToit()) {
                g.setColor(new Color(0, 160, 0));

                double longMur = this.controleur.convertirImperialEnPixelDTO(mur.getLongueur()) * multiplicateur;
                double hauteurMur = this.controleur.convertirImperialEnPixelDTO(mur.getHauteur()) * multiplicateur;
                double epaisseurMur = this.controleur.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * multiplicateur;
                int ajustementRetrait = (int) (controleur.convertirImperialEnPixelDTO(mur.getDistanceRetrait()) / 2 * ChaletController.multiplicateur);
                double hauteurPignon = toitD.getHauteur().convertirEnDoubleDTO() * multiplicateur;
                double hauteurRallongeVerticale = toitD.getHauteurRallonge().convertirEnDoubleDTO() * multiplicateur;

                if (mur.getNom().equals("right")) {
                    g.drawRect((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) longMur, (int) hauteurMur);
                    g.setColor(Color.BLACK);
                    if (toitD.getNom().equals("pignonDroite")) {
                        if (this.controleur.getSens() == SensToit.ARRIERE_FACADE || this.controleur.getSens() == SensToit.FACADE_ARRIERE) {
                            if (ChaletController.isSideEnabled) {
                                g.drawRect((int) (ChaletController.positionInitialeX - epaisseurMur - ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                                g.drawRect((int) (ChaletController.positionInitialeX + longMur + ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                            }
                            if (this.controleur.getSens() == SensToit.FACADE_ARRIERE) {
                                if (ChaletController.isSideEnabled) {
                                    //PanneauSupérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY - (int) epaisseurMur , (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ),(int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                    //Rallonge Vertical
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX + (int) longMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX + (int) longMur , (int) (ChaletController.positionInitialeY - hauteurPignon ));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX + (int) (longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + (int) longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                }
                                //Ligne vertical droite
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX + longMur), (int) ChaletController.positionInitialeY - (int) hauteurPignon );
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX, (int) ( ChaletController.positionInitialeY));
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY ), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ));

                            } else {
                                if (ChaletController.isSideEnabled) {
                                    //Panneau Supérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY , (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ),(int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY ));

                                    //Rallonge Verticale
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX , (int) (ChaletController.positionInitialeY - hauteurPignon ));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX , (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                }
                                //Pignons
                                //Ligne Verticale gauche
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY , (int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon );
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon ,(int) (ChaletController.positionInitialeX) + (int) longMur, (int) ChaletController.positionInitialeY );
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY ), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ));

                                }
                        }
                    } else if (this.controleur.getSens() == SensToit.GAUCHE_DROITE) {
                        for (ToitDTO toit : this.controleur.getToit()) {
                            g.drawRect((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon, (int) longMur, (int) hauteurPignon);
                            if(ChaletController.isSideEnabled) {
                                g.drawRect((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY - (int) hauteurPignon - (int) epaisseurMur), (int) longMur, (int) epaisseurMur);
                            }
                        }
                    }
                    for (AccessoireDTO accessoire : mur.getAccessoiresDTO()) {
                        double longAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getLongueur()) * multiplicateur;
                        double hautAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getHauteur()) * multiplicateur;
                        g.drawRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                (int) longAccessoire,
                                (int) hautAccessoire);
                        if (accessoire.getType().equals("Porte")) {
                            if (mur.getEmplacementCorrectDTO(accessoire)) {
                                g.setColor(Color.BLACK);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                        } else {
                            if (mur.getEmplacementCorrectDTO(accessoire)) {
                                g.setColor(Color.gray);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void drawLeft(Graphics g) {
        for (MurDTO mur : this.controleur.getMurs()) {

            for (ToitDTO toitD : this.controleur.getToit()) {
                g.setColor(new Color(0, 160, 0));
                double longMur = this.controleur.convertirImperialEnPixelDTO(mur.getLongueur()) * multiplicateur;
                double hauteurMur = this.controleur.convertirImperialEnPixelDTO(mur.getHauteur()) * multiplicateur;
                double epaisseurMur = this.controleur.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * multiplicateur;
                int ajustementRetrait = (int) (controleur.convertirImperialEnPixelDTO(mur.getDistanceRetrait()) / 2 * ChaletController.multiplicateur);
                double hauteurPignon = toitD.getHauteur().convertirEnDoubleDTO() * multiplicateur;
                double hauteurRallongeVerticale = toitD.getHauteurRallonge().convertirEnDoubleDTO() * multiplicateur;


                if (mur.getNom().equals("left")) {
                    g.drawRect((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) longMur, (int) hauteurMur);
                    g.setColor(Color.BLACK);
                    if (toitD.getNom().equals("pignonDroite")) {
                        if (this.controleur.getSens() == SensToit.FACADE_ARRIERE || this.controleur.getSens() == SensToit.ARRIERE_FACADE) {
                            if (ChaletController.isSideEnabled) {
                                g.drawRect((int) (ChaletController.positionInitialeX - epaisseurMur - ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                                g.drawRect((int) (ChaletController.positionInitialeX + longMur + ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                            }
                            if (this.controleur.getSens() == SensToit.FACADE_ARRIERE) {

                                if (ChaletController.isSideEnabled) {
                                    //Panneau Supérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY , (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ),(int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY ));

                                    //Rallonge Verticale
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX , (int) (ChaletController.positionInitialeY - hauteurPignon ));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX , (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                }
                                //Pignons
                                //Ligne Verticale gauche
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY , (int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon );
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon ,(int) (ChaletController.positionInitialeX) + (int) longMur, (int) ChaletController.positionInitialeY );
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY ), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ));

                            } else { //ARRIERE-FACADE

                            if(ChaletController.isSideEnabled){
                                //PanneauSupérieur
                                //Ligne Verticale Gauche
                                g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                //Ligne Verticale Droite
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                //Ligne Diagonale Supérieur
                                g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY - (int) epaisseurMur , (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                //Ligne Diagonale inférieur
//                                g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ),(int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                //Ligne horizontale du bas
                                g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                //Rallonge Vertical
                                //ligne verticale droite
                                g.drawLine((int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                //ligne verticale gauche
                                g.drawLine((int) ChaletController.positionInitialeX + (int) longMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX + (int) longMur , (int) (ChaletController.positionInitialeY - hauteurPignon ));
                                //Ligne diagonale supérieur
                                g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX + (int) (longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                //Ligne horizontale du bas
                                g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + (int) longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                            }
                                //Pignon
//                                //Ligne vertical droite
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX + longMur), (int) ChaletController.positionInitialeY - (int) hauteurPignon );
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX, (int) ( ChaletController.positionInitialeY));
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY ), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ));

                            }
                        } else if (this.controleur.getSens() == SensToit.DROITE_GAUCHE) {
                            for (ToitDTO toit : this.controleur.getToit()) {
                                g.drawRect((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon, (int) longMur, (int) hauteurPignon);
                                if(ChaletController.isSideEnabled) {
                                    g.drawRect((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY - (int) hauteurPignon - (int) epaisseurMur), (int) longMur, (int) epaisseurMur);
                                }
                            }
                        }
                    }

                    for (AccessoireDTO accessoire : mur.getAccessoiresDTO()) {
                        double longAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getLongueur()) * multiplicateur;
                        double hautAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getHauteur()) * multiplicateur;
                        g.drawRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                (int) longAccessoire,
                                (int) hautAccessoire);
                        if (accessoire.getType().equals("Porte")) {
                            if (mur.getEmplacementCorrectDTO(accessoire)) {
                                g.setColor(Color.BLACK);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                        } else {
                            if (mur.getEmplacementCorrectDTO(accessoire)) {
                                g.setColor(Color.gray);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void drawBack(Graphics g) {
        for (MurDTO mur : this.controleur.getMurs()) {
            for (ToitDTO toitD : this.controleur.getToit()) {
                g.setColor(new Color(0, 160, 0));
                double longMur = this.controleur.convertirImperialEnPixelDTO(mur.getLongueur()) * multiplicateur;
                double hauteurMur = this.controleur.convertirImperialEnPixelDTO(mur.getHauteur()) * multiplicateur;
                double epaisseurMur = this.controleur.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * multiplicateur;
                int ajustementRetrait = (int) (controleur.convertirImperialEnPixelDTO(mur.getDistanceRetrait()) / 2 * ChaletController.multiplicateur);
                double hauteurPignon = toitD.getHauteur().convertirEnDoubleDTO() * multiplicateur;
                double hauteurRallongeVerticale = toitD.getHauteurRallonge().convertirEnDoubleDTO() * multiplicateur;



                if (mur.getNom().equals("back")) {
                    g.drawRect((int) ChaletController.positionInitialeX, (int) ChaletController.positionInitialeY, (int) longMur, (int) hauteurMur);
                    g.setColor(Color.BLACK);
                    if (toitD.getNom().equals("pignonDroite")) {
                        if (this.controleur.getSens() == SensToit.GAUCHE_DROITE || this.controleur.getSens() == SensToit.DROITE_GAUCHE) {
                            if (ChaletController.isSideEnabled) {
                                g.drawRect((int) (ChaletController.positionInitialeX - epaisseurMur - ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                                g.drawRect((int) (ChaletController.positionInitialeX + longMur + ajustementRetrait), (int) ChaletController.positionInitialeY, (int) epaisseurMur, (int) hauteurMur);
                            }
                            if (this.controleur.getSens().equals(SensToit.DROITE_GAUCHE)) {
                                if (ChaletController.isSideEnabled) {
                                    //PanneauSupérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY - (int) epaisseurMur , (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ),(int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                    //Rallonge Verticale
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX + (int) longMur + (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX + (int) longMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX + (int) longMur , (int) (ChaletController.positionInitialeY - hauteurPignon ));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX + (int) (longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + (int) longMur ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + (int) longMur + epaisseurMur ), (int) (ChaletController.positionInitialeY ));

                                }
                                //Pignon
                                //Ligne vertical droite
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY), (int) (ChaletController.positionInitialeX + longMur), (int) ChaletController.positionInitialeY - (int) hauteurPignon );
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX, (int) ( ChaletController.positionInitialeY));
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY ), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ));

                            } else {
                                if (ChaletController.isSideEnabled) {
                                    //Panneau Supérieur
                                    //Ligne Verticale Gauche
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ));
                                    //Ligne Verticale Droite
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY , (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Diagonale Supérieur
                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale - epaisseurMur  ), (int) (ChaletController.positionInitialeX + longMur + epaisseurMur ), (int) ChaletController.positionInitialeY - (int) epaisseurMur );
                                    //Ligne Diagonale inférieur
//                                    g.drawLine((int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ),(int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX + longMur ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX + longMur  + epaisseurMur), (int) (ChaletController.positionInitialeY ));

                                    //Rallonge Verticale
                                    //ligne verticale gauche
                                    g.drawLine((int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //ligne verticale droite
                                    g.drawLine((int) ChaletController.positionInitialeX , (int) ChaletController.positionInitialeY , (int) ChaletController.positionInitialeX , (int) (ChaletController.positionInitialeY - hauteurPignon ));
                                    //Ligne diagonale supérieur
                                    g.drawLine((int) ChaletController.positionInitialeX , (int) (ChaletController.positionInitialeY - hauteurPignon ), (int) ChaletController.positionInitialeX - (int) epaisseurMur , (int) (ChaletController.positionInitialeY - hauteurRallongeVerticale ));
                                    //Ligne horizontale du bas
                                    g.drawLine((int) (ChaletController.positionInitialeX ), (int) (ChaletController.positionInitialeY ), (int) (ChaletController.positionInitialeX - epaisseurMur ), (int) (ChaletController.positionInitialeY ));
                                }
                                //Pignons
                                //Ligne Verticale gauche
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY , (int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon );
                                //Ligne diagonale
                                g.drawLine((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon ,(int) (ChaletController.positionInitialeX) + (int) longMur, (int) ChaletController.positionInitialeY );
                                //Ligne horizontal du bas
                                g.drawLine((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY ), (int)(ChaletController.positionInitialeX + longMur), (int) (ChaletController.positionInitialeY ));

                                }
                        } else if (this.controleur.getSens() == SensToit.FACADE_ARRIERE) {
                            for (ToitDTO toit : this.controleur.getToit()) {
                                g.drawRect((int) (ChaletController.positionInitialeX), (int) ChaletController.positionInitialeY - (int) hauteurPignon, (int) longMur, (int) hauteurPignon);
                                if(ChaletController.isSideEnabled) {
                                    g.drawRect((int) ChaletController.positionInitialeX, (int) (ChaletController.positionInitialeY - (int) hauteurPignon - (int) epaisseurMur), (int) longMur, (int) epaisseurMur);
                                }
                            }
                        }
                        for (AccessoireDTO accessoire : mur.getAccessoiresDTO()) {
                            double longAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getLongueur()) * multiplicateur;
                            double hautAccessoire = this.controleur.convertirImperialEnPixelDTO(accessoire.getHauteur()) * multiplicateur;
                            g.drawRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                    (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                    (int) longAccessoire,
                                    (int) hautAccessoire);
                            if (accessoire.getType().equals("Porte")) {
                                if (mur.getEmplacementCorrectDTO(accessoire)) {
                                    g.setColor(Color.BLACK);
                                } else {
                                    g.setColor(Color.RED);
                                }
                                g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                        (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                        (int) longAccessoire,
                                        (int) hautAccessoire);
                            } else {
                                if (mur.getEmplacementCorrectDTO(accessoire)) {
                                    g.setColor(Color.gray);
                                } else {
                                    g.setColor(Color.RED);
                                }
                                g.fillRect((int) (accessoire.getPositionX().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeX),
                                        (int) (accessoire.getPositionY().convertirEnDoubleDTO() * multiplicateur + ChaletController.positionInitialeY),
                                        (int) longAccessoire,
                                        (int) hautAccessoire);
                            }
                        }
                    }
                }
            }
        }
    }
}