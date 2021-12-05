package test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class RubberBall extends Ball {


    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = new Color(0xa4fffc);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker();
    private static final Color DEF_INNER_COLOR_SPEED = new Color(0xff4a3c);
    private static final Color DEF_BORDER_COLOR_SPEED = new Color(0x9f362e);

    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }

    @Override
    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA / 1);
        double y = center.getY() - (radiusB / 1);

        return new Ellipse2D.Double(x,y,radiusA,radiusB);
    }

	@Override
	public void changeColorState(int state) {
		// TODO Auto-generated method stub
		switch(state) {
		case STATE_NORMAL :
			this.setBorderColor(DEF_BORDER_COLOR);
			this.setInnerColor(DEF_INNER_COLOR);
			break;
		case STATE_FIRE :
			this.setBorderColor(DEF_BORDER_COLOR_SPEED);
			this.setInnerColor(DEF_INNER_COLOR_SPEED);
			break;
		}
	}
    
    
}
