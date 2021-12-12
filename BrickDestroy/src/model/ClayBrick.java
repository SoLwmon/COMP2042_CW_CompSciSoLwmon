package model;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by filippo on 04/09/16.
 *
 */
public class ClayBrick extends Brick {
	static final Color[] COLOR_INNER =  new Color[] {new Color(0xff0036),new Color(0xfd8317),new Color(0xe3c900), new Color(0x34c61d), new Color(0x4591da)};
	static final Color[] COLOR_BORDER =  new Color[] {new Color(0xc00036),new Color(0xd86500),new Color(0xc8af00), new Color(0x26a812), new Color(0x2879c9)};

    private static final int CLAY_STRENGTH = 1;

    /**
     * Clay Brick Constructor
     * @param point
     * @param size
     */
    public ClayBrick(Point point, Dimension size){
    	
        super(point,size, (point.y/size.height) >4?COLOR_BORDER[0]:COLOR_BORDER[point.y/size.height].darker(), (point.y/size.height) >4?COLOR_INNER[0]:COLOR_INNER[point.y/size.height],CLAY_STRENGTH);

    }

    /**
     * Create a clay brick
     * @param pos coordinate of the brick
     * @param size size of the brick
     * @return clay brick structure
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new RoundRectangle2D.Float(pos.x,pos.y,size.width, size.height, 5,5);
    }

    /**
     * Get the type of the brick
     * @return
     */
    @Override
    public Shape getBrick() {
        return super.getBrickFace();
    }


}
