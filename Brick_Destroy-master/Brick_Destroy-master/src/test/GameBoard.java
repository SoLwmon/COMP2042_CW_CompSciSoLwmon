package test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.IOException;

import static test.Constants.*;


public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

	private boolean keyRightPressed;
	private boolean keyLeftPressed;
	
	private GameFrame owner;
	
    public Timer gameTimer;

    private Wall wall;

    private String message;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private Rectangle messageRect;
    private Rectangle messageRect2;
    private Rectangle scoreRect;
    
    private int strLen;

    private DebugConsole debugConsole;
    private Font buttonFont;
    private Font titleFont;
    private Font textFont;
    public static int initialNbrBal;

    public GameBoard(GameFrame _owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        owner=_owner;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);

        this.initialize();
        message = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));
        messageRect = new Rectangle(DEF_WIDTH/2,150,0,0);
        messageRect2 = new Rectangle(DEF_WIDTH/2+50,150,0,0);
        scoreRect = new Rectangle(DEF_WIDTH-20,DEF_HEIGHT-20,0,0);
        
        debugConsole = new DebugConsole(owner,wall,this);
        //initialize the first level
        wall.nextLevel();
        try {
			setFonts();
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
        gameTimer = new Timer(10,e ->{
        	if (keyLeftPressed==true) {
        		wall.player.moveLeft();
        	}
        	else if (keyRightPressed==true) {
        		wall.player.movRight();
        	}
        	else {
        		wall.player.stop();
        	}
            wall.move();
            wall.findImpacts();
            message = "Bricks :	" + wall.getBrickCount() + "\n Balls :" + wall.getBallCount();
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    endround(false);
                    wall.wallReset();
                    message = "Game over";
                }
                wall.ballReset();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    endround(true);
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    endround(true);
                }
            }

            repaint();
        });

    }
    public void setFonts() throws FontFormatException, IOException
    {
        buttonFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(20f);
        titleFont = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont((int)(20*1.1));        
        textFont = Font.createFont(Font.PLAIN, new File("Fonts\\inter_regular.otf")).deriveFont((int)(20*0.8));
    }
    
    public void endround(boolean cas) {
    	gameTimer.stop();
    	owner.enableScoreBoardFromGameBoard(cas,wall.getBrickCount(), wall.getBallCount());
    	keyLeftPressed=false;
    	keyRightPressed=false;
    	
    }
    
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.player,g2d);
        drawMessage(g2d);
        g.setColor(Color.white);
        drawHRightAlignMultiline(g2d,"Score : " + String.valueOf((initialNbrBal-wall.getBrickCount())*20), scoreRect, buttonFont);
        if(showPauseMenu)
            drawMenu(g2d);

        
        Toolkit.getDefaultToolkit().sync();
    }
    
	public static void drawHRightAlignMultiline(Graphics2D g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text));
	    // Determine the Y coordinate for the text
	    int y = rect.y - metrics.getHeight() - metrics.getAscent();
	    // Set the font
	    // Draw the String
	    drawMultilineString(g,text, x, y, font);
	}
	
	public static void drawHCenteredRightAlignMultiline(Graphics2D g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + 3;
	    // Set the font
	    // Draw the String
	    drawMultilineString(g,text, x, y, font);
	}
	
	public static void drawMultilineString(Graphics2D g, String text, int x, int y, Font f) {
		g.setFont(f);
	    FontMetrics metrics = g.getFontMetrics(f);
	    int lineHeight = metrics.getHeight();
	    y+=metrics.getAscent();
	    for (String line : text.split("\n")) {
	    	g.drawString(line, x, y += lineHeight);
	    }
	        
	}
	
    private void drawMessage(Graphics2D g2d) {
    	g2d.setColor(Color.white);
    	if (!message.equals(""))
    	if (message.charAt(0)==('B')) {
        	drawHCenteredRightAlignMultiline(g2d,message.trim(),messageRect2,buttonFont);
    	}
    	else {
        	drawHCenteredRightAlignMultiline(g2d,message.trim(),messageRect,buttonFont);
    	}
   }
    
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR_Board);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
    	if (brick.collisionable) {
            Color tmp = g2d.getColor();

            g2d.setColor(brick.getInnerColor());
            g2d.fill(brick.getBrick());

            g2d.setColor(brick.getBorderColor());
            g2d.draw(brick.getBrick());

            if (brick.containsPowerUp) {
            	drawPowerUp(g2d,brick);
            }
            
            g2d.setColor(tmp);
    	}

    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }
    
    private void drawPowerUp(Graphics2D g2d, Brick b) {
    	g2d.setColor(Constants.COLOR_FIRE_1);
    	g2d.fill(b.getPowerUpEmplacement());
    	g2d.setColor(Constants.COLOR_FIRE_2);
    	g2d.fill(b.getPowerUpEmplacement2());
    	g2d.setColor(Constants.COLOR_FIRE_2);
    	g2d.fill(b.getPowerUpEmplacement2());
    }

    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.BORDER_COLOR);
        g2d.fill(s);

        Shape s2 = p.getPlayerFaceInner();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s2);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
        
            case KeyEvent.VK_A:
            	keyLeftPressed=true;
                break;
            case KeyEvent.VK_D:
            	keyRightPressed=true;
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
	        case KeyEvent.VK_A:
	            keyLeftPressed=false;
	            break;
	        case KeyEvent.VK_D:
	        	keyRightPressed=false;
	            break;
	    }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

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
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}
