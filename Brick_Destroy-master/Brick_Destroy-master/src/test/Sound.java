package test;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.net.URL;

@SuppressWarnings("deprecation")
public class Sound {
	static AudioClip bounce = Applet.newAudioClip(Sound.class.getResource("sound1.wav"));
	static AudioClip bonus = Applet.newAudioClip(Sound.class.getResource("sound2.wav"));
	
	public static void playSound(int num) {
		if (num==0) {
			URL url = Sound.class.getResource("collision.wav");
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
		else if (num==1){
			URL url = Sound.class.getResource("collisionpad.wav");
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
		else if (num==2){
			URL url = Sound.class.getResource("gameover.wav");
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
		else if (num==3){
			URL url = Sound.class.getResource("winbrickbreaker.wav");
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
		else if (num==4){
			URL url = Sound.class.getResource("looseball.wav");
			AudioClip clip = Applet.newAudioClip(url);
			clip.play();
		}
	}
	
}
