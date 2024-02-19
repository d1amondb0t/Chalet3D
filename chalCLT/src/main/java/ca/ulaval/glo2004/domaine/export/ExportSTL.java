package ca.ulaval.glo2004.domaine.export;

import ca.ulaval.glo2004.domaine.Chalet;

import java.io.PrintWriter;
import java.util.*;

public abstract class ExportSTL {

    public Chalet chalet;

    public ExportSTL(Chalet c) {
        this.chalet = c;
    }

    public void export() {
    }

    private void exportToit() {
    }

    public abstract void exporterMur(String nomMur);

    public abstract void exporterMurs();

    abstract void exporterRalongeToit();

    abstract void exportPignon();

    abstract void exportDessusToit();

    abstract void convertToSTL();

    public void generateSTL(PrintWriter writer, List<List<RectangleDetection3D.Point3D>> rectangles){
        for (List<RectangleDetection3D.Point3D> rectangle : rectangles) {
            //premier triangle
            RectangleDetection3D.Point3D v0 = rectangle.get(0);
            RectangleDetection3D.Point3D v1 = rectangle.get(1);
            RectangleDetection3D.Point3D v2 = rectangle.get(2);
            RectangleDetection3D.Point3D v3 = rectangle.get(3);


            writer.println("  facet normal 0.0 0.0 0.0");
            writer.println("    outer loop");
            writer.printf("      vertex %.2f %.2f %.2f\n", v0.getX(), v0.getY(), v0.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v1.getX(), v1.getY(), v1.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v2.getX(), v2.getY(), v2.getZ());
            writer.println("    endloop");
            writer.println("  endfacet");

            //deuxieme triangle
            writer.println(" facet normal 0.0 0.0 0.0");
            writer.println("    outer loop");
            writer.printf("      vertex %.2f %.2f %.2f\n", v0.getX(), v0.getY(), v0.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v1.getX(), v1.getY(), v1.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v3.getX(), v3.getY(), v3.getZ());
            writer.println("    endloop");
            writer.println("  endfacet");

            //troisieme triangle
            writer.println("  facet normal 0.0 0.0 0.0");
            writer.println("    outer loop");
            writer.printf("      vertex %.2f %.2f %.2f\n", v0.getX(), v0.getY(), v0.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v2.getX(), v2.getY(), v2.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v3.getX(), v3.getY(), v3.getZ());
            writer.println("    endloop");
            writer.println("  endfacet");
        }
    }

    public void generateSTLTriangles(PrintWriter writer, List<RectangleDetection3D.Triangle> triangles){
        for(RectangleDetection3D.Triangle triangle: triangles){
//            System.out.println("TEST");
//            System.out.println(triangle.v1.getX() + " " + triangle.v1.getY() + " " +  triangle.v1.getZ());
//            System.out.println(triangle.v2.getX() + " " + triangle.v2.getY() + " " +  triangle.v2.getZ());
//            System.out.println(triangle.v3.getX() + " " + triangle.v3.getY() + " " +  triangle.v3.getZ());
            RectangleDetection3D.Point3D v0 = triangle.v1;
            RectangleDetection3D.Point3D v1 = triangle.v2;
            RectangleDetection3D.Point3D v2 = triangle.v3;

            writer.println("  facet normal 0.0 0.0 0.0");
            writer.println("    outer loop");
            writer.printf("      vertex %.2f %.2f %.2f\n", v0.getX(), v1.getY(), v1.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v1.getX(), v1.getY(), v1.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v2.getX(), v2.getY(), v2.getZ());
            writer.println("    endloop");
            writer.println("  endfacet");
            //deuxieme triangle
            writer.println(" facet normal 0.0 0.0 0.0");
            writer.println("    outer loop");
            writer.printf("      vertex %.2f %.2f %.2f\n", v0.getX(), v0.getY(), v0.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v1.getX(), v1.getY(), v1.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v2.getX(), v2.getY(), v2.getZ());
            writer.println("    endloop");
            writer.println("  endfacet");

            //troisieme triangle
            writer.println("  facet normal 0.0 0.0 0.0");
            writer.println("    outer loop");
            writer.printf("      vertex %.2f %.2f %.2f\n", v0.getX(), v0.getY(), v0.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v2.getX(), v2.getY(), v2.getZ());
            writer.printf("      vertex %.2f %.2f %.2f\n", v1.getX(), v1.getY(), v1.getZ());
            writer.println("    endloop");
            writer.println("  endfacet");
        }
    }

    public class RectangleDetection3D {

        public static List<List<Point3D>> trouverRectangles(List<Point3D> points){
            List<List<Point3D>> rectangles = new ArrayList<>();

            for(int i =0; i< points.size(); i++){
                for (int j = i+1; j<points.size();j++){
                    for(int k = j+1; k< points.size(); k++){
                        for( int l = k+1; l< points.size(); l++){
                            List<Point3D> pe_rectangle = new ArrayList<>();
                            pe_rectangle.add(points.get(i));
                            pe_rectangle.add(points.get(j));
                            pe_rectangle.add(points.get(k));
                            pe_rectangle.add(points.get(l));
                            if(checkRectangle(pe_rectangle)) {
                                rectangles.add(pe_rectangle);
                            }
                        }

                    }
                }
            }
            return rectangles;
        }

        public static boolean checkRectangle(List<Point3D> points){
            boolean memeAxeX = true, memeAxeY = true, memeAxeZ = true;
            for (int i = 0; i < points.size()-1; i++){
                if (points.get(i).getX() != points.get(i+1).getX()) memeAxeX = false;
                if( points.get(i).getY() != points.get(i+1).getY()) memeAxeY = false;
                if(points.get(i).getZ() != points.get(i+1).getZ()) memeAxeZ =false;

            }
             if(!memeAxeX && !memeAxeY && !memeAxeZ){
                 return false;
             }

             if(memeAxeX){
                 Collections.sort(points, Comparator.comparing(Point3D::getZ).thenComparing(Point3D::getY));
             } else if (memeAxeY) {
                 Collections.sort(points, Comparator.comparing(Point3D::getX).thenComparing(Point3D::getZ));
             }
             else if (memeAxeZ){
                 Collections.sort(points, Comparator.comparing(Point3D::getX).thenComparing(Point3D::getY));
             }
             double diagonale1 =  checkDiagonale(points.get(0), points.get(2));
             double diagonale2 = checkDiagonale(points.get(1), points.get(3));
             return diagonale1 == diagonale2;
        }

        private static double checkDiagonale(Point3D p1, Point3D p2){
            return Math.sqrt(Math.pow(p1.getX()- p2.getX(), 2)+ Math.pow(p1.getY()-p2.getY(), 2)+ Math.pow(p1.getZ()- p2.getZ(), 2));
        }



         static class Point3D{
            private double x, y, z;

            public Point3D(double x, double y, double z){
                this.x = x;
                this.y = y;
                this.z = z;

            }
            public double getX(){
                return x;
            }

            public double getY(){
                return y;
            }

            public double getZ(){
                return z;
            }
             public double setX(double Nx){
                 return x = Nx;
             }

             public double setY(double Ny){
                 return y = Ny;
             }

             public double setZ(double Nz){
                 return z = Nz;
             }
        }

        static class Triangle {
            Point3D v1, v2, v3;

            Triangle(Point3D v1, Point3D v2, Point3D v3) {
                this.v1 = v1;
                this.v2 = v2;
                this.v3 = v3;
            }
        }


}}
