package test;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;


/**
 * Created by filippo on 04/09/16.
 *
 */
public class ClayBrick extends Brick {
	static final Color[] COLOR_INNER =  new Color[] {new Color(0xff0036),new Color(0xfd8317),new Color(0xe3c900), new Color(0x34c61d), new Color(0x4591da)};
	static final Color[] COLOR_BORDER =  new Color[] {new Color(0xc00036),new Color(0xd86500),new Color(0xc8af00), new Color(0x26a812), new Color(0x2879c9)};
	
    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    public ClayBrick(Point point, Dimension size){
    	
        super(NAME,point,size,(int)(point.y/size.height)>4?COLOR_BORDER[0]:COLOR_BORDER[(int)(point.y/size.height)].darker(),(int)(point.y/size.height)>4?COLOR_INNER[0]:COLOR_INNER[(int)(point.y/size.height)],CLAY_STRENGTH);

    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new RoundRectangle2D.Float(pos.x,pos.y,size.width, size.height, 5,5);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
