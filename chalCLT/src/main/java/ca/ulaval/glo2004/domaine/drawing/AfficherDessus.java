package ca.ulaval.glo2004.domaine.drawing;



import ca.ulaval.glo2004.domaine.ChaletController;
import ca.ulaval.glo2004.domaine.DTO.MurDTO;
import ca.ulaval.glo2004.domaine.Mur;

import java.awt.*;
import java.util.Vector;

public class AfficherDessus extends Drawer{

    private ChaletController controleur;

    public AfficherDessus(ChaletController controleur){this.controleur = controleur;}

//    @Override
//    public void drawChalet(Graphics g) {
//        for (MurDTO mur : this.controleur.getMurs()) {
//            if (mur.getNom().equals("left") || mur.getNom().equals("right")) {
//                g.drawRect((int) mur.getPositionX(), (int) mur.getPositionZ(), (int) mur.getEpaisseur(), (int) mur.getLongueur());
//            } else {
//                g.drawRect((int) mur.getPositionX(), (int) mur.getPositionZ(), (int) mur.getLongueur(), (int) mur.getEpaisseur());
//            }
//        }
//    }

    @Override
    public void drawChalet(Graphics g) {
        this.controleur.supprimerRainures();
        this.controleur.calculerRainure();
        for (MurDTO mur : controleur.getMurs()) {
            {
                for (int i = 0; i < mur.getRainures().size(); i++) {
                    if (i != 7) {
                        Vector<Integer> rainure1 = mur.getRainures().get(i);
                        Vector<Integer> rainure2 = mur.getRainures().get(i + 1);
                        if (mur.getNom().equals("back") || mur.getNom().equals("front")) {
                            g.setColor(Color.RED);
                            g.drawLine(rainure1.get(0), rainure1.get(2), rainure2.get(0), rainure2.get(2));
                        }
                        else {
                            g.setColor(Color.BLUE);
                            g.drawLine(rainure1.get(0), rainure1.get(2), rainure2.get(0), rainure2.get(2));
                        }
                        } else {
                            Vector<Integer> rainure3 = mur.getRainures().get(i);
                            Vector<Integer> rainure4 = mur.getRainures().get(0);
                            g.drawLine(rainure3.get(0), rainure3.get(2), rainure4.get(0), rainure4.get(2));
                        }
                    }
                }
            }

        }
    @Override
    void drawFront(Graphics g) {

    }

    @Override
    void drawRight(Graphics g) {

    }

    @Override
    void drawLeft(Graphics g) {

    }

    @Override
    void drawBack( Graphics g) {

    }
}
