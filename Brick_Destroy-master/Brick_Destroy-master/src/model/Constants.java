package test;

import java.awt.*;

public class Constants {
    public static final String GREETINGS = "Welcome to:";
    public static final String GAME_TITLE = "Brick Destroy";
    public static final String CREDITS = "Version 0.1";
    public static final String START_TEXT = "Start";
    public static final String MENU_TEXT = "Exit";
    public static final String BACK_MENU_TEXT = "BACK TO MENU";
    public static final String INFORMATION_TITLE = "INFORMATIONS";
    public static final String INFORMATION_TEXT = "Your goal is to destroy a wall with a small ball\n\n"
    		+"Controls :\n"
    		+"SPACE : restart/pause the game \n"
    		+"A/D : move the paddle \n"
    		+"ESC : enter/exit pause menu \n"
    		+"ALT+SHIFT+F1 : open console";
    public static final String SCORE_TITLE = "HIGH SCORE LIST";
    public static final String NEXT_TEXT = "NEXT";
    public static final String RETRY_TEXT = "RETRY";
    public static final String SCORE_TEXT = "Use W/S to scroll through the high score list";
    public static final String WIN_TEXT = "CONGRATULATIONS !";
    public static final String LOOSE_TEXT = "TRY AGAIN !";
    
    public static final Color BG_COLOR = new Color(0x0a0838);
    public static final Color BORDER_COLOR = new Color(200,8,21); 
    public static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);
    public static final Color TEXT_COLOR = new Color(0xFFFFFF);
    public static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    public static final Color CLICKED_TEXT = Color.WHITE;
    public static final Color INFO_BUTTON_E1 = new Color(0x4b43cb);
    public static final Color INFO_BUTTON_E2 = new Color(0x6d65eb);
    
    public static final Color COLOR_BUTTON_E1 = new Color(0x2c277d);
    public static final Color COLOR_BUTTON_E2 = new Color(0x3b3698);
    
    public static final Color COLOR_BOX_INFO = new Color(0xb2b0dd);
    public static final Color COLOR_BOX_INFO_SHADOW = new Color(0x8380bb);
    public static final Color COLOR_INFO_TEXT  = new Color(0x1A1933);
    public static final Color COLOR_BOX_SCORE = new Color(0xEFEFFE);
    public static final Color COLOR_BOX_SCORE_SHADOW = new Color(0xDDDCEE);
    
    public static final Color COLOR_FIRE_1 = new Color(0xfff40b);
    public static final Color COLOR_FIRE_2 = new Color(0xfff40b);
    public static final Color COLOR_FIRE_3 = new Color(0xfff40b);
    
    public static final int BORDER_SIZE = 5;
    public static final float[] DASHES = {12,6};
    public static final String CONTINUE = "Continue";
    public static final String RESTART = "Restart";
    public static final String EXIT = "Exit";
    public static final String PAUSE = "Pause Menu";
    public static final int TEXT_SIZE = 30;
    public static final Color MENU_COLOR = new Color(0,255,0);


    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 450;

    public static final Color BG_COLOR_Board = new Color(0x0a0838);
    
    public static final int SPEED = 4;
    public static final int SPEED_FAST = 6;
}
