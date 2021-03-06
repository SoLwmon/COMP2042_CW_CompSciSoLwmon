package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */
public abstract class Ball {
    public static final int SPEED = 4;
    public static final int SPEED_FAST = 6;
	private static final int TIMER_STATE_FIRE = 250;
	public static final int STATE_NORMAL = 0;
	public static final int STATE_FIRE = 1;

	
    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;
    
    private int stateBall;
    private int timer;
    
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    public void handleState() {
    	if (stateBall==STATE_FIRE) {
    		timer++;
    		if (timer>=TIMER_STATE_FIRE) {
    			timer=0;
    			changeState(STATE_NORMAL);
    		}
    	}
    }
    
    public void move(){

        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);

        ballFace = tmp;
        handleState();
    }

    public void setSpeed(int x, int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
        speedY = s;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public void setBorderColor(Color c){
        border = c;
    }

    public void setInnerColor(Color c){
        inner = c;
    }
    
    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

    public int getState() {
    	return stateBall;
    }
    
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }

    public abstract void changeColorState(int state);
    
    public void changeState(int state) {
    	changeColorState(state);
    	switch (state) {
	    	case STATE_NORMAL :
	    		speedX = speedX>0?SPEED:-SPEED;
	    		speedY = speedY>0?SPEED:-SPEED;
	    		break;
	    	case STATE_FIRE :
	    		speedX = speedX>0?SPEED_FAST:-SPEED_FAST;
	    		speedY = speedY>0?SPEED_FAST:-SPEED_FAST;
	    		break;
    		default :
    			break;
    	}
    	stateBall=state;
    }
    
}

