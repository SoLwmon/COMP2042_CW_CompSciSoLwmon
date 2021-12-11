package model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;


/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Brick  {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    public boolean containsPowerUp = false;


    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    int strength;

    private boolean broken;

    public int x;
    public int y;
    public int w;
    public int h;
    public int line;
    public int indexLine;
    public boolean collisionable = true;
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){

        broken = false;
        this.name = name;
Brick  {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    public boolean containsPowerUp = false;


    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    int strength;

    private boolean broken;

    public int x;
    public int y;
    public int w;
    public int h;
    public int line;
    public int indexLine;
    public boolean collisionable = true;
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){

        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
        x=pos.x;
        y=pos.y;
        w=size.width;
        h=size.height;
    }

    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    public abstract Shape getBrick();

    public Shape getPowerUpEmplacement() {
        return new Ellipse2D.Float(x+w/2-h/4,y+h/4,h/2,h/2);
    }

    public Shape getPowerUpEmplacement2() {
        return new Ellipse2D.Float(x+w/2-h/4+3,y+h/4+3,h/2-6,h/2-6);
    }

    public Shape getPowerUpEmplacement3() {
        return new Ellipse2D.Float(x+w/2-h/4+5,y+h/4+5,h/2-10,h/2-10);
    }

    public Color getBorderColor(){
        return  border;
    }

    public Color getInnerColor(){
        return inner;
    }


    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    public final boolean isBroken(){
        return broken;
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    public void setPowerUp() {
        this.containsPowerUp=true;
    }

}


