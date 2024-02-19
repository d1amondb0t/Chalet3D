package ca.ulaval.glo2004.domaine.export;

import ca.ulaval.glo2004.domaine.Chalet;
import ca.ulaval.glo2004.domaine.DTO.MurDTO;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.Toit;
import ca.ulaval.glo2004.domaine.utils.Imperial;
import ca.ulaval.glo2004.domaine.ChaletController;

import java.io.PrintWriter;
import java.sql.Array;
import java.text.CollationElementIterator;
import java.util.*;

import static ca.ulaval.glo2004.domaine.export.ExportSTL.RectangleDetection3D.trouverRectangles;

public class ExportBrut extends ExportSTL {

    public ExportBrut(Chalet c) {
        super(c);
    }

    private ChaletController controleur;

    @Override
    public void exporterMur(String nomMur) {
        List<RectangleDetection3D.Point3D> points = new ArrayList<>();
        int cpt =0;
        try (PrintWriter writer = new PrintWriter(nomMur + " brut.stl", "UTF-8")) {
            writer.println("solid object");
            for (Mur mur : this.chalet.getMurs()) {
                if ((nomMur.equals("left") || nomMur.equals("right")) && cpt==0) {
                    cpt++;
                    double hauteur = mur.getHauteur().convertirEnDouble();
                    double longueur = mur.getLongueur().convertirEnDouble();
                    double epaisseur = mur.getEpaisseur().convertirEnDouble();
                    points.clear();

                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble()));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble() + longueur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble() + longueur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble()));

                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + epaisseur, mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble()));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + epaisseur, mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble() + longueur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + epaisseur, mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble() + longueur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + epaisseur, mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble()));

                } else if(((nomMur.equals("back")) || nomMur.equals("front")) && cpt==0) {
                    cpt++;
                    double hauteur = mur.getHauteur().convertirEnDouble();
                    double longueur = mur.getLongueur().convertirEnDouble();
                    double epaisseur = mur.getEpaisseur().convertirEnDouble();
                    points.clear();

                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble()));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + longueur, mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble()));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + longueur, mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble()));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble()));

                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble() + epaisseur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + longueur, mur.getPositionY().convertirEnDouble(), mur.getPositionZ().convertirEnDouble() + epaisseur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble() + longueur, mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble() + epaisseur));
                    points.add(new RectangleDetection3D.Point3D(mur.getPositionX().convertirEnDouble(), mur.getPositionY().convertirEnDouble() + hauteur, mur.getPositionZ().convertirEnDouble() + epaisseur));

                }

                List<List<RectangleDetection3D.Point3D>> rectangles = trouverRectangles(points);

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
                for (List<RectangleDetection3D.Point3D> rectangle : rectangles) {
                    System.out.println("3D Rectangle:");
                    for (RectangleDetection3D.Point3D point : rectangle) {
                        System.out.println("(" + point.getX() + ", " + point.getY() + ", " + point.getZ() + ")");
                    }
                    System.out.println();
                }
                generateSTL(writer, rectangles);
            }

            writer.println("endsolid object");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


   /* public void exporterMur(String nomMur) {
        try (PrintWriter writer = new PrintWriter(nomMur + (" export brut.stl"), "UTF-8")) {
            writer.println("solid object");
            List<RectangleDetection3D.Point3D> pointTr = new ArrayList<>();

            for (Toit toit : this.chalet.getToit()) {
                //System.out.println(toit.getNom());
//
                if (toit.getNom().equals("pignonDroite")) {
                    pointTr.clear();
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                    }

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(3), pointTr.get(4)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(3)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(9)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(5)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(5)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(8), pointTr.get(9))); //hauta angle
                    //triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(9), pointTr.get(4)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(8), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(4), pointTr.get(9))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(9)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(10), pointTr.get(13), pointTr.get(14))); //cote devant
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(10), pointTr.get(12), pointTr.get(13)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(16)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(14))); //bas
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(14)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(17)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(17), pointTr.get(12)));//petit devant

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(17), pointTr.get(18))); //hauta angle
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(18), pointTr.get(13)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(13), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(14), pointTr.get(18)));


                    generateSTLTriangles(writer, triangles);

                    writer.println("endsolid object");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try (PrintWriter writer = new PrintWriter(nomMur + (" export bruggt.stl"), "UTF-8")) {
            writer.println("solid object");
            List<RectangleDetection3D.Point3D> pointTr = new ArrayList<>();

            for (Toit toit : this.chalet.getToit()) {
                if (toit.getNom().equals("pignonGauche")) {
                    pointTr.clear();
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                    }

                    //Ajouter Triangle manuellement
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(3), pointTr.get(4))); //cote devant
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(9))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(5))); //bas
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(5)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(8), pointTr.get(3)));//petit devant

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(8), pointTr.get(9))); //hauta angle
                    //triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(9), pointTr.get(4)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(4), pointTr.get(9))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(9)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(10), pointTr.get(13), pointTr.get(14))); //cote devant
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(12)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(16)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(14))); //bas
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(14)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(17)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(17), pointTr.get(12)));//petit devant

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(17), pointTr.get(18))); //hauta angle
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(18), pointTr.get(13)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(13), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(14), pointTr.get(18)));


                    //generateSTL(writer, rectangles);
                    generateSTLTriangles(writer, triangles);
                    break;
                } else if (toit.getNom().equals("rallongeVertical")) {
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    pointTr.clear();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                        //Ajouter les triangles pour la rallonge

                    }
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(1), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(2), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(5), pointTr.get(6)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(5), pointTr.get(7)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(6), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(0), pointTr.get(6)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(1)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(1), pointTr.get(5)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(3), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(1), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(2), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(3), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(1), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(2), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(5), pointTr.get(6)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(5), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(8), pointTr.get(9), pointTr.get(10)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(10), pointTr.get(11)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(13), pointTr.get(14)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(13), pointTr.get(15)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(8), pointTr.get(14), pointTr.get(10)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(8), pointTr.get(14)));
//
//                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(8), pointTr.get(13), pointTr.get(9)));
//                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(9), pointTr.get(13)));
////
//                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(15)));
//                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(13), pointTr.get(9), pointTr.get(15)));
////
//                       triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(10), pointTr.get(15)));
//                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(11), pointTr.get(15)));

                    //test
                    //triangles.add(new RectangleDetection3D.Triangle(pointTr.get(16), pointTr.get(17), pointTr.get(18)));
                    generateSTLTriangles(writer, triangles);
                    break;
                } else if (toit.getNom().equals("panneauSuperieur")) {
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    pointTr.clear();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                        //Ajouter les triangles pour la rallonge

                    }
                    System.out.println("Here");
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(1), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(2), pointTr.get(4)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(3), pointTr.get(4)));//Left

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(6), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(7), pointTr.get(9)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(7), pointTr.get(8), pointTr.get(9)));//Right


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(1)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(5), pointTr.get(6)));//Back

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(4), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(8), pointTr.get(9)));//Front

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(4), pointTr.get(9)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(6), pointTr.get(9)));//Top


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(3), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(8)));//Bottom

                    generateSTLTriangles(writer, triangles);
                    break;
                }
            }


            writer.println("endsolid object");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    public void exporterMurs() {
        try (PrintWriter writer = new PrintWriter((" pignondroite.stl"), "UTF-8")) {
            writer.println("solid object");
            List<RectangleDetection3D.Point3D> pointTr = new ArrayList<>();

            for (Toit toit : this.chalet.getToit()) {
                //System.out.println(toit.getNom());
//
                if (toit.getNom().equals("pignonDroite")) {
                    pointTr.clear();
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                    }

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(3), pointTr.get(4)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(3)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(9)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(5)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(5)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(8), pointTr.get(9))); //hauta angle
                    //triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(9), pointTr.get(4)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(8), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(4), pointTr.get(9))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(9)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(10), pointTr.get(13), pointTr.get(14))); //cote devant
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(10), pointTr.get(12), pointTr.get(13)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(16)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(14))); //bas
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(14)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(17)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(17), pointTr.get(12)));//petit devant

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(17), pointTr.get(18))); //hauta angle
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(18), pointTr.get(13)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(13), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(14), pointTr.get(18)));


                    generateSTLTriangles(writer, triangles);

                    writer.println("endsolid object");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try (PrintWriter writer = new PrintWriter((" pignongauche.stl"), "UTF-8")) {
            writer.println("solid object");
            List<RectangleDetection3D.Point3D> pointTr = new ArrayList<>();

            for (Toit toit : this.chalet.getToit()) {
                if (toit.getNom().equals("pignonGauche")) {
                    pointTr.clear();
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                    }

                    //Ajouter Triangle manuellement
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(3), pointTr.get(4))); //cote devant
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(9))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(8), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(5))); //bas
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(5)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(8), pointTr.get(3)));//petit devant

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(8), pointTr.get(9))); //hauta angle
                    //triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(9), pointTr.get(4)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(4), pointTr.get(9))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(9)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(10), pointTr.get(13), pointTr.get(14))); //cote devant
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(12)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(17), pointTr.get(16)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(14))); //bas
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(14)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(16), pointTr.get(17)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(11), pointTr.get(17), pointTr.get(12)));//petit devant

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(17), pointTr.get(18))); //hauta angle
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(18), pointTr.get(13)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(13), pointTr.get(18))); //cote derriere
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(14), pointTr.get(18)));


                    //generateSTL(writer, rectangles);
                    generateSTLTriangles(writer, triangles);
                    writer.println("endsolid object");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try (PrintWriter writer = new PrintWriter((" rallongeverticale.stl"), "UTF-8")) {
            writer.println("solid object");
            List<RectangleDetection3D.Point3D> pointTr = new ArrayList<>();

            for (Toit toit : this.chalet.getToit()) {
                if (toit.getNom().equals("rallongeVertical")) {
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    pointTr.clear();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                        //Ajouter les triangles pour la rallonge

                    }
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(1), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(2), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(5), pointTr.get(6)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(5), pointTr.get(7)));


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(6), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(0), pointTr.get(6)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(1)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(1), pointTr.get(5)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(3), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(1), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(2), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(3), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(1), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(2), pointTr.get(3)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(5), pointTr.get(6)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(5), pointTr.get(7)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(8), pointTr.get(9), pointTr.get(10)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(10), pointTr.get(11)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(13), pointTr.get(14)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(13), pointTr.get(15)));

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(8), pointTr.get(14), pointTr.get(10)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(8), pointTr.get(14)));

                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(8), pointTr.get(13), pointTr.get(9)));
                       triangles.add(new RectangleDetection3D.Triangle(pointTr.get(12), pointTr.get(9), pointTr.get(13)));

                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(9), pointTr.get(11), pointTr.get(15)));
                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(13), pointTr.get(9), pointTr.get(15)));

                       triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(10), pointTr.get(15)));
                        triangles.add(new RectangleDetection3D.Triangle(pointTr.get(14), pointTr.get(11), pointTr.get(15)));

                    //test
                    //triangles.add(new RectangleDetection3D.Triangle(pointTr.get(16), pointTr.get(17), pointTr.get(18)));
                    generateSTLTriangles(writer, triangles);
                    writer.println("endsolid object");
                }
            }
      } catch (Exception e) {
            e.printStackTrace();
        }


        try (PrintWriter writer = new PrintWriter((" panneausuperieur.stl"), "UTF-8")) {
            writer.println("solid object");
            List<RectangleDetection3D.Point3D> pointTr = new ArrayList<>();

            for (Toit toit : this.chalet.getToit()) {
                if (toit.getNom().equals("panneauSuperieur")) {
                    List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                    List<Vector<Double>> rainures = toit.getRainures();
                    pointTr.clear();
                    for (Vector<Double> rainure : rainures) {
                        pointTr.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                        //Ajouter les triangles pour la rallonge

                    }
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(1), pointTr.get(2)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(2), pointTr.get(4)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(3), pointTr.get(4)));//Left

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(5), pointTr.get(6), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(6), pointTr.get(7), pointTr.get(9)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(7), pointTr.get(8), pointTr.get(9)));//Right


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(1)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(5), pointTr.get(6)));//Back

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(3), pointTr.get(4), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(4), pointTr.get(8), pointTr.get(9)));//Front

                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(4), pointTr.get(9)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(1), pointTr.get(6), pointTr.get(9)));//Top


                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(2), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(0), pointTr.get(5), pointTr.get(7)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(3), pointTr.get(8)));
                    triangles.add(new RectangleDetection3D.Triangle(pointTr.get(2), pointTr.get(7), pointTr.get(8)));//Bottom

                    generateSTLTriangles(writer, triangles);
                    break;
                }
            }


            writer.println("endsolid object");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    void exporterRalongeToit() {

    }

    @Override
    void exportPignon() {

    }

    @Override
    void exportDessusToit() {

    }

    @Override
    void convertToSTL() {

    }
}
