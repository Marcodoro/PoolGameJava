import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {

    public static int height = 600;
    public static int width = 800;

    public static void main(String[] arg) {
        // Configure the window settings
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setTitle("My Custom 2D Game");
        config.setWindowedMode(width, height); // Width, Height in pixels
        config.useVsync(true);            // Caps frame rate to your monitor's refresh rate

        // Launch the window and pass it your game loop
        new Lwjgl3Application(new Game(), config);
    }
}
