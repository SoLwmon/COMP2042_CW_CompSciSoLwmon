package test;

import static test.Constants.COLOR_BUTTON_E1;
import static test.Constants.COLOR_BUTTON_E2;
import static test.Constants.CREDITS;
import static test.Constants.GAME_TITLE;
import static test.Constants.GREETINGS;
import static test.Constants.INFO_BUTTON_E1;
import static test.Constants.INFO_BUTTON_E2;
import static test.Constants.MENU_TEXT;
import static test.Constants.BACK_MENU_TEXT;
import static test.Constants.START_TEXT;
import static test.Constants.TEXT_COLOR;
import static test.Constants.COLOR_INFO_TEXT;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;

public class InfoBoard extends JComponent implements MouseListener, MouseMotionListener {

    private Rectangle menuFace;
    private Rectangle infoContent;
    private Rectangle infoContentShadow;
    private Rectangle menuButton;

    private Font buttonFont;
    private Font titleFont;
    private Font textFont;
    
    private GameFrame owner;

    private boolean menuClicked;

    public InfoBoard(GameFrame owner,Dimension area){
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);
        
        Dimension btnDim = new Dimension(area.width / 3, area.height / 18);
        menuButton = new Rectangle(btnDim);
        
        infoContent = new Rectangle(new Point(30,30), new Dimension(area.width-60, area.height-65));
        infoContentShadow = new Rectangle(new Point(30,30), new Dimension(area.width-60, area.height-60));
  
        try {
			setFonts();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void setFonts() throws FontFormatException, IOException
    {
        buttonFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(15f);
        titleFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(17f);        
        textFont = Font.createFont(Font.PLAIN, new File("Fonts\\inter_regular.otf")).deriveFont(12f);
    }

    public void paint(Graphics g){
        drawInfo((Graphics2D)g);
    }


    public void drawInfo(Graphics2D g2d){
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
        drawText(g2d);
        
        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(Constants.BG_COLOR);
        g2d.fill(menuFace);
        g2d.setColor(Constants.COLOR_BOX_INFO_SHADOW);
        g2d.fill(infoContentShadow);
        g2d.setColor(Constants.COLOR_BOX_INFO);
        g2d.fill(infoContent);

        g2d.setColor(prev);
    }
	public static void drawAlignString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + metrics.getAscent() + 3;
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
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
	
	private void drawText(Graphics2D g2d) {
		int x = infoContent.x+15;
		int y = infoContent.y+10;
		
		//drawTitle
		g2d.setColor(COLOR_INFO_TEXT);
		drawAlignString(g2d,Constants.INFORMATION_TITLE, new Rectangle(x, y,0,0), titleFont);
		y+=30;
		drawMultilineString(g2d,Constants.INFORMATION_TEXT, x, y);
	}
	
	void drawMultilineString(Graphics2D g, String text, int x, int y) {
		g.setFont(textFont);
	    FontMetrics metrics = g.getFontMetrics(textFont);
	    int lineHeight = metrics.getHeight();
	    y+=metrics.getAscent();
	    for (String line : text.split("\n")) {
	    	g.drawString(line, x, y += lineHeight);
	    }
	        
	}
	
    private void drawButton(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = infoContent.x+10;
        int y = infoContent.x+infoContent.height-menuButton.height-10;
        menuButton.setLocation(x,y);
        
        drawButtonOne(g2d,BACK_MENU_TEXT,x,y,menuButton.width,menuButton.height,menuClicked);

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
  
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(menuButton.contains(p)){
            Sound.playSound(0);
        	owner.enableMenuBoardFromInfoBoard();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
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
        if(menuButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}