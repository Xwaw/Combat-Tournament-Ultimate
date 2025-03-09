package ct.game.main;

import MapGame.Maps.MAP_TestLevel;
import Player.Entity;
import Player.EntityGraphicsManager;
import Player.PlayerController;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

import static ct.game.main.GameMain.*;

public class MainScreenCamera implements Screen {
    public static int offSetX = -42, offSetY = -65;
    //batch.draw(currentFrame, (entityPlayer.getPositionX() / PPM) + (offSetX / PPM), (entityPlayer.getPositionY() / PPM) + (offSetY / PPM), (wFrame/2) / PPM, (hFrame/2) / PPM);
    // Camera

    private final CameraManager camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;

    public final Entity mainPlayer;

    private final MAP_TestLevel mapTEST;

    public MainScreenCamera(){
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH / PPM, HEIGHT / PPM, orthographicCamera);

        camera = new CameraManager(viewport);
        camera.setZoom(1f);
        camera.setOffsetsOfCamera(0, 5);
        camera.setCameraDamping(0.1f);

        mainPlayer = new Entity(100, 100, new Vector2);

        batch = new SpriteBatch();

        mapTEST = new MAP_TestLevel(batch);
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        camera.follow(mainPlayer.getBodyPositions());

        mainPlayer.update(deltaTime);

        GameMain.getPhysicsManager().renderHitboxObjects(camera.getCamera());

        batch.begin();
            loadMap();
            mainPlayer.renderPlayerHitBox(batch);
            mainPlayer.renderPlayerGraphics(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTEST.dispose();
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

    private void loadMap(){ //After implements map editor this function will disappear same as class MAP_TestLevel
        mapTEST.render();
    }

    private void refreshScreen(){
        camera.update();
        batch.setProjectionMatrix(camera.getCamera().combined);
    }

    public EntityGraphicsManager getEntityPlayer() {
        return mainPlayer;
    }
}
