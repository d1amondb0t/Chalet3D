package ca.ulaval.glo2004.domaine.export;

import ca.ulaval.glo2004.domaine.Chalet;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.SensToit;

import java.io.PrintWriter;
import java.util.*;

import static ca.ulaval.glo2004.domaine.export.ExportSTL.RectangleDetection3D.trouverRectangles;

public class ExportRetrait extends ExportSTL {
    public ExportRetrait(Chalet c) {
        super(c);
    }

    @Override
    public void exporterMur(String nomMur) {
        List<ExportSTL.RectangleDetection3D.Point3D> points = new ArrayList<>();
        int cpt = 0;
        for(int i =0; i<2; i++) {
            try (PrintWriter writer = new PrintWriter(  nomMur+i+" retraits.stl ", "UTF-8")) {
                writer.println("solid object");
                for (Mur mur : this.chalet.getMurs()) {
                    if (chalet.getSens().equals(SensToit.ARRIERE_FACADE) || chalet.getSens().equals(SensToit.FACADE_ARRIERE)) {
                        if ((nomMur.equals("left") || nomMur.equals("right") && (cpt == 0))) {
                            points.clear();
                            cpt++;
                            mur = chalet.getMur("left");
                            double hauteur = mur.getHauteur().convertirEnDouble();
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));

                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                        } else if ((nomMur.equals("back") && (cpt == 0))) {
                            points.clear();
                            cpt++;
                            //mur = chalet.getMur("back");
                            double hauteur = mur.getHauteur().convertirEnDouble();
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1), mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));

                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1) + hauteur, mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));

                        } else if ((nomMur.equals("front") && (cpt == 0))) {

                            points.clear();
                            cpt++;
                            //mur = chalet.getMur("back");
                            double hauteur = mur.getHauteur().convertirEnDouble();
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1), mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));

                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1) + hauteur, mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                        }
                    } else {
                        if ((nomMur.equals("right") && (cpt == 0))) {
                            points.clear();
                            cpt++;
                            //mur = chalet.getMur("back");
                            double hauteur = mur.getHauteur().convertirEnDouble();
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1), mur.getRainures().get(3).get(2)));
//                        points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble()));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1), mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));

                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1) + hauteur, mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                        } else if ((nomMur.equals("left") && (cpt == 0))) {
                            points.clear();
                            cpt++;
                            //mur = chalet.getMur("back");
                            double hauteur = mur.getHauteur().convertirEnDouble();
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1), mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));

                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1) + hauteur, mur.getRainures().get(3).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0) + mur.getEpaisseur().convertirEnDouble(), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2) - mur.getEpaisseur().convertirEnDouble() / 2));
                        } else if (((nomMur.equals("back") || nomMur.equals("front")) && (cpt == 0))) {
                            points.clear();
                            cpt++;
                            mur = chalet.getMur("back");
                            double hauteur = mur.getHauteur().convertirEnDouble();
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));

                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                            points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0) - mur.getEpaisseur().convertirEnDouble() / 2, mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));
                        }
                    }
                }
//System.out.println("-------------------------------------");

                List<List<RectangleDetection3D.Point3D>> rectangles = trouverRectangles(points);
              //  System.out.println("-------------------------------------");
              //  System.out.println("Coordonnées des points");
                for (RectangleDetection3D.Point3D point : points) {
                    //points.add(new RectangleDetection3D.Point3D( position.get(0), position.get(1), position.get(2)));
                    System.out.println("X: " + point.getX() + ", Y:" + point.getY() + ", Z: " + point.getZ());
                }


                Iterator<List<RectangleDetection3D.Point3D>> iterator = rectangles.iterator();
                while (iterator.hasNext()) {
                    List<RectangleDetection3D.Point3D> rectangle = iterator.next();
                    Set<Double> posX = new HashSet<>();
                    Set<Double> posY = new HashSet<>();
                    Set<Double> posZ = new HashSet<>();
                    for (RectangleDetection3D.Point3D point : rectangle) {
                        posX.add(point.getX());
                        posY.add(point.getY());
                        posZ.add(point.getZ());
                    }

                    if (posX.size() > 2 || posY.size() > 2 || posZ.size() > 2) {
                        iterator.remove();
                    }
                }


                generateSTL(writer, rectangles);


                writer.println("endsolid object");
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        }
        }

        @Override
        public void exporterMurs () {

        }

        @Override
        void exporterRalongeToit () {

        }

        @Override
        void exportPignon () {

        }

        @Override
        void exportDessusToit () {

        }

        @Override
        void convertToSTL () {

        }
    }
