package com.unreal.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.exit;
import static java.lang.System.exit;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
@SuppressWarnings("all")
public class Main extends Game implements Serializable {
    Float spaceGravityByPlantName(String Planet){
        if (Planet.equals("Mars")) return -3.73f;
        else if (Planet.equals("Earth")) return -9.81f;
        else if (Planet.equals("Jupiter")) return -23.1f;
        else if (Planet.equals("Saturn")) return -9.05f;
        else if (Planet.equals("Uranus")) return -8.69f;
        else if (Planet.equals("Moon")) return -1.62f;
        else if (Planet.equals("Neptune")) return -11f;
        else if (Planet.equals("Mercury")) return -3.7f;
        else if (Planet.equals("Venus")) return -8.87f;
        return -3.73f;
    }
    public transient Music ost_theme;
    String musicFile;
    boolean paused = true;
    @Override
    public void create() {
        ost_theme = Gdx.audio.newMusic(Gdx.files.internal("assets/TitleTheme.mp3"));
        ost_theme.setLooping(true);
        ost_theme.setVolume(0.5f);
        ost_theme.play();
        paused = false;
        musicFile = Gdx.files.internal("assets/TitleTheme.mp3").toString();
        setScreen(new HomePage(this));
    }
    public void playMusic(){
        if(ost_theme.isPlaying() && ost_theme!=null){
            ost_theme.play();
            paused = false;
        }
    }
    public boolean isPlayingTitleTheme(){
        return musicFile.equals(Gdx.files.internal("assets/TitleTheme.mp3").toString());
    }
    public void playMusic(String Path){
        if(ost_theme!=null){
            ost_theme.stop();
            ost_theme.dispose();
        }
        ost_theme = Gdx.audio.newMusic(Gdx.files.internal(Path));
        ost_theme.setLooping(true);
        ost_theme.setVolume(0.5f);
        musicFile = Gdx.files.internal(Path).toString();
        if (paused) return;
        ost_theme.play();
        paused = false;
    }
    public void pauseMusic() {
        if(ost_theme.isPlaying() && ost_theme!=null){
            ost_theme.pause();
            ost_theme.setVolume(0.0f);
            paused = true;
        }
    }
    public void pauseMusicForDispose() {
        if(ost_theme.isPlaying() && ost_theme!=null){
            ost_theme.pause();
            ost_theme.setVolume(0.0f);
//            paused = true;
        }
    }
    public void resumeMusic() {
        if (paused) {
            ost_theme.play();
            ost_theme.setVolume(0.5f);
        }

    }

    public void stopMusic() {
        if(ost_theme!=null){
            ost_theme.stop();
            paused = true;
        }
    }

    public void setVolume(float volume) {
        if(ost_theme!=null && ost_theme.isPlaying()){
            ost_theme.setVolume(volume);
        }
    }

    public void setOst_theme(String Path) {
        this.ost_theme = Gdx.audio.newMusic(Gdx.files.internal(Path));
    }

