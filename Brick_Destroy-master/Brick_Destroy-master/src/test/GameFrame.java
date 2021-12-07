package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private HomeMenu homeMenu;
    private InfoBoard infoBoard;
    private HighScoreBoard scoreBoard;
    
    private boolean gaming;

    public GameFrame(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout());
        this.setResizable(false);
        gameBoard = new GameBoard(this);
        homeMenu = new HomeMenu(this,new Dimension(450,300));
        infoBoard = new InfoBoard(this,new Dimension(450,300));
        scoreBoard = new HighScoreBoard(this, new Dimension(600,450));
        
        this.add(homeMenu,BorderLayout.CENTER);

    }

    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }
    
    public void enableInfoBoard() {
    	this.remove(homeMenu);
        this.add(infoBoard,BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }
    
    public void enableMenuBoardFromInfoBoard() {
    	this.remove(infoBoard);
        this.add(homeMenu,BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }
    
    public void enableMenuBoardFromHighScoreBoard() {
    	this.remove(scoreBoard);
        this.add(homeMenu,BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }
    
    public void enableGameBoardFromScoreBoard() {
    	this.remove(scoreBoard);
        this.add(gameBoard,BorderLayout.CENTER);
        gameBoard.requestFocusInWindow();
        gaming=true;
        this.pack();
        this.repaint();
    }
    
    public void enableScoreBoardFromGameBoard(boolean cas, int brickcount, int life) {
    	this.remove(gameBoard);
        this.add(scoreBoard,BorderLayout.CENTER);
        if (cas) {
        	scoreBoard.setTitle(Constants.WIN_TEXT);
        }
        else {
        	scoreBoard.setTitle(Constants.LOOSE_TEXT);
        }
        scoreBoard.addScore(brickcount, life);
        scoreBoard.getScores();
        scoreBoard.requestFocusInWindow();
        this.pack();
        this.repaint();
    }
    
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();

    }
    
}
