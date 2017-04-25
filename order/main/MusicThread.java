//음악이 재생되고있음

package order.main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicThread extends Thread{
	MediaPlayer mediaPlayer;
	URL[] url= new URL[2];
	
	public void run() {
		
		try {
			for (int i = 0; i < url.length; i++) {
				url[i] = new URL("http://localhost:9090/data/jazz" + (i + 1) +  ".mp3");
				Media hit;
				hit = new Media(url[i].toString());
				mediaPlayer=new MediaPlayer(hit);
				mediaPlayer.play();
				final JFXPanel fxPanel = new JFXPanel();
			}
			//MediaView mediaview = new MediaView(mediaPlayer);
			
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		/*		while(true){
			System.out.println("음악재생");
			FileInputStream fis=null;
			String fileLocation="http://localhost:9090/data/jazz.mp3";
			
			try {
				fis=new FileInputStream(new File(fileLocation));
				AdvancedPlayer player=new AdvancedPlayer(fis);
				player.play();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		
		}*/

	}
}
