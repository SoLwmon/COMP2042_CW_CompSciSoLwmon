package model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class Crack{

    private final GeneralPath crack;

    private final int crackDepth;
    private final int steps;

    private static final Random rnd = new Random();

    /**
     * Crack constructor
     * @param crackDepth Depth of the crack
     * @param steps Steps of brick
     */
    public Crack(int crackDepth, int steps){

        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }

    /**
     * Draw crack on the brick
     * @return crack of the brick
     */
    public GeneralPath draw(){
        return crack;
    }

    /**
     * Resetting the crack on the brick
     */
    public void reset(){
        crack.reset();
    }

    /**
     * Make the crack on the brick
     * @param point the point of the brick that is hit
     * @param direction the direction of the brick that is hit
     * @param bounds the bound of the brick
     */
    protected void makeCrack(Point2D point, int direction, Rectangle bounds){

        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();
        Point tmp = new Point();

        if(direction == getLEFT()){
            moveLeft(bounds,impact,start,end,tmp);
        }else if(direction == getRIGHT()){
            moveRight(bounds,impact,start,end,tmp);
        }else if(direction == getUP()){
            moveUp(bounds,impact,start,end,tmp);
        }else if(direction == getDOWN()){
            moveDown(bounds,impact,start,end,tmp);
        }
    }

    /**
     * Crack to the left brick
     * @param bounds bound of the brick
     * @param impact impact on the brick
     * @param start start point of the brick
     * @param end end point of the brick
     * @param tmp random crack on the brick
     */
    public void moveLeft(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.x + bounds.width, bounds.y);
        end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
        tmp = makeRandomPoint(start,end,getVERTICAL());
        makeCrack(impact,tmp);
    }

    /**
     * Crack to the right brick
     * @param bounds bound of the brick
     * @param impact impact on the brick
     * @param start point of the brick
     * @param end end point of the brick
     * @param tmp random crack on the brick
     */
    public void moveRight(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.getLocation());
        end.setLocation(bounds.x, bounds.y + bounds.height);
        tmp = makeRandomPoint(start,end,getVERTICAL());
        makeCrack(impact,tmp);
    }

    /**
     * Crack to the upper brick
     * @param bounds bound of the brick
     * @param impact impact on the brick
     * @param start start point of the brick
     * @param end end point of the brick
     * @param tmp random crack on the brick
     */
    public void moveUp(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.x, bounds.y + bounds.height);
        end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
        tmp = makeRandomPoint(start,end,getHORIZONTAL());
        makeCrack(impact,tmp);
    }

    /**
     * Crack to the bottom brick
     * @param bounds bound of the brick
     * @param impact impact on the brick
     * @param start start point of the brick
     * @param end end point of the brick
     * @param tmp random crack on the brick
     */
    public void moveDown(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.getLocation());
        end.setLocation(bounds.x + bounds.width, bounds.y);
        tmp = makeRandomPoint(start,end,getHORIZONTAL());
        makeCrack(impact,tmp);
    }

    /**
     * Make the crack on the brick
     * @param start start point of the brick
     * @param end end point of the brick
     */
    protected void makeCrack(Point start, Point end){

        GeneralPath path = new GeneralPath();


        path.moveTo(start.x,start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps;i++){

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i,getCrackSections(),steps))
                y += jumps(jump,getJumpProbability());

            path.lineTo(x,y);

        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);
    }

    /**
     * Random the Bound
     * @param bound bound of brick
     * @return random number of brick
     */
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    /**
     * crack on the middle
     * @param i checker
     * @param steps steps of brick
     * @param divisions division of brick
     * @return checker true or false
     */
    private boolean inMiddle(int i,int steps,int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    /**
     * The probability of the jump
     * @param bound bound of brick
     * @param probability probability of brick
     * @return random in bound
     */
    private int jumps(int bound,double probability){

        if(rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

    /**
     * Make random point
     * @param from start point
     * @param to end point
     * @param direction direction of point
     * @return output location
     */
    private Point makeRandomPoint(Point from,Point to, int direction){

        Point out = new Point();
        int pos;

        if(direction == getHORIZONTAL()){
            pos = rnd.nextInt(to.x - from.x) + from.x;
            out.setLocation(pos,to.y);
        }else if(direction == getVERTICAL()){
            pos = rnd.nextInt(to.y - from.y) + from.y;
            out.setLocation(to.x,pos);
        }
        return out;
    }

    public static int getLEFT(){
        int LEFT = 10;
        return LEFT;
    }

    public static int getRIGHT(){
        int RIGHT = 20;
        return RIGHT;
    }

    public static int getUP(){
        int UP = 30;
        return UP;
    }

    public static int getDOWN(){
        int DOWN = 40;
        return DOWN;
    }

    public static int getVERTICAL(){
        int VERTICAL = 100;
        return VERTICAL;
    }

    public static int getHORIZONTAL(){
        int HORIZONTAL = 200;
        return HORIZONTAL;
    }

    public static int getCrackSections(){
        int CRACK_SECTIONS = 3;
        return CRACK_SECTIONS;
    }

    public static double getJumpProbability(){
        double JUMP_PROBABILITY = 0.7;
        return JUMP_PROBABILITY;
    }
}