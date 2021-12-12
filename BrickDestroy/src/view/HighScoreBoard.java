package view;


import controller.HighScoreHandler;
import model.*;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

public class HighScoreBoard extends JComponent implements MouseListener, MouseMotionListener, KeyListener {

    public static final Color COLOR_BOX_INFO = new Color(0xb2b0dd);
    public static final Color COLOR_BOX_INFO_SHADOW = new Color(0x8380bb);
    public static final Color COLOR_BOX_SCORE = new Color(0xEFEFFE);
    public static final Color COLOR_BOX_SCORE_SHADOW = new Color(0xDDDCEE);
    public static final Color COLOR_INFO_TEXT  = new Color(0x1A1933);
    public static final Color COLOR_BUTTON_E1 = new Color(0x2c277d);
    public static final Color COLOR_BUTTON_E2 = new Color(0x3b3698);
    public static final Color TEXT_COLOR = new Color(0xFFFFFF);

    public static final String START_TEXT = "Start";
    public static final String MENU_TEXT = "Exit";
    public static final String SCORE_TITLE = "SCORE LIST";
    public static final String SCORE_TEXT = "Use W/S to scroll through the score list";
    public static final String NEXT_TEXT = "NEXT";
    public static final String BACK_MENU_TEXT = "BACK TO MENU";
    public static final Color BG_COLOR = new Color(0x0a0838);

    private Rectangle menuFace;
    private Rectangle infoContent;
    private Rectangle infoContentShadow;
    private Rectangle scoreContent;
    private Rectangle scoreContentShadow;
    private Rectangle menuButton;
    private Rectangle menu1Button;

    private Font buttonFont;
    private Font titleFont;
    private Font textFont;
    
    private GameFrame owner;

    private boolean menuClicked;
    private boolean menu1Clicked;
    private String title;
    
    private List<String> listeScore = new LinkedList<>();
    private int cameray = 0;

    /**
     * High score constructor
     * @param owner game frame
     * @param area area of the high score
     */
    public HighScoreBoard(GameFrame owner,Dimension area){
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);
        
        Dimension btnDim = new Dimension(250 / 3, 300 / 18);
        menuButton = new Rectangle(btnDim);
        menu1Button = new Rectangle(new Dimension(450 / 3, 300 / 18));
         
        infoContent = new Rectangle(new Point(30,30), new Dimension(area.width-60, area.height-65));
        infoContentShadow = new Rectangle(new Point(30,30), new Dimension(area.width-60, area.height-60));
        scoreContent = new Rectangle(new Point(45,110), new Dimension(area.width-90, area.height-195));
        scoreContentShadow = new Rectangle(new Point(45,110), new Dimension(area.width-90, area.height-190));
  
        try {
			setFonts();
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * add score
     * @param point score point
     */
    public void addScore(int point) {
    	HighScoreHandler.saveScore(String.valueOf(point));
    }

    /**
     * get score
     */
    public void getScores() {
    	listeScore = HighScoreHandler.read();
    	Collections.sort(listeScore);
    }

    /**
     * set title score
     * @param arg String
     */
    public void setTitle(String arg) {
    	title = arg + " - " + SCORE_TITLE;
    }

    /**
     * Set font type
     * @throws FontFormatException error found
     * @throws IOException error found
     */
    public void setFonts() throws FontFormatException, IOException
    {
        buttonFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(15f);
        titleFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(17f);        
        textFont = Font.createFont(Font.PLAIN, new File("Fonts\\inter_regular.otf")).deriveFont(12f);
    }
    public void paint(Graphics g){
        drawInfo((Graphics2D)g);
    }

    /**
     * draw info on score board
     * @param g2d graphic
     */
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

    /**
     * To draw the container for the highscore board
     * @param g2d graphic
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);
        g2d.setColor(COLOR_BOX_INFO_SHADOW);
        g2d.fill(infoContentShadow);
        g2d.setColor(COLOR_BOX_INFO);
        g2d.fill(infoContent);
        g2d.setColor(COLOR_BOX_SCORE_SHADOW);
        g2d.fill(scoreContentShadow);
      
        g2d.setColor(COLOR_BOX_SCORE);
        g2d.fill(scoreContent);
        drawScore(g2d);
        g2d.setColor(prev);
    }

    /**
     * To draw score on board
     * @param g2d graphic
     */
    private void drawScore(Graphics2D g2d) {
    	String txt = "";
    	String txtNum = "";
    	int i = 0;
    	for (String t : listeScore) {
    		i++;
    		txt+=t +"\n";
    		txtNum+=i + ")" + "\n";
    	}
    	g2d.setColor(COLOR_INFO_TEXT);
    	BufferedImage img = new BufferedImage(scoreContent.width,scoreContent.height,BufferedImage.TYPE_INT_ARGB);
    	Graphics2D gimg = (Graphics2D)img.createGraphics();
    	gimg.setFont(textFont);
    	gimg.setColor(COLOR_INFO_TEXT);
    	drawMultilineString(gimg,txtNum,20, -5-cameray);
    	drawMultilineString(gimg,txt,60, -5-cameray);
    	
    	g2d.drawImage(img, scoreContent.x, scoreContent.y, null);
    }

    /**
     *  To draw align string
     * @param g graphic
     * @param text text type
     * @param rect rectangle size
     * @param font font type
     */
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

    /**
     * TO draw center string
     * @param g graphic
     * @param text text type
     * @param rect rectangle size
     * @param font font type
     */
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + 3;
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}

