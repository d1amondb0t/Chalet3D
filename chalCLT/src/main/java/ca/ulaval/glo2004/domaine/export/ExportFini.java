package ca.ulaval.glo2004.domaine.export;

import ca.ulaval.glo2004.domaine.Chalet;
import ca.ulaval.glo2004.domaine.ChaletController;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.Toit;
import ca.ulaval.glo2004.domaine.utils.Point;

import java.io.PrintWriter;
import java.util.*;

import static ca.ulaval.glo2004.domaine.export.ExportSTL.RectangleDetection3D.trouverRectangles;

public class ExportFini extends ExportSTL {

    public ExportFini(Chalet c) {
        super(c);
    }

    @Override
    public void exporterMurs() {

    }

    private ChaletController controleur;

    @Override
    public void exporterMur(String nomMur) {
        List<RectangleDetection3D.Point3D> points = new ArrayList<>();
        try (PrintWriter writer = new PrintWriter(nomMur + (" export fini.stl"), "UTF-8")) {
            writer.println("solid object");
            for (Mur mur : this.chalet.getMurs()) {
                //if (nomMur.equals(mur.getNom())) {
                double hauteur = mur.getHauteur().convertirEnDouble();
                points.clear();

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1), mur.getRainures().get(0).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1), mur.getRainures().get(1).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1), mur.getRainures().get(2).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1), mur.getRainures().get(3).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(4).get(0), mur.getRainures().get(4).get(1), mur.getRainures().get(4).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(5).get(0), mur.getRainures().get(5).get(1), mur.getRainures().get(5).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(6).get(0), mur.getRainures().get(6).get(1), mur.getRainures().get(6).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(7).get(0), mur.getRainures().get(7).get(1), mur.getRainures().get(7).get(2)));


                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(0).get(0), mur.getRainures().get(0).get(1) + hauteur, mur.getRainures().get(0).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(1).get(0), mur.getRainures().get(1).get(1) + hauteur, mur.getRainures().get(1).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(2).get(0), mur.getRainures().get(2).get(1) + hauteur, mur.getRainures().get(2).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(3).get(0), mur.getRainures().get(3).get(1) + hauteur, mur.getRainures().get(3).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(4).get(0), mur.getRainures().get(4).get(1) + hauteur, mur.getRainures().get(4).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(5).get(0), mur.getRainures().get(5).get(1) + hauteur, mur.getRainures().get(5).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(6).get(0), mur.getRainures().get(6).get(1) + hauteur, mur.getRainures().get(6).get(2)));

                points.add(new RectangleDetection3D.Point3D(mur.getRainures().get(7).get(0), mur.getRainures().get(7).get(1) + hauteur, mur.getRainures().get(7).get(2)));


                for(RectangleDetection3D.Point3D point: points){
                   double Np =  point.getY();
                   point.setY(Np-1.92*hauteur);
                   double Npx  = point.getX();
                   point.setX(Npx- 0.827*hauteur);
                   double NpZ = point.getZ();
                   point.setZ(NpZ - 0.92*hauteur);
                }


                List<List<RectangleDetection3D.Point3D>> rectangles = trouverRectangles(points);


                generateSTL(writer, rectangles);
            }

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
                } else if (toit.getNom().equals("pignonGauche")) {
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
                } else  if (toit.getNom().equals("rallongeVertical")) {
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

                }
            }

            writer.println("endsolid object");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void verifierAccessoires(List<List<RectangleDetection3D.Point3D>> rectangles, List<Vector<Double>> accessoires) {
        Iterator<List<RectangleDetection3D.Point3D>> iterator = rectangles.iterator();
        int k = 0;

        while (iterator.hasNext()) {
            List<RectangleDetection3D.Point3D> rectangle = iterator.next();
            boolean accessoireDansRectangle = true; // Assume it's inside until proven otherwise

            for (int i = 0; i < accessoires.size(); i += 8) {
                for (int j = i; j < i + 4 && j < accessoires.size(); j++) {
                    Vector<Double> point = accessoires.get(j);
                    double x = point.get(0);
                    double y = point.get(1);

                    // Check if the point is outside the rectangle
                    if (j == i && (rectangle.get(0).getX() > x || rectangle.get(0).getY() > y)) {
                        accessoireDansRectangle = false;
                        break;
                    } else if (j == i + 3 && (rectangle.get(1).getX() > x || rectangle.get(1).getY() < y)) {
                        accessoireDansRectangle = false;
                        break;
                    } else if (j == i + 1 && (rectangle.get(2).getX() < x || rectangle.get(2).getY() > y)) {
                        accessoireDansRectangle = false;
                        break;
                    } else if (j == i + 2 && (rectangle.get(3).getX() < x || rectangle.get(3).getY() < y)) {
                        accessoireDansRectangle = false;
                        break;
                    }
                }
            }

            if (accessoireDansRectangle) {
                //System.out.println("removed accessoire");
                // System.out.println(k);
                iterator.remove();
            }

            k++;
        }
    }

    private void verifierAccessoiresGaucheDroite(List<List<RectangleDetection3D.Point3D>> rectangles, List<Vector<Double>> accessoires) {
        Iterator<List<RectangleDetection3D.Point3D>> iterator = rectangles.iterator();
        int k = 0;

        while (iterator.hasNext()) {
            List<RectangleDetection3D.Point3D> rectangle = iterator.next();
            boolean accessoireDansRectangle = true; // Assume it's inside until proven otherwise

            for (int i = 0; i < accessoires.size(); i += 8) {
                for (int j = i; j < i + 4 && j < accessoires.size(); j++) {
                    Vector<Double> point = accessoires.get(j);
                    double z = point.get(2);
                    double y = point.get(1);

                    // Check if the point is outside the rectangle
                    if (j == i && (rectangle.get(0).getZ() > z || rectangle.get(0).getY() > y)) {
                        accessoireDansRectangle = false;
                        break;
                    } else if (j == i + 3 && (rectangle.get(1).getZ() > z || rectangle.get(1).getY() < y)) {
                        accessoireDansRectangle = false;
                        break;
                    } else if (j == i + 1 && (rectangle.get(2).getZ() < z || rectangle.get(2).getY() > y)) {
                        accessoireDansRectangle = false;
                        break;
                    } else if (j == i + 2 && (rectangle.get(3).getZ() < z || rectangle.get(3).getY() < y)) {
                        accessoireDansRectangle = false;
                        break;
                    }
                }
            }
            if (accessoireDansRectangle) {
                //System.out.println("removed accessoire");
                // System.out.println(k);
                iterator.remove();
            }

            k++;
        }
    }

    @Override
    void exporterRalongeToit() {

    }

    @Override
    void exportPignon() {
        List<RectangleDetection3D.Point3D> points = new ArrayList<>();

        for (Toit toit : this.chalet.getToit()) {
            if (toit.getNom().equals("pignonGauche")) {
                List<RectangleDetection3D.Triangle> triangles = new ArrayList<>();
                List<Vector<Double>> rainures = toit.getRainures();
                for (Vector<Double> rainure : rainures) {
                    points.add(new RectangleDetection3D.Point3D(rainure.get(0), rainure.get(1), rainure.get(2)));
                }

                //Ajouter Triangle manuellement
                triangles.add(new RectangleDetection3D.Triangle(points.get(0), points.get(1), points.get(2))); //cote devant
                triangles.add(new RectangleDetection3D.Triangle(points.get(1), points.get(2), points.get(3)));
                triangles.add(new RectangleDetection3D.Triangle(points.get(1), points.get(3), points.get(4)));

                triangles.add(new RectangleDetection3D.Triangle(points.get(5), points.get(7), points.get(8))); //cote derriere
                triangles.add(new RectangleDetection3D.Triangle(points.get(5), points.get(6), points.get(8)));
                triangles.add(new RectangleDetection3D.Triangle(points.get(6), points.get(8), points.get(9)));

                triangles.add(new RectangleDetection3D.Triangle(points.get(0), points.get(2), points.get(5))); //bas
                triangles.add(new RectangleDetection3D.Triangle(points.get(2), points.get(7), points.get(5)));

                triangles.add(new RectangleDetection3D.Triangle(points.get(2), points.get(7), points.get(8)));
                triangles.add(new RectangleDetection3D.Triangle(points.get(2), points.get(8), points.get(3)));//petit devant

                triangles.add(new RectangleDetection3D.Triangle(points.get(3), points.get(8), points.get(9))); //hauta angle
                triangles.add(new RectangleDetection3D.Triangle(points.get(3), points.get(9), points.get(4)));

                triangles.add(new RectangleDetection3D.Triangle(points.get(0), points.get(4), points.get(9))); //cote derriere
                triangles.add(new RectangleDetection3D.Triangle(points.get(0), points.get(5), points.get(9)));


            }
        }

    }

    @Override
    void exportDessusToit() {

    }

    @Override
    void convertToSTL() {

    }
}
