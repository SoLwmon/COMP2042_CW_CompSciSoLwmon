package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class Wall {

    private static final int LEVELS_COUNT = 10;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int INVISIBLE = 4;
    
    private Random random;
    private Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        random = new Random();

        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = Constants.SPEED;
        }while(speedX == 0);
        do{
            speedY = -Constants.SPEED;
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        //We add the power-up to a brick in the middle
        tmp[tmp.length/3+2].setPowerUp();
        tmp[tmp.length/3*2-2].setPowerUp();
        return tmp;

    }
 
    private Brick[] makeSingleTypeLevelWDecalage(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,INVISIBLE);
        }

        return tmp;
    }
 
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }

        return tmp;
    }

    private Brick[] makeTriangleLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        int brickOnLine = brickCnt / lineCnt;

        Brick[] tmp  = makeSingleTypeLevelWDecalage(drawArea, brickCnt, lineCnt, brickSizeRatio, typeA);
        for (int i = 0; i<tmp.length;i++) {
        	Brick b=tmp[i];
        	int dx = b.x/b.w;
        	int line = b.y/b.h;
        	
        	if (dx>=(brickOnLine-2*line+1)/2 && dx<=(brickOnLine+2*line-1)/2) {    
        		tmp[i]=Wall.makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeB);
        	}
        }
        
        return tmp;
    }

    private Brick[] makeAlternatedLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        Brick[] tmp  = makeSingleTypeLevelWDecalage(drawArea, brickCnt, lineCnt, brickSizeRatio, typeA);
        boolean Do = false;
        boolean changementP = tmp.length%2==0;
        int al=0;
        for (int i = 0; i<tmp.length;i++) {
        	Brick b=tmp[i];
        	int line = b.y/b.h;
        	if (changementP && al!=line) {
        		Do=!Do;
        		al=line;
        	}
        	if (Do) {
            	tmp[i]=Wall.makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeB);
        	}
        	
        	Do=!Do;

        }

        tmp[2*tmp.length/3-6].setPowerUp();
        tmp[2*tmp.length/3-4].setPowerUp();
        tmp[2*tmp.length/3-2].setPowerUp();
        
        return tmp;
    }

    private Brick[] makeCrossLevel(Rectangle drawArea, int brickCnt, int lineCnt, int lnarg, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        int brickOnLine = brickCnt / lineCnt;

        Brick[] tmp  = makeSingleTypeLevelWDecalage(drawArea, brickCnt, lineCnt, brickSizeRatio, typeA);
        boolean Do = false;
        boolean changementP = tmp.length%2==0;
        int al=0;
        for (int i = 0; i<tmp.length;i++) {
        	Brick b=tmp[i];
        	int dx = b.x/b.w;
        	int line = b.y/b.h;
        	if (changementP && al!=line) {
        		Do=!Do;
        		al=line;
        	}
        	if (line==lnarg-1 || (dx==brickOnLine/2||dx==brickOnLine/2-1)) {
            	tmp[i]=Wall.makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeB);
        	}
        	
        	Do=!Do;

        }

        tmp[2*tmp.length/3-1-2*brickOnLine].setPowerUp();
        tmp[2*tmp.length/3+2-2*brickOnLine].setPowerUp();
        tmp[2*tmp.length/3-1].setPowerUp();
        tmp[2*tmp.length/3+2].setPowerUp();

        return tmp;
    }
 
    private void addLine(Brick[] tmp, int ligne, int startP, int nbrCase, int typeA) {
        for (int i = 0; i<tmp.length;i++) {
        	Brick b=tmp[i];
        	int dx = b.x/b.w;
        	int line = b.y/b.h;
        	
        	if (line==ligne && startP<=dx && startP+nbrCase>dx) {
        		tmp[i]=Wall.makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeA);
        	}
        }
        
    }
    
    private void addPowerUp(Brick[] tmp, int ligne, int column, int lineCnt) {
        int brickOnLine = tmp.length / lineCnt;
        tmp[brickOnLine*ligne+column].setPowerUp();
        
    }
    
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,3,brickDimensionRatio,CLAY);
        tmp[1] = makeTriangleLevel(drawArea,40,4,brickDimensionRatio,CLAY,INVISIBLE);
        addPowerUp(tmp[1],0,2,4);
        addPowerUp(tmp[1],0,7,4);
        
        tmp[2] = makeChessboardLevel(drawArea,brickCount*5/3,5,brickDimensionRatio,CLAY,CEMENT);
        addPowerUp(tmp[2],3,5,5);
        addPowerUp(tmp[2],3,6,5);
        tmp[3] = makeAlternatedLevel(drawArea,40,4,brickDimensionRatio,CLAY,INVISIBLE);
        tmp[4] = makeSingleTypeLevel(drawArea,brickCount*5/3,5,brickDimensionRatio,CLAY);
        addPowerUp(tmp[4],1,2,5);
        addPowerUp(tmp[4],3,9,5);
        
        tmp[5] = makeCrossLevel(drawArea,50,5,3,brickDimensionRatio,CLAY,CEMENT);
        tmp[6] = makeTriangleLevel(drawArea,50,5,brickDimensionRatio,CLAY,INVISIBLE);
        addLine(tmp[6], 4,3,4,CEMENT);
        addLine(tmp[6], 3,4,2,CEMENT);
        addPowerUp(tmp[6],4,4,5);
        addPowerUp(tmp[6],4,5,5);
        tmp[7] = makeCrossLevel(drawArea,50,5,4,brickDimensionRatio,CLAY,INVISIBLE);
        tmp[8] = makeAlternatedLevel(drawArea,40,4,brickDimensionRatio,CLAY, CEMENT);
        addLine(tmp[8], 0,0,10,CLAY);     
        addLine(tmp[8], 3,0,10,CLAY);
        tmp[9] = makeSingleTypeLevelWDecalage(drawArea,50,5,brickDimensionRatio,CLAY);
        addLine(tmp[9], 0,0,10,CEMENT);   
        addLine(tmp[9], 1,1,8,CEMENT);     
        addLine(tmp[9], 2,2,6,CEMENT);  
        addLine(tmp[9], 3,3,4,CEMENT);  
        addLine(tmp[9], 4,4,2,CEMENT);  
        addLine(tmp[9], 0,1,8,INVISIBLE);    
        addLine(tmp[9], 1,2,6,INVISIBLE);    
        addLine(tmp[9], 2,3,4,INVISIBLE);  
        addLine(tmp[9], 3,4,2,INVISIBLE);  
        addLine(tmp[9], 0,3,4,CLAY);   
        addLine(tmp[9], 1,4,2,CLAY);   
        addPowerUp(tmp[9],0,4,5);
        addPowerUp(tmp[9],0,5,5);
        
        return tmp;
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
            brickCount--;
            if (brickCount==0) {
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
            ballCount--;
            ballLost = true;
            if (ballCount!=0)
            	Sound.playSound(4);
            else
            	Sound.playSound(2);
        }
    }

    private boolean impactWall(){
        for(Brick b : bricks){
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

                    return b.setImpact(ball.down, Brick.Crack.UP);
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
                    return b.setImpact(ball.up,Brick.Crack.DOWN);
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
	                    return b.setImpact(ball.right,Brick.Crack.RIGHT);
                	
                case Brick.RIGHT_IMPACT:
	                    ball.reverseX();
                        if (b.containsPowerUp) {
                        	ball.changeState(Ball.STATE_FIRE);
                        	Sound.playSound(1);
                        }
                        else {
                          	Sound.playSound(0);
                        }
	                    return b.setImpact(ball.left,Brick.Crack.LEFT);
                	
            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public int getBrickCount(){
        return brickCount;
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = Constants.SPEED;
        }while(speedX == 0);
        do{
            speedY = -Constants.SPEED;
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;
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

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
    	System.out.println("next level");
        bricks = levels[level++];
    	brickCount=0;
        for(Brick b : bricks) {
        	if (b.collisionable) {
        		brickCount++;
        	}
            b.repair();
        }
        GameBoard.initialNbrBal=brickCount;
        
    }

    public boolean hasLevel(){
        return level < levels.length;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    private static Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            case INVISIBLE :
            	out = new InvisibleBrick(point, size);
            	break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

}
