import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    public static final AudioClip BACKGROUND = Applet.newAudioClip(Sound.class.getResource("Resources/sound_bg.wav"));
    public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource("Resources/sound_gameover.wav"));
    public static final AudioClip LOSEREWARD = Applet.newAudioClip(Sound.class.getResource("Resources/sound_losereward.wav"));
    public static final AudioClip MENU = Applet.newAudioClip(Sound.class.getResource("Resources/sound_menu.wav"));
    public static final AudioClip MISSILEHIT = Applet.newAudioClip(Sound.class.getResource("Resources/sound_missilehit.wav"));
    public static final AudioClip PICKREWARD = Applet.newAudioClip(Sound.class.getResource("Resources/sound_pickreward.wav"));
    public static final AudioClip WIN = Applet.newAudioClip(Sound.class.getResource("Resources/sound_win.wav"));
    public static final AudioClip WALLDESTROY = Applet.newAudioClip(Sound.class.getResource("Resources/sound_walldestroy.wav"));

}