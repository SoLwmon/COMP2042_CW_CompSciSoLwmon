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
