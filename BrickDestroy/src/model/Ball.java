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

    private final Point2D center;

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

    /**
     * A constructor to create new ball when called
     * @param center center of the ball
     * @param radiusA radius of the ball for x axis
     * @param radiusB radius of the ball for y axis
     * @param inner inner colour of the ball
     * @param border border colour of the ball
     */
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

    /**
     * To set the timer of the ball when changese into a fireball
     */
    public void handleState() {
    	if (getStateBall()==getStateFire()) {
    	    setTimer(getTimer()+1);
    		if (getTimer()>=getTimerStateFire()) {
    			setTimer(0);
    			changeState(getStateNormal());
    		}
    	}
    }

    /**
     * set speed of the ball
     * @param x set speed of the ball x axis
     * @param y set speed of the ball y axis
     */
    public void setSpeed(int x, int y){
        speedX = x;
        speedY = y;
    }

    /**
     * reverse the ball in x axis
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * reverse the ball in y axis
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * get colour border of ball
     * @return border
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * get colour inner of ball
     * @return colour of inner ball
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * set border colour of ball
     * @param c set border colour
     */
    public void setBorderColor(Color c){
        border = c;
    }

    /**
     * set inner colour of ball
     * @param c set inner colour
     */
    public void setInnerColor(Color c){
        inner = c;
    }

    /**
     * get center position
     * @return position of the ball
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * get the face of the ball
     * @return the face of the ball
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * get the state of the ball
     * @return the state of the ball
     */
    public int getState() {
    	return stateBall;
    }

    /**
     * get the speed of the ball
     * @return speed of ball
     */
    public int getSpeed(){
        return SPEED;
    }

    /**
     * get the speed of the fireball
     * @return speed of fireball
     */
    public int getSpeedFast(){
        return SPEED_FAST;
    }

    /**
     * get the state of the ball
     * @return state of ball
     */
    public int getStateBall(){
        return stateBall;
    }

    /**
     * get the timer of the fireball
     * @return timer for fireball
     */
    public int getTimerStateFire(){
        return TIMER_STATE_FIRE;
    }

    /**
     * get the state of the fireball
     * @return state of fireball
     */
    public int getStateFire(){
        return STATE_FIRE;
    }

    /**
     *  get time to change state
     * @return timer
     */
    public int getTimer(){
        return timer;
    }

    /**
     *  change the state of the ball to normal
     * @return state of the ball
     */
    public int getStateNormal(){
        return STATE_NORMAL;
    }

    /**
     * set the timer for the ball
     * @param timer for the ball
     */
    public void setTimer(int timer){
        this.timer = timer;
    }

    /**
     *  move the ball to  a specific location
     * @param p point to move
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * movement of the ball
     */
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

    /**
     * Set the location of the ball
     * @param width location x axis
     * @param height location y axis
     */
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

    public void setSpeedX(int s){
        speedX = s;
    }

    public void setSpeedY(int s){
        speedY = s;
    }

    public Point2D getUp() {
        return up;
    }

    public Point2D getDown() {
        return down;
    }

    public Point2D getLeft() {
        return left;
    }

    public Point2D getRight() {
        return right;
    }

    /**
     * To change the state of the ball to fireball
     * @param state changes of the ball to fireball
     */
    public abstract void changeColorState(int state);
    
    public void changeState(int state) {
    	changeColorState(state);
    	switch (state) {
	    	case STATE_NORMAL :
	    	    setSpeedX(getSpeedX()>0?getSpeed():-getSpeed());
                setSpeedY(getSpeedY()>0?getSpeed():-getSpeed());
	    		break;
	    	case STATE_FIRE :
	    	    setSpeedX(getSpeedX()>0?getSpeedFast():-getSpeedFast());
	    	    setSpeedY(getSpeedY()>0?getSpeedFast():-getSpeedFast());
	    		break;
    		default :
    			break;
    	}
    	stateBall=state;
    }
    
}
