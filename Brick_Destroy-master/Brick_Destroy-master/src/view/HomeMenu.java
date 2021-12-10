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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

import static test.Constants.*;


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {



    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle menuButton;
    private Rectangle infoButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean infoClicked;

    public HomeMenu(GameFrame owner,Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 9);
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        Dimension btnDimInfo = new Dimension(area.height/9, area.height/9);
        infoButton = new Rectangle(btnDimInfo);
        
        setFonts();
    }

    public void setFonts()
    {
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Noto Mono",Font.PLAIN,10);
        try {
			buttonFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(20f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.drawImage(Resources.backgroundMenu, 0, 0, null);
        Stroke tmp = g2d.getStroke();

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        setArea(greetingsRect,gameTitleRect,creditsRect,g2d);

    }

    public void setArea(Rectangle2D greetingsRect, Rectangle2D gameTitleRect, Rectangle2D creditsRect, Graphics2D g2d)
    {
        int sX,sY;
        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);
    }
	
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + 3;
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
    private void drawButton(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.65);
        startButton.setLocation(x,y);
        
        drawButtonOne(g2d,START_TEXT,x,y,startButton.width,startButton.height,startClicked);
        
        y += 15 + startButton.height;
        drawButtonOne(g2d,MENU_TEXT,x,y,menuButton.width,menuButton.height,menuClicked);
        menuButton.setLocation(x,y);
        
        x=menuFace.width-infoButton.width-20;
        y=menuFace.height-infoButton.height-20;
        infoButton.setLocation(x,y);
        drawButtonInfo(g2d,x,y,infoButton.width, infoButton.height, infoClicked);
    }

    private void drawButtonOne(Graphics2D g2d, String text, int x, int y, int w, int h, boolean clicked) {
    	RoundRectangle2D rd1 = new RoundRectangle2D.Float(x,y,w,h,5,5);
    	RoundRectangle2D rd2 = new RoundRectangle2D.Float(x,y,w,h+6,5,5);
    	
    	int yfont;
    	int wfont;
    	if (!clicked) {
        	rd1 = new RoundRectangle2D.Float(x,y,w,h,5,5);
        	rd2 = new RoundRectangle2D.Float(x,y,w,h+6,5,5);
        	yfont=y;
        	wfont=h;
    	}
    	else {
        	rd1 = new RoundRectangle2D.Float(x,y+5,w,h-3,5,5);
        	rd2 = new RoundRectangle2D.Float(x,y+5,w,h-5+6,5,5);
        	yfont=y+5;
        	wfont=h-3;
    	}
    	
    	yfont-=2;
    	
		g2d.setColor(COLOR_BUTTON_E2);
		g2d.fill(rd2);
		g2d.setColor(COLOR_BUTTON_E1);
		g2d.fill(rd1);
		g2d.setColor(TEXT_COLOR);
		drawCenteredString(g2d,text,new Rectangle(x,yfont,w,wfont),buttonFont);

    	
    }
  
    private void drawButtonInfo(Graphics g2d, int x, int y, int w, int h, boolean clicked) {
    	int yfont;
    	int wfont;
    	if (!clicked) {
    		g2d.setColor(INFO_BUTTON_E2);
    		g2d.fillOval(x,y,w,h+3);
    		g2d.setColor(INFO_BUTTON_E1);
    		g2d.fillOval(x,y,w,h);
    		g2d.setColor(TEXT_COLOR);
    		yfont=y;
    		wfont=h;
    	}
    	else {
    		g2d.setColor(INFO_BUTTON_E1);
    		g2d.fillOval(x,y+5,w,h);
    		g2d.setColor(TEXT_COLOR);
    		yfont=y+5;
    		wfont=h-3;
    	}
    	yfont-=2;
		drawCenteredString(g2d,"!",new Rectangle(x,yfont,w,wfont),buttonFont);

    }
    
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();

        }
        else if(menuButton.contains(p)){
            System.exit(0);
        }
        else if(infoButton.contains(p)){
        	owner.enableInfoBoard();
        }
    }
   

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();

        if(startButton.contains(p)){
            Sound.playSound(0);
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(menuButton.contains(p)){
            Sound.playSound(0);
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if(infoButton.contains(p)) {
            Sound.playSound(0);
            infoClicked = true;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if (infoClicked) {
            infoClicked = false;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || menuButton.contains(p) ||infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