    /**
     * To draw text
     * @param g2d graphic
     */
	private void drawText(Graphics2D g2d) {
		int x = infoContent.x+15;
		int y = infoContent.y+10;
		
		//drawTitle
		g2d.setColor(COLOR_INFO_TEXT);
		drawAlignString(g2d,title, new Rectangle(x, y,0,0), titleFont);
		y+=30;
		drawMultilineString(g2d,SCORE_TEXT, x, y);
	}

    /**
     * To draw multi line string
     * @param g graphic
     * @param text text type
     * @param x x axis
     * @param y y avis
     */
	void drawMultilineString(Graphics2D g, String text, int x, int y) {
		g.setFont(textFont);
	    FontMetrics metrics = g.getFontMetrics(textFont);
	    int lineHeight = metrics.getHeight();
	    y+=metrics.getAscent();
	    for (String line : text.split("\n")) {
	    	g.drawString(line, x, y += lineHeight);
	    }
	        
	}

    /**
     * To draw button
     * @param g2d graphic
     */
    private void drawButton(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = infoContent.x+10;
        int y = infoContent.y+infoContent.height-menuButton.height-10;
        menuButton.setLocation(x,y);
        
        drawButtonOne(g2d,NEXT_TEXT,x,y,menuButton.width,menuButton.height,menuClicked);
        
        x = infoContent.x+20+menuButton.width;
        y = infoContent.y+infoContent.height-menuButton.height-10;
        menu1Button.setLocation(x,y);
        
        drawButtonOne(g2d,BACK_MENU_TEXT,x,y,menu1Button.width,menu1Button.height,menu1Clicked);

    }

    /**
     * To draw button
     * @param g2d graphic
     * @param text type text
     * @param x x axis
     * @param y y axis
     * @param w width
     * @param h height
     * @param clicked mouse click
     */
    private void drawButtonOne(Graphics2D g2d, String text, int x, int y, int w, int h, boolean clicked) {
    	RoundRectangle2D rd1;
    	RoundRectangle2D rd2;
    	
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
        	owner.enableGameBoardFromScoreBoard();
        }         
        else if(menu1Button.contains(p)) {
            Sound.playSound(0);
            owner.enableMenuBoardFromHighScoreBoard();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if(menu1Button.contains(p)) {
            menu1Clicked = true;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if(menu1Clicked) {
            menu1Clicked = false;
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
        if(menuButton.contains(p) || menu1Button.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
        switch(keyEvent.getKeyCode()){
        
        case KeyEvent.VK_W:
        	if (cameray>0)
        	cameray-=5;
        	repaint();
            break;
        case KeyEvent.VK_S:
        	if (cameray<this.listeScore.size()*5)
        		cameray+=5;
        	repaint();
            break;
    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
