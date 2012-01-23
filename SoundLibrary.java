import java.util.HashMap;
import java.applet.*;
import java.net.*;
public class SoundLibrary {
    HashMap<String,AudioClip> soundLibrary;
    public SoundLibrary (){
        AudioClip select = null;
        AudioClip match = null;
        AudioClip fall = null;
        try {
        select = Applet.newAudioClip(new URL("file:select.wav"));
        fall = Applet.newAudioClip(new URL("file:fall.wav"));
        match = Applet.newAudioClip(new URL("file:match.wav"));
        } catch (Exception e) {System.out.println(e.getMessage());}
        soundLibrary = new HashMap(3);
        soundLibrary.put("select", select);
        soundLibrary.put("fall", fall);
        soundLibrary.put("match",match);
         
         
    }
    public void playAudio(String name){
        AudioClip sample = soundLibrary.get(name);
        sample.play();
    }
}
