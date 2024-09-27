package ct.game.main;

import Player.ActionSpritesAnimations;
import Player.EntityPlayer;
import Player.HomerunBatAnimations;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

import static ct.game.main.GameMain.HEIGHT;
import static ct.game.main.GameMain.WIDTH;

public class PlayerScreen implements Screen {
    // Camera
    private final Camera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;

    public EntityPlayer entityPlayer;
    private HomerunBatAnimations homerunBatAnimations;

    public PlayerScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        homerunBatAnimations = new HomerunBatAnimations(0.025f);
        entityPlayer = new EntityPlayer(100, 1, homerunBatAnimations, new Vector2(250, 35));

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        entityPlayer.updatePlayerElements(deltaTime);

        batch.begin();

        batch.draw(entityPlayer.getCurrentFrame(), entityPlayer.getPositionX(), entityPlayer.getPositionY(), 100, 100);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private void refreshScreen(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }
}
