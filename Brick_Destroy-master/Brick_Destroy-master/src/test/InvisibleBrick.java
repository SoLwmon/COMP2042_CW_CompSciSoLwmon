package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public class InvisibleBrick extends Brick {
    private static final String NAME = "Invisible Brick";
    private static final Color DEF_INNER = new Color(0xc9c9d4);
    private static final Color DEF_BORDER = new Color(0x9d9daf);
    private static final int INVISIBLE_STRENGTH = 2;
    
	public InvisibleBrick(Point point, Dimension size) {
        super(NAME,point,size,DEF_BORDER,DEF_INNER,INVISIBLE_STRENGTH);
		collisionable=false;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Shape makeBrickFace(Point pos, Dimension size) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Shape getBrick() {
		// TODO Auto-generated method stub
		return null;
	}

}
