package model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class Crack{

    private static int CRACK_SECTIONS = 3;
    private static double JUMP_PROBABILITY = 0.7;

    private static int LEFT = 10;
    private static int RIGHT = 20;
    private static int UP = 30;
    private static int DOWN = 40;
    private static int VERTICAL = 100;
    private static int HORIZONTAL = 200;

    private GeneralPath crack;

    private int crackDepth;
    private int steps;

    private static Random rnd = new Random();


    public Crack(int crackDepth, int steps){

        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }



    public GeneralPath draw(){

        return crack;
    }

    public void reset(){
        crack.reset();
    }

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

    public void moveLeft(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.x + bounds.width, bounds.y);
        end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
        tmp = makeRandomPoint(start,end,getVERTICAL());
        makeCrack(impact,tmp);
    }
    public void moveRight(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.getLocation());
        end.setLocation(bounds.x, bounds.y + bounds.height);
        tmp = makeRandomPoint(start,end,getVERTICAL());
        makeCrack(impact,tmp);
    }
    public void moveUp(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.x, bounds.y + bounds.height);
        end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
        tmp = makeRandomPoint(start,end,getHORIZONTAL());
        makeCrack(impact,tmp);
    }
    public void moveDown(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
    {
        start.setLocation(bounds.getLocation());
        end.setLocation(bounds.x + bounds.width, bounds.y);
        tmp = makeRandomPoint(start,end,getHORIZONTAL());
        makeCrack(impact,tmp);
    }

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

    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    private boolean inMiddle(int i,int steps,int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    private int jumps(int bound,double probability){

        if(rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

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
    
    /**
     * get method for left,
     * @return left cracking
     */
    public static int getLEFT(){
        return LEFT;
    }

    public static int getRIGHT(){
        return RIGHT;
    }

    public static int getUP(){
        return UP;
    }

    public static int getDOWN(){
        return DOWN;
    }

    public static int getVERTICAL(){
        return VERTICAL;
    }

    public static int getHORIZONTAL(){
        return HORIZONTAL;
    }

    public static int getCrackSections(){
        return CRACK_SECTIONS;
    }

    public static double getJumpProbability(){
        return JUMP_PROBABILITY;
    }
}
