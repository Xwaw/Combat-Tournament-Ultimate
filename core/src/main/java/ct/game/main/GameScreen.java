package ct.game.main;

import Player.EntityPlayer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

import static ct.game.main.GameMainScreen.HEIGHT;
import static ct.game.main.GameMainScreen.WIDTH;

public class GameScreen implements Screen {
    // Camera
    private final Camera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;
    // Test
    private Sprite playerSprite;

    public EntityPlayer entityPlayer;

    public GameScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        Texture imagePlayerTest = new Texture("pngFiles/TEST_PLAYER_SpritePositions.png");
        playerSprite = new Sprite(imagePlayerTest);
        entityPlayer = new EntityPlayer(100, 25, playerSprite, new Vector2(0, 0));

        batch = new SpriteBatch();
    }

    int TEST_specialPoint = 0; // checking if the bars are displayed correctly according to the player data

    @Override
    public void render(float deltaTime) {
        refreshScreen();

        TEST_specialPoint++;
        entityPlayer.setPlayerSP(TEST_specialPoint);
        if(entityPlayer.getPlayerSP() >= 100){
            entityPlayer.setPlayerSP(0);

            TEST_specialPoint = 0;
        }

        batch.begin();

        entityPlayer.drawSprite(batch);

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
