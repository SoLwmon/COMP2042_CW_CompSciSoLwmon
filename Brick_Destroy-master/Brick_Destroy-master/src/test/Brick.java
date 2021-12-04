package test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

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

    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;


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

        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();
            Point tmp = new Point();

            switch(direction){
                case LEFT:
                    moveLeft(bounds,impact,start,end,tmp);
                    break;
                case RIGHT:
                    moveRight(bounds,impact,start,end,tmp);
                    break;
                case UP:
                    moveUp(bounds,impact,start,end,tmp);
                    break;
                case DOWN:
                    moveDown(bounds,impact,start,end,tmp);
                    break;
            }
        }

        public void moveLeft(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
        {
            start.setLocation(bounds.x + bounds.width, bounds.y);
            end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
            tmp = makeRandomPoint(start,end,VERTICAL);
            makeCrack(impact,tmp);
        }
        public void moveRight(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
        {
            start.setLocation(bounds.getLocation());
            end.setLocation(bounds.x, bounds.y + bounds.height);
            tmp = makeRandomPoint(start,end,VERTICAL);
            makeCrack(impact,tmp);
        }
        public void moveUp(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
        {
            start.setLocation(bounds.x, bounds.y + bounds.height);
            end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
            tmp = makeRandomPoint(start,end,HORIZONTAL);
            makeCrack(impact,tmp);
        }
        public void moveDown(Rectangle bounds,Point impact,Point start,Point end,Point tmp)
        {
            start.setLocation(bounds.getLocation());
            end.setLocation(bounds.x + bounds.width, bounds.y);
            tmp = makeRandomPoint(start,end,HORIZONTAL);
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

                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

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

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    int strength;

    private boolean broken;

    private int x;
    private int y;
    private int w;
    private int h;
    
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
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




