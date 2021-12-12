/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class SteelBrick extends Brick {

    private static final Color DEF_INNER = new Color(0xc1c2d5);
    private static final Color DEF_BORDER = new Color(0x9999a2);
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private final Random rnd;
    private final Shape brickFace;

    /**
     * Steel brick constructor
     * @param point coordinate of brick
     * @param size size of brick
     */
    public SteelBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }


    /**
     * Make the steel brick
     * @param pos coordinate of brick
     * @param size size of brick
     * @return type of brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * get brick type
     * @return brick type
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * To check if the brick has been impact
     * @param point coordinate of the brick
     * @param dir where the brick is impact
     * @return
     */
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    /**
     * set the impact probability of steel brick
     */
    public void impact(){
        if(rnd.nextDouble() < getSteelProbability()){
            super.impact();
        }
    }

    public double getSteelProbability(){
        return STEEL_PROBABILITY;
    }

}
