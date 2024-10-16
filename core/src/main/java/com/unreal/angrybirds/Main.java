package com.unreal.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    SpaceStorage spaceLevelsStorage = new SpaceStorage();

    @Override
    public void create() {
        setScreen(new HomePage(this));
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
                    Gdx.app.log("scaleY", scaleY + "");
                }
                if ((float) ButtonCopy.getWidth() != (float) PixmapCopy.getWidth()) {
                    scaleX = (float) ButtonCopy.getWidth() / (float) PixmapCopy.getWidth();
                    Gdx.app.log("scaleY", scaleX + "");
                }

                float buttonX = ButtonCopy.getX();  float buttonY = ButtonCopy.getY();
                float localX = -(buttonX - localCoords.x);  float localY = ButtonCopy.getHeight() + (buttonY - localCoords.y);

                Gdx.app.log("Click", "Local Coordinates: (" + localX + ", " + localY + ")");
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

    protected boolean isPixelOpaque(int x, int y, Pixmap a) {
//        Gdx.app.log("getWidth, getHeight", "(" + a.getWidth() + ", " + a.getHeight() + ")");
        if (x < 0 || x >= a.getWidth() || y < 0 || y >= a.getHeight()) {
            return false;  // Click is outside the button boundaries
        }
        int pixel = a.getPixel(x, y);
        return (pixel & 0x000000ff) != 0;  // Check alpha value (non-zero is opaque)
    }
}
