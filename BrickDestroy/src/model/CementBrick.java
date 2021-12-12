package model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {

    private static final Color DEF_INNER = new Color(0xc9c9d4);
    private static final Color DEF_BORDER = new Color(0x9d9daf);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    /**
     * Cement brick constructor
     * @param point coordinate of the brick
     * @param size size of the brick
     */
    public CementBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * Create a cement brick
     * @param pos coordinate of the brick
     * @param size size of the brick
     * @return cement brick structure
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * To determine the brick has been crack by the ball
     * @param point coordinate of the brick
     * @param dir where the brick is impact
     * @return the state of the brick that is broken or not
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir, brickFace.getBounds());
            updateBrick();
            return false;
        }
        return true;
    }


    /**
     * Get the type of the brick
     * @return The cement brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Update the crack on the brick when it is impact by the ball
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.getBrickFace(),false);
            brickFace = gp;
        }
    }

    /**
     * Repair brick
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }
}
