package model;

import java.awt.*;
import java.awt.geom.Point2D;


public class Wall {

    private Brick[] bricks;
    private int brickCount = 0;
    private int ballCount;
    private boolean ballLost;
    private int point;

    public static final int SPEED = 4;
    private final Rectangle area;

    public Ball ball;
    public Player player;


    private final Point startPoint;

    /**
     * Wall constructor
     * @param drawArea size of level
     * @param ballPos position of the ball at the start
     */
    public Wall(Rectangle drawArea, Point ballPos){

        this.startPoint = new Point(ballPos);
        ballCount = 3;
        ballLost = false;
        point = 0;

        makeBall(ballPos);
        int speedX,speedY;
        speedX = SPEED;
        speedY = -SPEED;

        ball.setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }

    /**
     *  make the ball
     * @param ballPos position of the ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * movement of the paddle and the ball
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * TO find the impact of the ball
     */
    public void findImpacts(){

        if(player.impact(ball)){
        	Sound.playSound(0);
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            setBrickCount(getBrickCount()-1);
            setPoint(getPoint()+20);
            if (getBrickCount()==0) {
            	Sound.playSound(3);
            }
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            setBallCount(getBallCount()-1);
            setBallLost(true);
            if (getBallCount() != 0)
            	Sound.playSound(4);
            else
            	Sound.playSound(2);
        }
    }

    /**
     *  CHeck to see if the wall have been impact
     * @return true if the ball impact the wall
     */
    public boolean impactWall(){
        for(Brick b : getBricks()){
            //Vertical Impact
            //Horizontal Impact
            if (b.collisional)
                switch (b.findImpact(ball)) {
                    case Brick.UP_IMPACT -> {
                        if (ball.getState() == Ball.STATE_NORMAL || b.getStrength() > 1) {
                            ball.reverseY();
                            if (b.containsPowerUp) {
                                ball.changeState(Ball.STATE_FIRE);
                                Sound.playSound(1);
                            } else {
                                Sound.playSound(0);
                            }
                        }
                        return b.setImpact(ball.down, Crack.getUP());
                    }
                    case Brick.DOWN_IMPACT -> {
                        if (ball.getState() == Ball.STATE_NORMAL || b.getStrength() > 1) {
                            ball.reverseY();
                            if (b.containsPowerUp) {
                                ball.changeState(Ball.STATE_FIRE);
                                Sound.playSound(1);
                            } else {
                                Sound.playSound(0);
                            }
                        }
                        return b.setImpact(ball.up, Crack.getDOWN());
                    }
                    case Brick.LEFT_IMPACT -> {
                        ball.reverseX();
                        if (b.containsPowerUp) {
                            ball.changeState(Ball.STATE_FIRE);
                            Sound.playSound(1);
                        } else {
                            Sound.playSound(0);
                        }
                        return b.setImpact(ball.right, Crack.getRIGHT());
                    }
                    case Brick.RIGHT_IMPACT -> {
                        ball.reverseX();
                        if (b.containsPowerUp) {
                            ball.changeState(Ball.STATE_FIRE);
                            Sound.playSound(1);
                        } else {
                            Sound.playSound(0);
                        }
                        return b.setImpact(ball.left, Crack.getLEFT());
                    }
                }
        }
        return false;
    }

    /**
     * check the impact of border
     * @return true if the ball impact the border
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * Reset the ball to its original position
     */
    public void ballReset(){
        int speedX,speedY;
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        speedX = getSpeed();
        speedY = -getSpeed();

        ball.setSpeed(speedX,speedY);
        setBallLost(false);
    }

    /**
     * Reset the wall
     */
    public void wallReset(){
        setBrickCount(0);
        for(Brick b : bricks) {
            if (b.collisional) {
                setBrickCount(getBrickCount()+1);
            }
            b.repair();
        }
        setBallCount(3);
    }

    /**
     * Reset the ball count
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * To check the end of the game
     * @return the ball count
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * To check the end of the level
     * @return the ball count
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * To check the player live
     * @return the decrease live
     */
    public boolean getBallLost(){
        return ballLost;
    }

    public void setBallXSpeed(int s){
        ball.setSpeedX(s);
    }

    public void setBallYSpeed(int s){
        ball.setSpeedY(s);
    }

    public int getBrickCount(){
        return brickCount;
    }

    public int getBallCount(){
        return ballCount;
    }

    public Brick[] getBricks(){
        return bricks;
    }

    public void setBrickCount(int brickCount){
        this.brickCount = brickCount;
    }

    public void setBallCount(int ballCount){
        this.ballCount = ballCount;
    }

    public void setBallLost(boolean ballLost){
        this.ballLost = ballLost;
    }

    public void setBricks(Brick[] bricks){
        this.bricks = bricks;
    }

    public void setPoint(int point){
        this.point = point;
    }

    public int getPoint(){
        return point;
    }

    public int getSpeed(){
        return SPEED;
    }

    public void resetPoint(){
        point = 0;
    }

}
