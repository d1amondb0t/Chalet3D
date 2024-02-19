package ca.ulaval.glo2004.domaine.utils;

public class Point {

    private Imperial x;
    private Imperial y;

    public Point(Imperial x, Imperial y){
        this.x = x;
        this.y = y;
    }

    public Imperial obtenirX(){
        return this.x;
    }

    public void modifierX(Imperial x){
        this.x = x;
    }

    public Imperial obtenirY(){
        return this.y;
    }

    public void modifierY(Imperial y){
        this.y = y;
    }
}
