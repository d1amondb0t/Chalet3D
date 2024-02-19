package ca.ulaval.glo2004.domaine.drawing;



import ca.ulaval.glo2004.domaine.ChaletController;

import java.awt.*;

abstract class Drawer {

    ChaletController controleur;

    public Drawer(){
        this.controleur = new ChaletController();
    }

    abstract void drawChalet( Graphics g);

    abstract void drawFront(Graphics g);

    abstract void drawRight(Graphics g);
    abstract void drawLeft(Graphics g);

    abstract void drawBack(Graphics g);

}
