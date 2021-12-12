package model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;


/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;
    private int fullStrength;
    private int strength;
    public int x;
    public int y;
    public int w;
    public int h;


    public boolean containsPowerUp = false;
    private boolean broken;
    public boolean collisional = true;
    public Shape brickFace;

    private final Color border;
    private final Color inner;

    /**
     * Brick Constructor
     * @param pos coordinate of the brick
     * @param size size of the brick
     * @param border colour of border brick
     * @param inner colour of inner brick
     * @param strength strength of the brick
     */
    public Brick(Point pos,Dimension size,Color border,Color inner,int strength){

        broken = false;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = strength;
        this.strength = strength;
        x=pos.x;
        y=pos.y;
        w=size.width;
        h=size.height;
    }

    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * Set the impact of the brick
     * @param point coordinate of the brick
     * @param dir where the brick is impact
     * @return state of brick
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
            return  broken;
    }

    public abstract Shape getBrick();

    /**
     * The shape of brick that has powerup
     * @return the shape of the powerup brick
     */
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


    /**
     * impact of the brick when the ball hit the brick
     * @param b ball type
     * @return direction of the ball after impact
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(getBrickFace().contains(b.getRight()))
            out = LEFT_IMPACT;
        else if(getBrickFace().contains(b.getLeft()))
            out = RIGHT_IMPACT;
        else if(getBrickFace().contains(b.getUp()))
            out = DOWN_IMPACT;
        else if(getBrickFace().contains(b.getDown()))
            out = UP_IMPACT;
        return out;
    }

    /**
     *  return broken state of a brick
     * @return broken state when is broken
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * reset the state of the brick
     */
    public void repair() {
        broken = false;
        setStrength(getFullStrength());
    }

    /**
     * reduce the strength of the brick, if strength equal 0 the brick is broken
     */
    public void impact(){
        setStrength(getStrength()-1);
        broken = (getStrength() == 0);
    }


    public void setPowerUp() {
        this.containsPowerUp=true;
    }

    public int getStrength(){
        return strength;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public int getFullStrength(){
        return fullStrength;
    }

    public Shape getBrickFace(){
        return brickFace;
    }

}





