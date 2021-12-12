package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;

public class InvisibleBrick extends Brick {
    private static final Color DEF_INNER = new Color(0xc9c9d4);
    private static final Color DEF_BORDER = new Color(0x9d9daf);
    private static final int INVISIBLE_STRENGTH = 2;

	/**
	 * Invisible brick constructor
	 * @param point
	 * @param size
	 */
	public InvisibleBrick(Point point, Dimension size) {
        super(point,size,DEF_BORDER,DEF_INNER,INVISIBLE_STRENGTH);
		collisional =false;
	}

	/**
	 * Get the type of the brick
	 * @param pos coordinate of brick
	 * @param size size of brick
	 * @return invisible brick
	 */
	@Override
	protected Shape makeBrickFace(Point pos, Dimension size) {
		return null;

	}

	@Override
	public Shape getBrick() {
		return null;
	}

}
