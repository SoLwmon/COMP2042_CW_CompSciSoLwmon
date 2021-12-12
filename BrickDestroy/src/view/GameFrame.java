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
package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


public class GameFrame extends JFrame implements WindowFocusListener {

    public static final String WIN_TEXT = "CONGRATULATIONS !";
    public static final String LOOSE_TEXT = "TRY AGAIN !";
    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private HomeMenu homeMenu;
    private InfoBoard infoBoard;
    private HighScoreBoard scoreBoard;
    
    private boolean gaming;

    /**
     * Game Frame constructor
     */
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

    /**
     * initiate the game frame
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * To enable game board
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }

    /**
     * To enable info board
     */
    public void enableInfoBoard() {
    	this.remove(homeMenu);
        this.add(infoBoard,BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }

    /**
     * To enable menu board from info board
     */
    public void enableMenuBoardFromInfoBoard() {
    	this.remove(infoBoard);
        this.add(homeMenu,BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }

    /**
     * To enable menu board from high score board
     */
    public void enableMenuBoardFromHighScoreBoard() {
    	this.remove(scoreBoard);
        this.add(homeMenu,BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }

    /**
     * To enable game board from score board
     */
    public void enableGameBoardFromScoreBoard() {
    	this.remove(scoreBoard);
        this.add(gameBoard,BorderLayout.CENTER);
        gameBoard.requestFocusInWindow();
        gaming=true;
        this.pack();
        this.repaint();
    }

    /**
     * To enable score board from game board
     * @param cas flag to see the win or lose text
     * @param point point gain
     */
    public void enableScoreBoardFromGameBoard(boolean cas, int point) {
    	this.remove(gameBoard);
        this.add(scoreBoard,BorderLayout.CENTER);
        if (cas) {
        	scoreBoard.setTitle(WIN_TEXT);
        }
        else {
        	scoreBoard.setTitle(LOOSE_TEXT);
        }
        scoreBoard.addScore(point);
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