    protected void clickHandling(final ImageButton ButtonData, final Pixmap buttonMap, final Screen toGo){
        ButtonData.addListener(new ClickListener() {
            final ImageButton ButtonCopy = ButtonData;
            final Pixmap PixmapCopy = buttonMap;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 localCoords = new Vector2();
                ButtonCopy.localToStageCoordinates(localCoords.set(x, y));
                float scaleX = 1.0f;
                float scaleY = 1.0f;
                if ((float) ButtonCopy.getHeight() != (float) PixmapCopy.getHeight()) {
                    scaleY = (float) ButtonCopy.getHeight() / (float) PixmapCopy.getHeight();
//                    Gdx.app.log("scaleY", scaleY + "");
                }
                if ((float) ButtonCopy.getWidth() != (float) PixmapCopy.getWidth()) {
                    scaleX = (float) ButtonCopy.getWidth() / (float) PixmapCopy.getWidth();
//                    Gdx.app.log("scaleY", scaleX + "");
                }
                float buttonX = ButtonCopy.getX();  float buttonY = ButtonCopy.getY();
                float localX = -(buttonX - localCoords.x);  float localY = ButtonCopy.getHeight() + (buttonY - localCoords.y);

//                Gdx.app.log("Click", "Local Coordinates: (" + localX + ", " + localY + ")");
                // Check if the pixel is opaque
                if (isPixelOpaque((int) (localX/scaleX), (int) (localY/scaleY), PixmapCopy)) {
                    Gdx.app.log("Click", "Button clicked inside shape!");
                    if (toGo != null){
                        setScreen(toGo);
                    }
                } else {
                    Gdx.app.log("Click", "Clicked outside the button shape");
                }
            }

        });
    }

    protected void clickHandlingByFunction(final ImageButton ButtonData, final Pixmap buttonMap, final Runnable action) {
        ButtonData.addListener(new ClickListener() {
            final ImageButton ButtonCopy = ButtonData;
            final Pixmap PixmapCopy = buttonMap;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 localCoords = new Vector2();
                ButtonCopy.localToStageCoordinates(localCoords.set(x, y));
                float scaleX = 1.0f;
                float scaleY = 1.0f;
                if (ButtonCopy.getHeight() != PixmapCopy.getHeight()) {
                    scaleY = (float) ButtonCopy.getHeight() / PixmapCopy.getHeight();
                }
                if (ButtonCopy.getWidth() != PixmapCopy.getWidth()) {
                    scaleX = (float) ButtonCopy.getWidth() / PixmapCopy.getWidth();
                }
                float buttonX = ButtonCopy.getX();
                float buttonY = ButtonCopy.getY();
                float localX = -(buttonX - localCoords.x);
                float localY = ButtonCopy.getHeight() + (buttonY - localCoords.y);

                // Check if the pixel is opaque
                if (isPixelOpaque((int) (localX / scaleX), (int) (localY / scaleY), PixmapCopy)) {
                    Gdx.app.log("Click", "Button clicked inside shape!");
                    if (action != null) {
                        action.run();  // Run the provided function instead of changing screen
                    }
                } else {
                    Gdx.app.log("Click", "Clicked outside the button shape");
                }
            }
        });
    }

    protected void clickHandlingByFunction(final ImageButton ButtonData, final Pixmap buttonMap, final Screen toGo, final Runnable action) {
        ButtonData.addListener(new ClickListener() {
            final ImageButton ButtonCopy = ButtonData;
            final Pixmap PixmapCopy = buttonMap;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 localCoords = new Vector2();
                ButtonCopy.localToStageCoordinates(localCoords.set(x, y));
                float scaleX = 1.0f;
                float scaleY = 1.0f;
                if (ButtonCopy.getHeight() != PixmapCopy.getHeight()) {
                    scaleY = (float) ButtonCopy.getHeight() / PixmapCopy.getHeight();
                }
                if (ButtonCopy.getWidth() != PixmapCopy.getWidth()) {
                    scaleX = (float) ButtonCopy.getWidth() / PixmapCopy.getWidth();
                }
                float buttonX = ButtonCopy.getX();
                float buttonY = ButtonCopy.getY();
                float localX = -(buttonX - localCoords.x);
                float localY = ButtonCopy.getHeight() + (buttonY - localCoords.y);

                // Check if the pixel is opaque
                if (isPixelOpaque((int) (localX / scaleX), (int) (localY / scaleY), PixmapCopy)) {
                    Gdx.app.log("Click", "Button clicked inside shape!");
                    if (action != null) {
                        action.run();  // Run the provided function instead of changing screen
                        setScreen(toGo);
                    }
                } else {
                    Gdx.app.log("Click", "Clicked outside the button shape");
                }
            }
        });
    }



    protected boolean isPixelOpaque(int x, int y, Pixmap a) {
//        Gdx.app.log("getWidth, getHeight", "(" + a.getWidth() + ", " + a.getHeight() + ")");
        if (x < 0 || x >= a.getWidth() || y < 0 || y >= a.getHeight()) {
            return false;  // Click is outside the button boundaries
        }
        int pixel = a.getPixel(x, y);
        return (pixel & 0x000000ff) != 0;  // Check alpha value (non-zero is opaque)
    }

    protected void saveGameScreen(Screen screen, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("GameSaves/"+fileName))) {
            out.writeObject(screen);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Deserialize method
    protected Screen loadGameScreen(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("GameSaves/" + fileName))) {
            return (Screen) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected void saveGameScore(Player screen, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("GameSaves/" + fileName))) {
            out.writeObject(screen);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Deserialize method
    protected Player loadGameScore(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("GameSaves/" + fileName))) {
            return (Player) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    protected void removeFile(String fileName) {
        File file = new File("GameSaves/"+fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
