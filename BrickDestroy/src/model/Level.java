package model;

import java.awt.*;
import view.GameBoard;

public class Level {

    private static final int LEVELS_COUNT = 10;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int INVISIBLE = 4;

    private final Brick[][] levels;
    private int level;
    Wall wall;

    /**
     * Level constructor
     * @param drawArea size of the level
     * @param brickCount number of the brick
     * @param brickDimensionRatio size of the brick
     * @param wall wall class
     */
    public Level(Rectangle drawArea, int brickCount, double brickDimensionRatio,Wall wall){

        levels = makeLevels(drawArea,brickCount,brickDimensionRatio);
        level = 0;
        this.wall = wall;
    }

    /**
     * Determine the brick type
     * @param point coordinate of brick
     * @param size size of the brick
     * @param type type of brick
     * @return
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out = switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case STEEL -> new SteelBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            case INVISIBLE -> new InvisibleBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
        return  out;
    }

    /**
     * Add number of line of brick
     * @param tmp length of the line
     * @param ligne the number of line
     * @param startP start point of the line
     * @param nbrCase number case
     * @param typeA type of brick
     */
    private void addLine(Brick[] tmp, int ligne, int startP, int nbrCase, int typeA) {
        for (int i = 0; i<tmp.length;i++) {
            Brick b=tmp[i];
            int dx = b.x/b.w;
            int line = b.y/b.h;

            if (line==ligne && startP<=dx && startP+nbrCase>dx) {
                tmp[i]= makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeA);
            }
        }

    }

    /**
     * Add power up in the brick
     * @param tmp level
     * @param line number of row brick
     * @param column number of column  brick
     * @param lineCnt line count
     */
    private void addPowerUp(Brick[] tmp, int line, int column, int lineCnt) {
        int brickOnLine = tmp.length / lineCnt;
        tmp[brickOnLine*line+column].setPowerUp();

    }

    /**
     * organize the level
     * @param drawArea size of the level
     * @param brickCount number of brick
     * @param brickDimensionRatio brick dimension ratio
     * @return level with different design
     */
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,double brickDimensionRatio){
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

    /**
     * go to next level
     */
    public void nextLevel(){
        System.out.println("next level");
        wall.setBricks(levels[level++]);
        wall.setBrickCount(0);
        for(Brick b : wall.getBricks()) {
            if (b.collisional) {
                wall.setBrickCount(wall.getBrickCount()+1);
            }
            b.repair();
        }
        GameBoard.initialNbrBal = wall.getBrickCount();

    }

    /**
     * check if level exist
     * @return level existing
     */
    public boolean hasLevel(){
        return level < levels.length;
    }

    /**
     * Make single type level
     * @param drawArea size of the level
     * @param brickCnt number of brick
     * @param lineCnt number of the line
     * @param brickSizeRatio size of the brick
     * @param type type of the brick
     * @return level type
     */
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

    /**
     * Make single type level with large size
     * @param drawArea size of level
     * @param brickCnt number of brick
     * @param lineCnt number of line
     * @param brickSizeRatio size of brick
     * @param type type of brick
     * @return level type
     */
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

    /**
     * Make chessboard type level
     * @param drawArea size of level
     * @param brickCnt number of brick
     * @param lineCnt number of line
     * @param brickSizeRatio size of brick
     * @param typeA type of brick
     * @return level type
     */
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

    /**
     * Make triangle type level
     * @param drawArea size of level
     * @param brickCnt number of brick
     * @param lineCnt number of line
     * @param brickSizeRatio size of brick
     * @param typeA type of brick
     * @param typeB type of brick
     * @return level type
     */
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
                tmp[i] = makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeB);
            }
        }

        return tmp;
    }

    /**
     * Make alternate level type
     * @param drawArea size of level
     * @param brickCnt number of brick
     * @param lineCnt number of line
     * @param brickSizeRatio size of the brick
     * @param typeA type of brick
     * @param typeB type of brick
     * @return type of level
     */
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
                tmp[i]= makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeB);
            }

            Do=!Do;

        }

        tmp[2*tmp.length/3-6].setPowerUp();
        tmp[2*tmp.length/3-4].setPowerUp();
        tmp[2*tmp.length/3-2].setPowerUp();

        return tmp;
    }

    /**
     * Make cross type level
     * @param drawArea size of the level
     * @param brickCnt number of brick
     * @param lineCnt number of line
     * @param lnarg line to compare to lineCnt
     * @param brickSizeRatio size of brick
     * @param typeA type of brick
     * @param typeB type of brick
     * @return type of level
     */
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
                tmp[i] = makeBrick(new Point(b.x,b.y), new Dimension(b.w, b.h),typeB);
            }

            Do=!Do;

        }

        tmp[2*tmp.length/3-1-2*brickOnLine].setPowerUp();
        tmp[2*tmp.length/3+2-2*brickOnLine].setPowerUp();
        tmp[2*tmp.length/3-1].setPowerUp();
        tmp[2*tmp.length/3+2].setPowerUp();

        return tmp;
    }
}
