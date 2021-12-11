package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class Wall {

    private Brick[] bricks;
    private int brickCount = 0;
    private int ballCount;
    private boolean ballLost;
    private int point;

    public static final int SPEED = 4;
    private Random random;
    private Rectangle area;

    public Ball ball;
    public Player player;


    private Point startPoint;

    public Wall(Rectangle drawArea, Point ballPos){

        this.startPoint = new Point(ballPos);
        ballCount = 3;
        ballLost = false;
        point = 0;
        random = new Random();

        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = SPEED;
        }while(speedX == 0);
        do{
            speedY = -SPEED;
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public void move(){
        player.move();
        ball.move();
    }

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

    public boolean impactWall(){
        for(Brick b : getBricks()){
        	if (b.collisionable)
            switch(b.findImpact(ball)) {

                //Vertical Impact
                case Brick.UP_IMPACT:
                	if (ball.getState()==Ball.STATE_NORMAL||b.strength>1) {
                        ball.reverseY();
                        if (b.containsPowerUp) {
                        	ball.changeState(Ball.STATE_FIRE);
                        	Sound.playSound(1);
                        }
                        else {
                          	Sound.playSound(0);
                        }
                	}

                    return b.setImpact(ball.down, Crack.getUP());
                case Brick.DOWN_IMPACT:
                	if (ball.getState()==Ball.STATE_NORMAL||b.strength>1) {
	                    ball.reverseY();
                        if (b.containsPowerUp) {
                        	ball.changeState(Ball.STATE_FIRE);
                        	Sound.playSound(1);
                        }
                        else {
                          	Sound.playSound(0);
                        }
                	}
                    return b.setImpact(ball.up,Crack.getDOWN());
                //Horizontal Impact
                case Brick.LEFT_IMPACT:

	                    ball.reverseX();
                        if (b.containsPowerUp) {
                        	ball.changeState(Ball.STATE_FIRE);
                        	Sound.playSound(1);
                        }
                        else {
                          	Sound.playSound(0);
                        }
	                    return b.setImpact(ball.right,Crack.getRIGHT());

                case Brick.RIGHT_IMPACT:
	                    ball.reverseX();
                        if (b.containsPowerUp) {
                        	ball.changeState(Ball.STATE_FIRE);
                        	Sound.playSound(1);
                        }
                        else {
                          	Sound.playSound(0);
                        }
	                    return b.setImpact(ball.left,Crack.getLEFT());

            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = SPEED;
        }while(speedX == 0);
        do{
            speedY = -SPEED;
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        setBallLost(false);
    }

    public void wallReset(){
        brickCount=0;
        for(Brick b : bricks) {
            if (b.collisionable) {
                brickCount++;
            }
            b.repair();
        }

        ballCount = 3;
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
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

    public void resetPoint(){
        point = 0;
    }

}
