package ct.game.main;

import MapGame.Maps.MAP_TestLevel;
import Player.EntityPlayer;
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
    // Player
    public static EntityPlayer entityPlayer;
    public static EntityPlayer bot;

    private final MAP_TestLevel mapTEST;

    public MainScreenCamera(){
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH / PPM, HEIGHT / PPM, orthographicCamera);

        camera = new CameraManager(viewport);
        camera.setZoom(1f);
        camera.setOffsetsOfCamera(0, 5);
        camera.setCameraDamping(0.1f);

        entityPlayer = new EntityPlayer(100, 0, new Vector2(0, -240));

        batch = new SpriteBatch();

        mapTEST = new MAP_TestLevel(batch);
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        camera.follow(entityPlayer.getPositions());

        entityPlayer.updatePlayerComponents(deltaTime);
        GameMain.getPhysicsManager().renderHitboxObjects(camera.getCamera());

        batch.begin();

        loadMap();
        entityPlayer.renderPlayerGraphics(batch);

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

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }
}
