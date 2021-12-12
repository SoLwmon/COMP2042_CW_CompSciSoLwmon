package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {
	public static Image backgroundMenu;

	/**
	 * generate the path to the background image
	 */
	public static void generationResources() {
		try {			
			backgroundMenu = ImageIO.read(new File("BrickDestroy/res/background.png"));
		} 
		catch (IOException e) {		
			e.printStackTrace();	
		}
	}
}
